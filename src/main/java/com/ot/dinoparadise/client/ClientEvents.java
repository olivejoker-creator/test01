package com.ot.dinoparadise.client;

import com.ot.dinoparadise.client.model.TyrannosaurusModel;
import com.ot.dinoparadise.client.renderer.TyrannosaurusRenderer;
import com.ot.dinoparadise.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
}
