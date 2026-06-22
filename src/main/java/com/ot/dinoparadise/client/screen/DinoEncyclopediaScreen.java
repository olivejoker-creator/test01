package com.ot.dinoparadise.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.data.DinoEntry;
import com.ot.dinoparadise.inventory.DinoEncyclopediaContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

/**
 * 図鑑GUI（Task014）。
 * 1ページ1種、左右ボタンで種切替。ロックなし・全種閲覧可。
 */
public class DinoEncyclopediaScreen extends AbstractContainerScreen<DinoEncyclopediaContainer> {

    private static final ResourceLocation BG_TEXTURE =
            new ResourceLocation(DinoParadise.MOD_ID, "textures/gui/encyclopedia.png");
    private static final int IMG_W = 176;
    private static final int IMG_H = 220;

    private final List<DinoEntry> entries = DinoEntry.ALL_ENTRIES;
    private int currentIndex = 0;

    // ナビゲーションボタン領域（GUI相対座標）
    private int prevBtnX, nextBtnX, btnY;

    public DinoEncyclopediaScreen(DinoEncyclopediaContainer menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth  = IMG_W;
        this.imageHeight = IMG_H;
    }

    @Override
    protected void init() {
        super.init();
        prevBtnX = this.leftPos + 10;
        nextBtnX = this.leftPos + IMG_W - 30;
        btnY     = this.topPos  + IMG_H - 25;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        // 背景（テクスチャが未作成の場合はグレー矩形でフォールバック）
        graphics.fill(this.leftPos, this.topPos, this.leftPos + IMG_W, this.topPos + IMG_H, 0xFF888888);

        DinoEntry entry = entries.get(currentIndex);
        int x = this.leftPos + 8;
        int y = this.topPos + 8;
        int lineHeight = 10;

        graphics.drawString(this.font, entry.displayName(),   x, y,              0xFFFFFF);
        graphics.drawString(this.font, Component.translatable("gui.dinoparadise.encyclopedia.diet",
                entry.diet()), x, y + lineHeight * 2,  0xFFFFFF);
        graphics.drawString(this.font, Component.translatable("gui.dinoparadise.encyclopedia.habitat",
                entry.habitat()), x, y + lineHeight * 3, 0xFFFFFF);
        graphics.drawString(this.font, Component.translatable("gui.dinoparadise.encyclopedia.role",
                entry.role()), x, y + lineHeight * 4,   0xFFFFFF);
        graphics.drawString(this.font, Component.translatable("gui.dinoparadise.encyclopedia.size",
                entry.size()), x, y + lineHeight * 5,   0xFFFFFF);

        // 成長段階
        StringBuilder stages = new StringBuilder();
        for (int i = 0; i < entry.stages().size(); i++) {
            if (i > 0) stages.append(" → ");
            stages.append(entry.stages().get(i).getString());
        }
        graphics.drawString(this.font, stages.toString(), x, y + lineHeight * 6, 0xFFFFFF);

        // 解説（折り返し）
        this.font.split(entry.description(), IMG_W - 16)
                .forEach(line -> {});
        graphics.drawWordWrap(this.font, entry.description(), x, y + lineHeight * 8, IMG_W - 16, 0xFFFFFF);

        // ナビゲーションボタン
        graphics.fill(prevBtnX, btnY, prevBtnX + 20, btnY + 15, 0xFF444444);
        graphics.fill(nextBtnX, btnY, nextBtnX + 20, btnY + 15, 0xFF444444);
        graphics.drawString(this.font, "<", prevBtnX + 6, btnY + 4, 0xFFFFFF);
        graphics.drawString(this.font, ">", nextBtnX + 6, btnY + 4, 0xFFFFFF);

        // ページ番号
        String page = (currentIndex + 1) + "/" + entries.size();
        int pageX = this.leftPos + IMG_W / 2 - this.font.width(page) / 2;
        graphics.drawString(this.font, page, pageX, btnY + 4, 0xFFFFFF);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // タイトル・インベントリラベルは不要
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (mouseX >= prevBtnX && mouseX <= prevBtnX + 20 && mouseY >= btnY && mouseY <= btnY + 15) {
                currentIndex = (currentIndex - 1 + entries.size()) % entries.size();
                return true;
            }
            if (mouseX >= nextBtnX && mouseX <= nextBtnX + 20 && mouseY >= btnY && mouseY <= btnY + 15) {
                currentIndex = (currentIndex + 1) % entries.size();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
