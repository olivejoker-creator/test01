package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DinoParadise.MOD_ID);

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
