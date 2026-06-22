package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.inventory.DinoInventoryContainer;
import com.ot.dinoparadise.inventory.DinoEncyclopediaContainer;
import com.ot.dinoparadise.inventory.NestContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, DinoParadise.MOD_ID);

    /** 巣GUI（卵 × 2スロット） */
    public static final RegistryObject<MenuType<NestContainer>> NEST_MENU =
            MENU_TYPES.register("nest_menu",
                    () -> IForgeMenuType.create((id, playerInv, buf) ->
                            new NestContainer(id, playerInv,
                                    new net.minecraft.world.SimpleContainer(2))));

    /** 恐竜インベントリGUI（サドル + 収納15スロット） */
    public static final RegistryObject<MenuType<DinoInventoryContainer>> DINO_INVENTORY_MENU =
            MENU_TYPES.register("dino_inventory_menu",
                    () -> IForgeMenuType.create((id, playerInv, buf) ->
                            new DinoInventoryContainer(id, playerInv,
                                    new net.minecraft.world.SimpleContainer(16))));

    /** 図鑑GUI */
    public static final RegistryObject<MenuType<DinoEncyclopediaContainer>> ENCYCLOPEDIA_MENU =
            MENU_TYPES.register("encyclopedia_menu",
                    () -> IForgeMenuType.create((id, playerInv, buf) ->
                            new DinoEncyclopediaContainer(id, playerInv)));

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}
