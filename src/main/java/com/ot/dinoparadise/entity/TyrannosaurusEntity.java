package com.ot.dinoparadise.entity;

import com.ot.dinoparadise.config.DinoConfig;
import com.ot.dinoparadise.entity.ai.DinoAttackHostileGoal;
import com.ot.dinoparadise.entity.ai.DinoFollowOwnerGoal;
import com.ot.dinoparadise.entity.ai.DinoOwnerHurtByTargetGoal;
import com.ot.dinoparadise.entity.ai.DinoOwnerHurtTargetGoal;
import com.ot.dinoparadise.registry.ModTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

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
    private static final EntityDataAccessor<Boolean> DATA_IS_FEMALE =
            SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> DATA_COMMAND =
            SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.STRING);

    // ---- NBT キー ----
    private static final String NBT_GROWTH_STAGE = "GrowthStage";
    private static final String NBT_GROWTH_TICK  = "GrowthTick";
    private static final String NBT_IS_FEMALE    = "IsFemale";
    private static final String NBT_COMMAND      = "DinoCommand";

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

    // ==== AI（Task007: 所有・命令対応） ====

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));  // SIT コマンド
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new DinoFollowOwnerGoal(this, 1.2D, 6.0F, 2.0F, false)); // FOLLOW コマンド
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        // 所有個体: DEFEND/ATTACK 命令で飼い主の攻防に連動
        this.targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this)); // DEFEND: 飼い主を攻撃した相手
        this.targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));   // DEFEND/ATTACK: 飼い主の攻撃対象
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this)            // 被攻撃時反撃（SIT 以外・未所有）
                .setAlertOthers());
        this.targetSelector.addGoal(4, new DinoAttackHostileGoal(this));     // ATTACK: 索敵して敵対Mob攻撃
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(   // 野生は常時プレイヤーを敵対視
                this, Player.class, true,
                (e) -> !this.isTame()));
    }

    // ==== 命令システム ====

    public DinoCommand getDinoCommand() {
        try {
            return DinoCommand.valueOf(this.entityData.get(DATA_COMMAND));
        } catch (IllegalArgumentException e) {
            return DinoCommand.FOLLOW;
        }
    }

    public void setDinoCommand(DinoCommand command) {
        this.entityData.set(DATA_COMMAND, command.name());
        // SIT コマンド時は TamableAnimal の sit フラグも設定
        this.setOrderedToSit(command == DinoCommand.SIT);
    }

    // ==== 同期データ初期化 ====

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_GROWTH_STAGE, GrowthStage.BABY.name());
        this.entityData.define(DATA_GROWTH_TICK, 0);
        this.entityData.define(DATA_IS_FEMALE, false);
        this.entityData.define(DATA_COMMAND, DinoCommand.FOLLOW.name());
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

    // ==== その他 ====

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    // ==== 食性システム（Task005） ====

    /** 給餌対象かを返す。繁殖や tame 判定には使わず独自ロジックで処理する */
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModTags.Items.TYRANNOSAURUS_FOOD);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (isFood(stack) && this.isTame()) {
            if (this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                this.heal(4.0F);

                // 幼体・若年体への給餌で成長促進
                GrowthStage stage = getGrowthStage();
                if (!stage.isAdult() && DinoConfig.FEEDING_GROWTH_ENABLED.get()) {
                    int reduction = (int) (stage.growthTicks() * DinoConfig.FEEDING_GROWTH_REDUCTION.get());
                    this.reduceGrowthTick(reduction);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
            return InteractionResult.PASS;
        }

        return super.mobInteract(player, hand);
    }

    /** 繁殖しない（スコープ外） */
    @Override
    public TyrannosaurusEntity getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    // ==== 性別システム（Task006） ====

    public boolean isFemale() {
        return this.entityData.get(DATA_IS_FEMALE);
    }

    public void setFemale(boolean female) {
        this.entityData.set(DATA_IS_FEMALE, female);
    }

    /** 性別記号を返す（表示用） */
    public String getGenderSymbol() {
        return isFemale() ? "♀" : "♂";
    }

    /** スポーン時に性別を 50/50 で決定する */
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, SpawnGroupData spawnData,
                                        net.minecraft.nbt.CompoundTag dataTag) {
        this.setFemale(this.random.nextBoolean());
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, dataTag);
    }

    /**
     * カーソル時に名前と性別を表示するカスタム名を返す（Q-06: 視線時HUD方式）。
     * 実際の HUD 表示は ClientEvents の名前タグ描画イベントで制御する。
     */
    @Override
    public Component getDisplayName() {
        Component base = super.getDisplayName();
        return Component.literal(base.getString() + " " + getGenderSymbol());
    }

    // ==== NBT 保存・読込（成長段階・カウンタ・性別・命令） ====

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString(NBT_GROWTH_STAGE, getGrowthStage().name());
        tag.putInt(NBT_GROWTH_TICK, getGrowthTick());
        tag.putBoolean(NBT_IS_FEMALE, isFemale());
        tag.putString(NBT_COMMAND, getDinoCommand().name());
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
        if (tag.contains(NBT_IS_FEMALE)) {
            this.entityData.set(DATA_IS_FEMALE, tag.getBoolean(NBT_IS_FEMALE));
        }
        if (tag.contains(NBT_COMMAND)) {
            try {
                DinoCommand cmd = DinoCommand.valueOf(tag.getString(NBT_COMMAND));
                this.entityData.set(DATA_COMMAND, cmd.name());
                this.setOrderedToSit(cmd == DinoCommand.SIT);
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
