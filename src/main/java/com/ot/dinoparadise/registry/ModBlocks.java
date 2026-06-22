package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.block.TyrannosaurusEggBlock;
import com.ot.dinoparadise.block.TyrannosaurusNestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DinoParadise.MOD_ID);

    public static final RegistryObject<Block> TYRANNOSAURUS_EGG = BLOCKS.register(
            "tyrannosaurus_egg",
            () -> new TyrannosaurusEggBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_WHITE)
                            .strength(0.5F)
                            .sound(SoundType.STONE)
                            .noOcclusion()));

    public static final RegistryObject<Block> TYRANNOSAURUS_NEST = BLOCKS.register(
            "tyrannosaurus_nest",
            () -> new TyrannosaurusNestBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .strength(1.5F)
                            .sound(SoundType.WOOD)));  // 回収不可はロジックで制御（Q-03）

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
