package com.ot.dinoparadise.block;

import com.ot.dinoparadise.block.entity.TyrannosaurusEggBlockEntity;
import com.ot.dinoparadise.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

/**
 * ティラノサウルスの卵ブロック（F-05 孵化システム）。
 * 孵化ロジックは TyrannosaurusEggBlockEntity で管理する。
 */
public class TyrannosaurusEggBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE =
            box(3.0, 0.0, 3.0, 13.0, 14.0, 13.0);

    public TyrannosaurusEggBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    // ---- BlockEntity ----

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TyrannosaurusEggBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                   BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntityTypes.TYRANNOSAURUS_EGG_BE.get(),
                TyrannosaurusEggBlockEntity::tick);
    }

    /** 破壊時はアイテムに戻さない（03_仕様書 4-1） */
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
