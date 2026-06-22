package com.ot.dinoparadise.data;

import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * 図鑑に表示する恐竜1種分のデータ（データ駆動設計）。
 * 将来50種追加時はここへエントリを追加するだけでよい。
 */
public record DinoEntry(
        String id,
        Component displayName,
        Component diet,       // 食性
        Component habitat,    // 生息地
        Component role,       // 役割
        Component size,       // サイズ
        List<Component> stages, // 成長段階
        Component description
) {
    /** ティラノサウルスのエントリ */
    public static final DinoEntry TYRANNOSAURUS = new DinoEntry(
            "tyrannosaurus",
            Component.translatable("dino.dinoparadise.tyrannosaurus.name"),
            Component.translatable("dino.dinoparadise.tyrannosaurus.diet"),
            Component.translatable("dino.dinoparadise.tyrannosaurus.habitat"),
            Component.translatable("dino.dinoparadise.tyrannosaurus.role"),
            Component.translatable("dino.dinoparadise.tyrannosaurus.size"),
            List.of(
                    Component.translatable("dino.dinoparadise.stage.baby"),
                    Component.translatable("dino.dinoparadise.stage.juvenile"),
                    Component.translatable("dino.dinoparadise.stage.adult")
            ),
            Component.translatable("dino.dinoparadise.tyrannosaurus.description")
    );

    /** 登録されている全恐竜エントリ（将来的にここへ追加） */
    public static final List<DinoEntry> ALL_ENTRIES = List.of(TYRANNOSAURUS);
}
