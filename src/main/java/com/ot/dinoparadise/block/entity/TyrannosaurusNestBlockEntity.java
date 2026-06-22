package com.ot.dinoparadise.block.entity;

import com.ot.dinoparadise.inventory.NestContainer;
import com.ot.dinoparadise.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * ティラノサウルスの巣の BlockEntity。
 * SimpleContainer を保持し、卵を最大2個まで格納する（補充なし Q-02）。
 */
public class TyrannosaurusNestBlockEntity extends BlockEntity implements MenuProvider {

    public static final int SLOT_COUNT = 2;
    private static final String NBT_ITEMS = "Items";

    private final SimpleContainer items = new SimpleContainer(SLOT_COUNT) {
        @Override
        public void setChanged() {
            super.setChanged();
            TyrannosaurusNestBlockEntity.this.setChanged();
        }
    };

    public TyrannosaurusNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.TYRANNOSAURUS_NEST_BE.get(), pos, state);
    }

    public SimpleContainer getContainer() {
        return items;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.dinoparadise.tyrannosaurus_nest");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
        return new NestContainer(id, playerInv, items);
    }

    // ---- NBT ----

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        net.minecraft.core.NonNullList<ItemStack> list =
                net.minecraft.core.NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        for (int i = 0; i < SLOT_COUNT; i++) {
            list.set(i, items.getItem(i));
        }
        ContainerHelper.saveAllItems(tag, list);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        net.minecraft.core.NonNullList<ItemStack> list =
                net.minecraft.core.NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, list);
        for (int i = 0; i < SLOT_COUNT; i++) {
            items.setItem(i, list.get(i));
        }
    }
}
