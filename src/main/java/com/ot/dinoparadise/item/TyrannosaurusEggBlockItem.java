package com.ot.dinoparadise.item;

import com.ot.dinoparadise.block.entity.TyrannosaurusEggBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * ティラノの卵アイテム。設置時に設置者のUUIDをBlockEntityへ保存する（F-05 所有継承）。
 */
public class TyrannosaurusEggBlockItem extends BlockItem {

    public TyrannosaurusEggBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level,
                                                  net.minecraft.world.entity.player.Player player,
                                                  net.minecraft.world.item.ItemStack stack,
                                                  BlockState state) {
        boolean result = super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        if (!level.isClientSide() && player != null) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof TyrannosaurusEggBlockEntity eggBE) {
                eggBE.setOwnerUUID(player.getUUID());
            }
        }
        return result;
    }
}
