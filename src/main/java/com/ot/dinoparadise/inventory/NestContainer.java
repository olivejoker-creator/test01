package com.ot.dinoparadise.inventory;

import com.ot.dinoparadise.registry.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * 巣のコンテナメニュー（卵スロット × 2 + プレイヤースロット）。
 * Task009 で実装。GUI レイアウトは Task009 で完成させる。
 */
public class NestContainer extends AbstractContainerMenu {

    private final Container container;

    public NestContainer(int id, Inventory playerInv, Container container) {
        super(ModMenuTypes.NEST_MENU.get(), id);
        this.container = container;
        checkContainerSize(container, 2);

        // 巣スロット（卵 × 2）
        this.addSlot(new Slot(container, 0, 62, 35));
        this.addSlot(new Slot(container, 1, 98, 35));

        // プレイヤーインベントリ
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));
            }
        }
        // ホットバー
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
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
