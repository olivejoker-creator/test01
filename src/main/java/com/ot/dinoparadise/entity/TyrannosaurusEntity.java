package com.ot.dinoparadise.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * ティラノサウルス エンティティ。
 * Task003: エンティティ核（基本AI・属性）
 * Task004: 成長システム（GrowthStage / growthTick / 動的ステータス）
 */
public class TyrannosaurusEntity extends TamableAnimal {

    // ---- 同期データ ----
    private static final EntityDataAccessor<String> DATA_GROWTH_STAGE =
            SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> DATA_GROWTH_TICK =
            SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.INT);

    // ---- NBT キー ----
    private static final String NBT_GROWTH_STAGE = "GrowthStage";
    private static final String NBT_GROWTH_TICK  = "GrowthTick";

    public TyrannosaurusEntity(EntityType<? extends TyrannosaurusEntity> type, Level level) {
        super(type, level);
    }

    // ==== 属性（成体値を基底とし、段階変更時に上書き） ====

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ARMOR, 0.0D);
    }

    // ==== AI ====

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        // 野生個体は敵対（Task007 で所有個体のAIを切替）
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // ==== 同期データ初期化 ====

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_GROWTH_STAGE, GrowthStage.BABY.name());
        this.entityData.define(DATA_GROWTH_TICK, 0);
    }

    // ==== 成長ステージ ====

    public GrowthStage getGrowthStage() {
        try {
            return GrowthStage.valueOf(this.entityData.get(DATA_GROWTH_STAGE));
        } catch (IllegalArgumentException e) {
            return GrowthStage.BABY;
        }
    }

    public void setGrowthStage(GrowthStage stage) {
        this.entityData.set(DATA_GROWTH_STAGE, stage.name());
        if (!this.level().isClientSide()) {
            applyStageAttributes(stage);
        }
        this.refreshDimensions();
    }

    /** 段階に応じた属性値を適用する */
    private void applyStageAttributes(GrowthStage stage) {
        var health = this.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            double prev = this.getHealth();
            health.setBaseValue(stage.getMaxHealth());
            // HP が新しい最大値を超えないよう調整
            this.setHealth((float) Math.min(prev, stage.getMaxHealth()));
        }
        var attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null) attack.setBaseValue(stage.getAttackDamage());
        var speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) speed.setBaseValue(stage.getMoveSpeed());
    }

    // ==== 成長カウンタ ====

    public int getGrowthTick() {
        return this.entityData.get(DATA_GROWTH_TICK);
    }

    public void setGrowthTick(int tick) {
        this.entityData.set(DATA_GROWTH_TICK, tick);
    }

    /**
     * 成長残り時間を短縮する。Task005（食性システム）から呼び出す。
     * @param reduction 短縮する tick 数
     */
    public void reduceGrowthTick(int reduction) {
        GrowthStage stage = getGrowthStage();
        if (stage.isAdult()) return;
        int current = getGrowthTick();
        setGrowthTick(Math.max(0, current - reduction));
    }

    // ==== 毎 tick 成長処理 ====

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide() && this.isAlive()) {
            tickGrowth();
        }
    }

    private void tickGrowth() {
        GrowthStage stage = getGrowthStage();
        if (stage.isAdult()) return;

        int tick = getGrowthTick() + 1;
        int required = stage.growthTicks();

        if (tick >= required) {
            setGrowthTick(0);
            setGrowthStage(stage.next());
        } else {
            setGrowthTick(tick);
        }
    }

    // ==== サイズ（段階別） ====

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        GrowthStage stage = getGrowthStage();
        return EntityDimensions.scalable(stage.getWidth(), stage.getHeight());
    }

    // ==== NBT 保存・読込 ====

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString(NBT_GROWTH_STAGE, getGrowthStage().name());
        tag.putInt(NBT_GROWTH_TICK, getGrowthTick());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(NBT_GROWTH_STAGE)) {
            try {
                GrowthStage stage = GrowthStage.valueOf(tag.getString(NBT_GROWTH_STAGE));
                this.entityData.set(DATA_GROWTH_STAGE, stage.name());
                applyStageAttributes(stage);
                this.refreshDimensions();
            } catch (IllegalArgumentException ignored) {}
        }
        if (tag.contains(NBT_GROWTH_TICK)) {
            this.entityData.set(DATA_GROWTH_TICK, tag.getInt(NBT_GROWTH_TICK));
        }
    }

    // ==== その他 ====

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        // 食性は Task005 で実装
        return false;
    }

    /** 繁殖しない（スコープ外） */
    @Override
    public TyrannosaurusEntity getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }
}
