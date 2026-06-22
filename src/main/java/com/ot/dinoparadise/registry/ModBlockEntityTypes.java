package com.ot.dinoparadise.registry;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.block.entity.TyrannosaurusEggBlockEntity;
import com.ot.dinoparadise.block.entity.TyrannosaurusNestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DinoParadise.MOD_ID);

    public static final RegistryObject<BlockEntityType<TyrannosaurusEggBlockEntity>> TYRANNOSAURUS_EGG_BE =
            BLOCK_ENTITY_TYPES.register("tyrannosaurus_egg_be",
                    () -> BlockEntityType.Builder
                            .of(TyrannosaurusEggBlockEntity::new, ModBlocks.TYRANNOSAURUS_EGG.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<TyrannosaurusNestBlockEntity>> TYRANNOSAURUS_NEST_BE =
            BLOCK_ENTITY_TYPES.register("tyrannosaurus_nest_be",
                    () -> BlockEntityType.Builder
                            .of(TyrannosaurusNestBlockEntity::new, ModBlocks.TYRANNOSAURUS_NEST.get())
                            .build(null));

    public static void register(net.minecraftforge.eventbus.api.IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
