package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.world.TyrannosaurusNestFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, DinoParadise.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> TYRANNOSAURUS_NEST_FEATURE =
            FEATURES.register("tyrannosaurus_nest",
                    () -> new TyrannosaurusNestFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
