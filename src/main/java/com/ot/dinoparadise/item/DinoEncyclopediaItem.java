package com.ot.dinoparadise.item;

import com.ot.dinoparadise.registry.ModMenuTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import com.ot.dinoparadise.inventory.DinoEncyclopediaContainer;

/**
 * 図鑑アイテム（F-14 / Task014）。
 * 右クリックで図鑑GUIを開く。ロックなしで全種閲覧可。
 */
public class DinoEncyclopediaItem extends Item {

    public DinoEncyclopediaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            MenuProvider provider = new SimpleMenuProvider(
                    (id, inv, p) -> new DinoEncyclopediaContainer(id, inv),
                    Component.translatable("item.dinoparadise.dino_encyclopedia"));
            NetworkHooks.openScreen(serverPlayer, provider);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}
