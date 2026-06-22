package com.ot.dinoparadise.entity.ai;

import com.ot.dinoparadise.entity.DinoCommand;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;

/** DEFEND コマンド時のみ飼い主を攻撃した相手をターゲットにする */
public class DinoOwnerHurtByTargetGoal extends OwnerHurtByTargetGoal {

    private final TyrannosaurusEntity dino;

    public DinoOwnerHurtByTargetGoal(TyrannosaurusEntity entity) {
        super(entity);
        this.dino = entity;
    }

    @Override
    public boolean canUse() {
        return dino.isTame()
                && dino.getDinoCommand() == DinoCommand.DEFEND
                && super.canUse();
    }
}
