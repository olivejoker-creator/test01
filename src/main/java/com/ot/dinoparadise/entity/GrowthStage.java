package com.ot.dinoparadise.entity;

import com.ot.dinoparadise.config.DinoConfig;

/** ティラノサウルスの成長段階。NBT に名前で保存する。 */
public enum GrowthStage {
    BABY,
    JUVENILE,
    ADULT;

    public double getMaxHealth() {
        return switch (this) {
            case BABY     -> DinoConfig.BABY_MAX_HEALTH.get();
            case JUVENILE -> DinoConfig.JUVENILE_MAX_HEALTH.get();
            case ADULT    -> DinoConfig.ADULT_MAX_HEALTH.get();
        };
    }

    public double getAttackDamage() {
        return switch (this) {
            case BABY     -> DinoConfig.BABY_ATTACK_DAMAGE.get();
            case JUVENILE -> DinoConfig.JUVENILE_ATTACK_DAMAGE.get();
            case ADULT    -> DinoConfig.ADULT_ATTACK_DAMAGE.get();
        };
    }

    public double getMoveSpeed() {
        return switch (this) {
            case BABY     -> DinoConfig.BABY_MOVE_SPEED.get();
            case JUVENILE -> DinoConfig.JUVENILE_MOVE_SPEED.get();
            case ADULT    -> DinoConfig.ADULT_MOVE_SPEED.get();
        };
    }

    public float getWidth() {
        return switch (this) {
            case BABY     -> 0.7F;
            case JUVENILE -> 1.3F;
            case ADULT    -> 2.0F;
        };
    }

    public float getHeight() {
        return switch (this) {
            case BABY     -> 1.0F;
            case JUVENILE -> 2.0F;
            case ADULT    -> 3.0F;
        };
    }

    public boolean canRide()        { return this == ADULT; }
    public boolean hasInventory()   { return this == ADULT; }
    public boolean isAdult()        { return this == ADULT; }

    /** 次のステージを返す。ADULT の場合は自身 */
    public GrowthStage next() {
        return switch (this) {
            case BABY     -> JUVENILE;
            case JUVENILE -> ADULT;
            case ADULT    -> ADULT;
        };
    }

    /** 現段階の成長に必要な tick 数を返す */
    public int growthTicks() {
        return switch (this) {
            case BABY     -> DinoConfig.BABY_TO_JUVENILE_TICKS.get();
            case JUVENILE -> DinoConfig.JUVENILE_TO_ADULT_TICKS.get();
            case ADULT    -> 0;
        };
    }
}
