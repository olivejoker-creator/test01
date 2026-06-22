package com.ot.dinoparadise;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DinoParadise.MOD_ID)
public class DinoParadise {

    public static final String MOD_ID = "dinoparadise";
    public static final Logger LOGGER = LogManager.getLogger();

    public DinoParadise() {
        LOGGER.info("Dino Paradise initializing...");
    }
}
