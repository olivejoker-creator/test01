package com.ot.dinoparadise.item;

import com.ot.dinoparadise.entity.DinoCommand;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * ホイッスル（F-12 / Task010）。
 *
 * 操作体系（03_仕様書 6-3）：
 *   - しゃがみ＋右クリック → 命令巡回、アクションバー表示
 *   - 恐竜を直接右クリック → その個体に現在命令を適用
 *   - 空/ブロックを右クリック → 所有全恐竜に集団命令
 */
public class DinoWhistleItem extends Item {

    private static final String NBT_COMMAND = "SelectedCommand";
    private static final double RANGE = 128.0;

    public DinoWhistleItem(Properties properties) {
        super(properties);
    }

    // ---- 現在の選択命令 ----

    public static DinoCommand getSelectedCommand(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains(NBT_COMMAND)) {
            try {
                return DinoCommand.valueOf(stack.getTag().getString(NBT_COMMAND));
            } catch (IllegalArgumentException ignored) {}
        }
        return DinoCommand.FOLLOW;
    }

    private static void setSelectedCommand(ItemStack stack, DinoCommand command) {
        stack.getOrCreateTag().putString(NBT_COMMAND, command.name());
    }

    // ---- しゃがみ＋右クリック：命令巡回 / 通常右クリック：集団命令 ----

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            // 命令巡回
            DinoCommand next = getSelectedCommand(stack).next();
            setSelectedCommand(stack, next);
            player.displayClientMessage(
                    Component.translatable("item.dinoparadise.dino_whistle.command",
                            next.getDisplayName()), true);
            return InteractionResultHolder.success(stack);
        }

        // 集団命令（空に向けて右クリック）
        if (!level.isClientSide()) {
            DinoCommand cmd = getSelectedCommand(stack);
            applyToAllOwned((ServerLevel) level, player, cmd);
        }
        return InteractionResultHolder.success(stack);
    }

    // ---- 恐竜を直接右クリック：個別命令 ----

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player,
                                                   LivingEntity entity, InteractionHand hand) {
        if (player.isShiftKeyDown()) return InteractionResult.PASS; // shift は use() に委ねる
        if (!(entity instanceof TyrannosaurusEntity dino)) return InteractionResult.PASS;
        if (!isOwner(player, dino)) return InteractionResult.PASS;

        DinoCommand cmd = getSelectedCommand(stack);
        dino.setDinoCommand(cmd);
        if (!player.level().isClientSide()) {
            player.displayClientMessage(
                    Component.translatable("item.dinoparadise.dino_whistle.applied",
                            cmd.getDisplayName()), true);
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide());
    }

    // ---- ユーティリティ ----

    private void applyToAllOwned(ServerLevel level, Player player, DinoCommand cmd) {
        List<TyrannosaurusEntity> dinos = level.getEntitiesOfClass(
                TyrannosaurusEntity.class,
                new AABB(player.blockPosition()).inflate(RANGE),
                e -> isOwner(player, e));
        dinos.forEach(d -> d.setDinoCommand(cmd));
        player.displayClientMessage(
                Component.translatable("item.dinoparadise.dino_whistle.all_applied",
                        dinos.size(), cmd.getDisplayName()), true);
    }

    private boolean isOwner(Player player, TyrannosaurusEntity dino) {
        return dino.isTame() && player.getUUID().equals(dino.getOwnerUUID());
    }
}
