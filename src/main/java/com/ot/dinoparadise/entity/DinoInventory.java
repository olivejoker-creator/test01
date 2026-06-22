package com.ot.dinoparadise.entity;

import com.ot.dinoparadise.item.TyrannosaurusSaddleItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

/**
 * 恐竜インベントリ（サドルスロット×1 ＋ 収納スロット×15）。
 * Task012 で GUI と接続する。
 */
public class DinoInventory extends SimpleContainer {

    public static final int SADDLE_SLOT = 0;
    public static final int STORAGE_START = 1;
    public static final int STORAGE_COUNT = 15;
    public static final int TOTAL_SLOTS = 1 + STORAGE_COUNT; // 16

    private final TyrannosaurusEntity owner;

    public DinoInventory(TyrannosaurusEntity owner) {
        super(TOTAL_SLOTS);
        this.owner = owner;
    }

    /** サドルが装着されているかを返す */
    public boolean hasSaddle() {
        return getItem(SADDLE_SLOT).getItem() instanceof TyrannosaurusSaddleItem;
    }

    public void saveToTag(CompoundTag tag) {
        NonNullList<ItemStack> list = NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);
        for (int i = 0; i < TOTAL_SLOTS; i++) {
            list.set(i, getItem(i));
        }
        ContainerHelper.saveAllItems(tag, list);
    }

    public void loadFromTag(CompoundTag tag) {
        NonNullList<ItemStack> list = NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, list);
        for (int i = 0; i < TOTAL_SLOTS; i++) {
            setItem(i, list.get(i));
        }
    }
}
