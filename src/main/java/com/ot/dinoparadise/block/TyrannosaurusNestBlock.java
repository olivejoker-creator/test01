package com.ot.dinoparadise.block;

import com.ot.dinoparadise.block.entity.TyrannosaurusNestBlockEntity;
import com.ot.dinoparadise.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

/**
 * ティラノサウルスの巣ブロック（F-04）。
 * 自然生成のみ、プレイヤーによる設置・回収不可（Q-02, Q-03）。
 */
public class TyrannosaurusNestBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE = box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public TyrannosaurusNestBlock(Properties properties) {
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

    /** 右クリックで巣GUI を開く */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof TyrannosaurusNestBlockEntity nestBE) {
                NetworkHooks.openScreen(serverPlayer, nestBE, pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    /** 破壊時もドロップしない（Q-03：回収不可） */
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            level.removeBlockEntity(pos);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TyrannosaurusNestBlockEntity(pos, state);
    }
}
