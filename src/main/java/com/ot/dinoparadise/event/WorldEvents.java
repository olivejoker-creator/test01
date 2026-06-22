package com.ot.dinoparadise.event;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.config.DinoConfig;
import com.ot.dinoparadise.entity.GrowthStage;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import com.ot.dinoparadise.registry.ModEntities;
import com.ot.dinoparadise.registry.ModItems;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// バイオームスポーン登録は data/dinoparadise/forge/biome_modifiers/ JSON で行う（Forge 1.20.1 BiomeModifier）

@Mod.EventBusSubscriber(modid = DinoParadise.MOD_ID)
public class WorldEvents {

    /**
     * ティラノサウルス討伐時に低確率で卵をドロップする（F-03）。
     * ドロップ率は Config から参照。
     */
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof TyrannosaurusEntity tyrano)) return;
        if (tyrano.isTame()) return; // 所有個体はドロップしない

        int chance = DinoConfig.EGG_DROP_CHANCE.get();
        if (tyrano.getRandom().nextInt(100) < chance) {
            event.getDrops().add(
                    new net.minecraft.world.entity.item.ItemEntity(
                            tyrano.level(),
                            tyrano.getX(), tyrano.getY(), tyrano.getZ(),
                            new ItemStack(ModItems.TYRANNOSAURUS_EGG_ITEM.get())));
        }
    }

    /**
     * スポーン時に成体として出現させる（野生は成体のみ、F-02）。
     * finalizeSpawnに成体GrowthStageを設定。
     */
    @SubscribeEvent
    public static void onFinalizeSpawn(MobSpawnEvent.FinalizeSpawn event) {
        if (!(event.getEntity() instanceof TyrannosaurusEntity tyrano)) return;
        if (tyrano.isTame()) return;

        // 野生スポーンは常に成体
        tyrano.setGrowthStage(GrowthStage.ADULT);
        // setHealth は属性設定後に呼ぶ
        tyrano.setHealth((float) tyrano.getMaxHealth());
    }
}
