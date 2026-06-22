package com.ot.dinoparadise.event;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import com.ot.dinoparadise.registry.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DinoParadise.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.TYRANNOSAURUS.get(), TyrannosaurusEntity.createAttributes().build());
    }
}
