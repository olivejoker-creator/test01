package com.ot.dinoparadise.block.entity;

import com.ot.dinoparadise.config.DinoConfig;
import com.ot.dinoparadise.entity.GrowthStage;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import com.ot.dinoparadise.registry.ModBlockEntityTypes;
import com.ot.dinoparadise.registry.ModEntities;
import com.ot.dinoparadise.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

/**
 * 卵ブロックの BlockEntity。
 * 設置者 UUID を保持し、熱源隣接時のみ孵化カウンタを進める。
 */
public class TyrannosaurusEggBlockEntity extends BlockEntity {

    private static final String NBT_OWNER_UUID   = "EggOwnerUUID";
    private static final String NBT_HATCH_TICK   = "HatchTick";

    private UUID ownerUUID = null;
    private int  hatchTick = 0;

    public TyrannosaurusEggBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.TYRANNOSAURUS_EGG_BE.get(), pos, state);
    }

    // ---- Owner UUID ----

    public void setOwnerUUID(UUID uuid) {
        this.ownerUUID = uuid;
        setChanged();
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    // ---- Tick ----

    public static void tick(Level level, BlockPos pos, BlockState state,
                            TyrannosaurusEggBlockEntity be) {
        if (level.isClientSide()) return;

        if (!hasHeatSource(level, pos)) return;

        be.hatchTick++;
        be.setChanged();

        if (be.hatchTick >= DinoConfig.HATCH_TIME_TICKS.get()) {
            hatch(level, pos, be);
        }
    }

    /** 隣接ブロックに熱源タグが付いているか確認する */
    private static boolean hasHeatSource(Level level, BlockPos pos) {
        for (var direction : net.minecraft.core.Direction.values()) {
            BlockPos neighbor = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighbor);
            if (neighborState.is(ModTags.Blocks.INCUBATOR_HEAT_SOURCE)) {
                return true;
            }
        }
        return false;
    }

    /** 孵化：幼体エンティティを出現させブロックを消す */
    private static void hatch(Level level, BlockPos pos, TyrannosaurusEggBlockEntity be) {
        TyrannosaurusEntity baby = ModEntities.TYRANNOSAURUS.get()
                .create(level);
        if (baby == null) return;

        baby.setGrowthStage(GrowthStage.BABY);
        baby.setFemale(level.random.nextBoolean()); // 孵化時に性別決定

        // 飼い主継承
        if (be.ownerUUID != null) {
            baby.tame(null);         // owner フラグを立てる（Player 参照なし）
            baby.setOwnerUUID(be.ownerUUID);
        }

        double cx = pos.getX() + 0.5;
        double cy = pos.getY();
        double cz = pos.getZ() + 0.5;
        baby.moveTo(cx, cy, cz, 0F, 0F);
        baby.finalizeSpawn((net.minecraft.world.level.ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null, null);
        level.addFreshEntity(baby);

        // 卵ブロック消滅
        level.removeBlock(pos, false);
    }

    // ---- NBT ----

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (ownerUUID != null) {
            tag.putUUID(NBT_OWNER_UUID, ownerUUID);
        }
        tag.putInt(NBT_HATCH_TICK, hatchTick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.hasUUID(NBT_OWNER_UUID)) {
            ownerUUID = tag.getUUID(NBT_OWNER_UUID);
        }
        hatchTick = tag.getInt(NBT_HATCH_TICK);
    }
}
