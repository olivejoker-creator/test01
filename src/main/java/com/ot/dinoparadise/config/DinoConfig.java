package com.ot.dinoparadise.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Dino Paradise 共通コンフィグ。
 * 03_仕様書 8章の全項目を定義する。Task017 で設定ファイル反映を完成させる。
 */
public class DinoConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // ---- スポーン ----
    public static final ForgeConfigSpec.IntValue SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue SPAWN_MIN;
    public static final ForgeConfigSpec.IntValue SPAWN_MAX;

    // ---- ドロップ ----
    /** 討伐時の卵ドロップ率 (0〜100 %) */
    public static final ForgeConfigSpec.IntValue EGG_DROP_CHANCE;

    // ---- 孵化・成長 ----
    public static final ForgeConfigSpec.IntValue HATCH_TIME_TICKS;
    public static final ForgeConfigSpec.IntValue BABY_TO_JUVENILE_TICKS;
    public static final ForgeConfigSpec.IntValue JUVENILE_TO_ADULT_TICKS;

    // ---- 給餌成長促進 ----
    public static final ForgeConfigSpec.BooleanValue FEEDING_GROWTH_ENABLED;
    /** 給餌1回で短縮される成長時間の割合 (0.0〜1.0) */
    public static final ForgeConfigSpec.DoubleValue FEEDING_GROWTH_REDUCTION;

    // ---- 巣 ----
    public static final ForgeConfigSpec.IntValue NEST_EGG_MIN;
    public static final ForgeConfigSpec.IntValue NEST_EGG_MAX;

    // ---- ティラノステータス（幼体） ----
    public static final ForgeConfigSpec.DoubleValue BABY_MAX_HEALTH;
    public static final ForgeConfigSpec.DoubleValue BABY_ATTACK_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue BABY_MOVE_SPEED;

    // ---- ティラノステータス（若年体） ----
    public static final ForgeConfigSpec.DoubleValue JUVENILE_MAX_HEALTH;
    public static final ForgeConfigSpec.DoubleValue JUVENILE_ATTACK_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue JUVENILE_MOVE_SPEED;

    // ---- ティラノステータス（成体） ----
    public static final ForgeConfigSpec.DoubleValue ADULT_MAX_HEALTH;
    public static final ForgeConfigSpec.DoubleValue ADULT_ATTACK_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue ADULT_MOVE_SPEED;

    // ---- 騎乗中攻撃 ----
    public static final ForgeConfigSpec.BooleanValue MOUNT_ATTACK_ENABLED;

    static {
        BUILDER.push("spawn");
        SPAWN_WEIGHT   = BUILDER.comment("野生スポーン重み").defineInRange("weight", 2, 0, 1000);
        SPAWN_MIN      = BUILDER.comment("1群の最小頭数").defineInRange("min", 1, 1, 10);
        SPAWN_MAX      = BUILDER.comment("1群の最大頭数").defineInRange("max", 1, 1, 10);
        BUILDER.pop();

        BUILDER.push("drops");
        EGG_DROP_CHANCE = BUILDER.comment("討伐時の卵ドロップ率(%)").defineInRange("eggDropChance", 5, 0, 100);
        BUILDER.pop();

        BUILDER.push("growth");
        HATCH_TIME_TICKS        = BUILDER.comment("卵→幼体 孵化時間(tick)").defineInRange("hatchTimeTicks", 24000, 1, Integer.MAX_VALUE);
        BABY_TO_JUVENILE_TICKS  = BUILDER.comment("幼体→若年体 成長時間(tick)").defineInRange("babyToJuvenileTicks", 24000, 1, Integer.MAX_VALUE);
        JUVENILE_TO_ADULT_TICKS = BUILDER.comment("若年体→成体 成長時間(tick)").defineInRange("juvenileToAdultTicks", 48000, 1, Integer.MAX_VALUE);
        FEEDING_GROWTH_ENABLED  = BUILDER.comment("給餌による成長促進を有効にする").define("feedingGrowthEnabled", true);
        FEEDING_GROWTH_REDUCTION = BUILDER.comment("給餌1回で短縮される割合(0.0〜1.0)").defineInRange("feedingGrowthReduction", 0.1, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("nest");
        NEST_EGG_MIN = BUILDER.comment("巣に内包する卵の最小数").defineInRange("nestEggMin", 1, 0, 10);
        NEST_EGG_MAX = BUILDER.comment("巣に内包する卵の最大数").defineInRange("nestEggMax", 2, 1, 10);
        BUILDER.pop();

        BUILDER.push("stats");
        BUILDER.push("baby");
        BABY_MAX_HEALTH    = BUILDER.defineInRange("maxHealth", 40.0, 1.0, 1024.0);
        BABY_ATTACK_DAMAGE = BUILDER.defineInRange("attackDamage", 2.0, 0.0, 1024.0);
        BABY_MOVE_SPEED    = BUILDER.defineInRange("moveSpeed", 0.30, 0.01, 10.0);
        BUILDER.pop();

        BUILDER.push("juvenile");
        JUVENILE_MAX_HEALTH    = BUILDER.defineInRange("maxHealth", 100.0, 1.0, 1024.0);
        JUVENILE_ATTACK_DAMAGE = BUILDER.defineInRange("attackDamage", 6.0, 0.0, 1024.0);
        JUVENILE_MOVE_SPEED    = BUILDER.defineInRange("moveSpeed", 0.30, 0.01, 10.0);
        BUILDER.pop();

        BUILDER.push("adult");
        ADULT_MAX_HEALTH    = BUILDER.defineInRange("maxHealth", 200.0, 1.0, 1024.0);
        ADULT_ATTACK_DAMAGE = BUILDER.defineInRange("attackDamage", 12.0, 0.0, 1024.0);
        ADULT_MOVE_SPEED    = BUILDER.defineInRange("moveSpeed", 0.30, 0.01, 10.0);
        BUILDER.pop();
        BUILDER.pop();

        BUILDER.push("ride");
        MOUNT_ATTACK_ENABLED = BUILDER.comment("騎乗中の噛みつき攻撃を有効にする(Q-05)").define("mountAttackEnabled", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
