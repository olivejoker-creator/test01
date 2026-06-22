package com.ot.dinoparadise.entity;

import net.minecraft.network.chat.Component;

/** 所有恐竜への命令モード（03_仕様書 5-5） */
public enum DinoCommand {
    FOLLOW  ("command.dinoparadise.follow"),
    SIT     ("command.dinoparadise.sit"),
    DEFEND  ("command.dinoparadise.defend"),
    NEUTRAL ("command.dinoparadise.neutral"),
    ATTACK  ("command.dinoparadise.attack");

    private final String translationKey;

    DinoCommand(String key) {
        this.translationKey = key;
    }

    public Component getDisplayName() {
        return Component.translatable(translationKey);
    }

    /** 次のコマンドへ巡回する（ホイッスル用） */
    public DinoCommand next() {
        DinoCommand[] values = values();
        return values[(this.ordinal() + 1) % values.length];
    }
}
