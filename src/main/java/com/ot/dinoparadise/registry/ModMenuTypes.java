package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, DinoParadise.MOD_ID);

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}
