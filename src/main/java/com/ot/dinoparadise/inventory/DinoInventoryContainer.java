package com.ot.dinoparadise.inventory;

import com.ot.dinoparadise.registry.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * 恐竜インベントリ GUI（サドルスロット×1 + 収納15スロット）。
 * Task012 で完全実装する。
 */
public class DinoInventoryContainer extends AbstractContainerMenu {

    private final Container container;

    public DinoInventoryContainer(int id, Inventory playerInv, Container container) {
        super(ModMenuTypes.DINO_INVENTORY_MENU.get(), id);
        this.container = container;
        checkContainerSize(container, 16); // saddle(1) + storage(15)

        // サドルスロット (index 0)
        this.addSlot(new Slot(container, 0, 8, 18));

        // 収納スロット (index 1-15, 3×5)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                this.addSlot(new Slot(container, 1 + col + row * 5,
                        44 + col * 18, 18 + row * 18));
            }
        }

        // プレイヤーインベントリ
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 102 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 160));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
