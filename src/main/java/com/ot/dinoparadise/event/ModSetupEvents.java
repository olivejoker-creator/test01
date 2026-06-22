package com.ot.dinoparadise.event;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.registry.ModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DinoParadise.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetupEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
                ModEntities.TYRANNOSAURUS.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, level, spawnType, pos, random) -> {
                    // 地表の草・砂・土・岩など一般的な地面にスポーン可
                    return level.getBlockState(pos.below()).isSolidRender(level, pos.below());
                },
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }
}
