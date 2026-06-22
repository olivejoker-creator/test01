package com.ot.dinoparadise;

import com.ot.dinoparadise.config.DinoConfig;
import com.ot.dinoparadise.registry.ModBlockEntityTypes;
import com.ot.dinoparadise.registry.ModBlocks;
import com.ot.dinoparadise.registry.ModEntities;
import com.ot.dinoparadise.registry.ModItems;
import com.ot.dinoparadise.registry.ModMenuTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DinoParadise.MOD_ID)
public class DinoParadise {

    public static final String MOD_ID = "dinoparadise";
    public static final Logger LOGGER = LogManager.getLogger();

    public DinoParadise() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DinoConfig.SPEC, "dinoparadise-common.toml");

        LOGGER.info("Dino Paradise initializing...");
    }
}
