package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DinoParadise.MOD_ID);

    /** クリエイティブ確認用スポーンエッグ（Q-04） */
    public static final RegistryObject<Item> TYRANNOSAURUS_SPAWN_EGG = ITEMS.register(
            "tyrannosaurus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TYRANNOSAURUS, 0x504030, 0x8B4513,
                    new Item.Properties()));

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
