package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.item.DinoEncyclopediaItem;
import com.ot.dinoparadise.item.DinoWhistleItem;
import com.ot.dinoparadise.item.TyrannosaurusEggBlockItem;
import com.ot.dinoparadise.item.TyrannosaurusSaddleItem;
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

    /** ティラノの卵（設置時に設置者UUID記録。クラフト不可・巣/ドロップのみ入手）*/
    public static final RegistryObject<Item> TYRANNOSAURUS_EGG_ITEM = ITEMS.register(
            "tyrannosaurus_egg",
            () -> new TyrannosaurusEggBlockItem(ModBlocks.TYRANNOSAURUS_EGG.get(),
                    new Item.Properties().stacksTo(1)));

    /** ティラノサドル（Task011-Task012 連動）*/
    public static final RegistryObject<Item> TYRANNOSAURUS_SADDLE = ITEMS.register(
            "tyrannosaurus_saddle",
            () -> new TyrannosaurusSaddleItem(new Item.Properties().stacksTo(1)));

    /** ホイッスル（Task010 連動）*/
    public static final RegistryObject<Item> DINO_WHISTLE = ITEMS.register(
            "dino_whistle",
            () -> new DinoWhistleItem(new Item.Properties().stacksTo(1)));

    /** 図鑑（Task014 連動）*/
    public static final RegistryObject<Item> DINO_ENCYCLOPEDIA = ITEMS.register(
            "dino_encyclopedia",
            () -> new DinoEncyclopediaItem(new Item.Properties().stacksTo(1)));

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
