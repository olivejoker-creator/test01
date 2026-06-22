package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DinoParadise.MOD_ID);

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
