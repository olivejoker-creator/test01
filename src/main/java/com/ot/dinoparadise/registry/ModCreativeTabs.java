package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DinoParadise.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DinoParadise.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DINO_PARADISE_TAB =
            CREATIVE_MODE_TABS.register("dino_paradise_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.TYRANNOSAURUS_SPAWN_EGG.get()))
                    .title(Component.translatable("itemGroup.dinoparadise.dino_paradise"))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    @SubscribeEvent
    public static void buildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == DINO_PARADISE_TAB.getKey()) {
            event.accept(ModItems.TYRANNOSAURUS_SPAWN_EGG.get());
            event.accept(ModItems.TYRANNOSAURUS_EGG_ITEM.get());
            event.accept(ModItems.TYRANNOSAURUS_SADDLE.get());
            event.accept(ModItems.DINO_WHISTLE.get());
            event.accept(ModItems.DINO_ENCYCLOPEDIA.get());
        }
    }
}
