package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Items {
        /** 給餌対象：生の各種肉・腐肉 (03_仕様書 7-2) */
        public static final TagKey<Item> TYRANNOSAURUS_FOOD =
                ItemTags.create(new ResourceLocation(DinoParadise.MOD_ID, "tyrannosaurus_food"));
    }

    public static class Blocks {
        /** 孵化熱源ブロック (03_仕様書 7-2) */
        public static final TagKey<Block> INCUBATOR_HEAT_SOURCE =
                BlockTags.create(new ResourceLocation(DinoParadise.MOD_ID, "incubator_heat_source"));
    }
}
