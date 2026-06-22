package com.ot.dinoparadise.entity.ai;

import com.ot.dinoparadise.entity.DinoCommand;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;

/** DEFEND/ATTACK コマンド時、飼い主が攻撃した相手を共同ターゲットにする */
public class DinoOwnerHurtTargetGoal extends OwnerHurtTargetGoal {

    private final TyrannosaurusEntity dino;

    public DinoOwnerHurtTargetGoal(TyrannosaurusEntity entity) {
        super(entity);
        this.dino = entity;
    }

    @Override
    public boolean canUse() {
        if (!dino.isTame()) return false;
        DinoCommand cmd = dino.getDinoCommand();
        return (cmd == DinoCommand.DEFEND || cmd == DinoCommand.ATTACK) && super.canUse();
    }
}
