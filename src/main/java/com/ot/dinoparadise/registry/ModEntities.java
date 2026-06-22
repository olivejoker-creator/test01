package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DinoParadise.MOD_ID);

    public static final RegistryObject<EntityType<TyrannosaurusEntity>> TYRANNOSAURUS =
            ENTITY_TYPES.register("tyrannosaurus",
                    () -> EntityType.Builder.<TyrannosaurusEntity>of(TyrannosaurusEntity::new, MobCategory.MONSTER)
                            .sized(2.0F, 3.0F)   // 成体サイズ（幅×高）。成長後に Task004 で動的変更
                            .build("tyrannosaurus"));

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
