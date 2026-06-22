package com.ot.dinoparadise.client;

import com.ot.dinoparadise.client.model.TyrannosaurusModel;
import com.ot.dinoparadise.client.renderer.TyrannosaurusRenderer;
import com.ot.dinoparadise.client.screen.DinoEncyclopediaScreen;
import com.ot.dinoparadise.registry.ModEntities;
import com.ot.dinoparadise.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = com.ot.dinoparadise.DinoParadise.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TYRANNOSAURUS.get(), TyrannosaurusRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TyrannosaurusModel.LAYER_LOCATION, TyrannosaurusModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.ENCYCLOPEDIA_MENU.get(), DinoEncyclopediaScreen::new);
        });
    }
}
