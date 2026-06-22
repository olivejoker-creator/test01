package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DinoParadise.MOD_ID);

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
