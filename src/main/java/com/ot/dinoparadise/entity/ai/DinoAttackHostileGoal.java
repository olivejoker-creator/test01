package com.ot.dinoparadise.entity.ai;

import com.ot.dinoparadise.entity.DinoCommand;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

/**
 * ATTACK コマンド時、索敵範囲内の敵対Mob（MONSTER カテゴリ）を能動攻撃する。
 * 村人・プレイヤー・パッシブMobは対象外（Q-07）。
 */
public class DinoAttackHostileGoal extends NearestAttackableTargetGoal<Mob> {

    private final TyrannosaurusEntity dino;

    public DinoAttackHostileGoal(TyrannosaurusEntity entity) {
        super(entity, Mob.class, true, e ->
                e.getType().getCategory() == MobCategory.MONSTER && !(e instanceof Player));
        this.dino = entity;
    }

    @Override
    public boolean canUse() {
        return dino.isTame()
                && dino.getDinoCommand() == DinoCommand.ATTACK
                && super.canUse();
    }
}
