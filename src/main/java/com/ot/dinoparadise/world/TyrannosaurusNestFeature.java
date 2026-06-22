package com.ot.dinoparadise.world;

import com.mojang.serialization.Codec;
import com.ot.dinoparadise.block.entity.TyrannosaurusNestBlockEntity;
import com.ot.dinoparadise.config.DinoConfig;
import com.ot.dinoparadise.registry.ModBlocks;
import com.ot.dinoparadise.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * ティラノサウルスの巣を自然生成する Feature（Task009）。
 * 地表に配置し、内部に卵 1〜NEST_EGG_MAX 個を内包する。
 */
public class TyrannosaurusNestFeature extends Feature<NoneFeatureConfiguration> {

    public TyrannosaurusNestFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        // 地表に巣ブロックを配置
        if (!level.isEmptyBlock(origin)) return false;
        if (!level.getBlockState(origin.below()).isSolidRender(level, origin.below())) return false;

        level.setBlock(origin, ModBlocks.TYRANNOSAURUS_NEST.get().defaultBlockState(), 2);

        BlockEntity be = level.getBlockEntity(origin);
        if (be instanceof TyrannosaurusNestBlockEntity nestBE) {
            int eggMin = DinoConfig.NEST_EGG_MIN.get();
            int eggMax = DinoConfig.NEST_EGG_MAX.get();
            int eggCount = eggMin + context.random().nextInt(Math.max(1, eggMax - eggMin + 1));
            eggCount = Math.min(eggCount, nestBE.getContainer().getContainerSize());

            for (int i = 0; i < eggCount; i++) {
                nestBE.getContainer().setItem(i, new ItemStack(ModItems.TYRANNOSAURUS_EGG_ITEM.get()));
            }
            nestBE.setChanged();
        }

        return true;
    }
}
