package com.ot.dinoparadise.entity.ai;

import com.ot.dinoparadise.entity.DinoCommand;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

/** FOLLOW コマンド時のみ飼い主追従を行うゴール */
public class DinoFollowOwnerGoal extends FollowOwnerGoal {

    private final TyrannosaurusEntity dino;

    public DinoFollowOwnerGoal(TyrannosaurusEntity entity, double speed, float minDist, float maxDist, boolean canFly) {
        super(entity, speed, minDist, maxDist, canFly);
        this.dino = entity;
    }

    @Override
    public boolean canUse() {
        return dino.isTame()
                && !dino.isOrderedToSit()
                && dino.getDinoCommand() == DinoCommand.FOLLOW
                && super.canUse();
    }
}
