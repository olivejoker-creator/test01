package com.ot.dinoparadise.inventory;

import com.ot.dinoparadise.registry.ModMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

/**
 * 図鑑 GUI のメニュー（スロットなし・純粋な表示GUI）。
 * Task014 で完全実装する。
 */
public class DinoEncyclopediaContainer extends AbstractContainerMenu {

    public DinoEncyclopediaContainer(int id, Inventory playerInv) {
        super(ModMenuTypes.ENCYCLOPEDIA_MENU.get(), id);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
