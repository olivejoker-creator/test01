package com.ot.dinoparadise.client.renderer;

import com.ot.dinoparadise.DinoParadise;
import com.ot.dinoparadise.client.model.TyrannosaurusModel;
import com.ot.dinoparadise.entity.TyrannosaurusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TyrannosaurusRenderer extends MobRenderer<TyrannosaurusEntity, TyrannosaurusModel> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(DinoParadise.MOD_ID, "textures/entity/tyrannosaurus.png");

    public TyrannosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new TyrannosaurusModel(
                context.bakeLayer(TyrannosaurusModel.LAYER_LOCATION)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(TyrannosaurusEntity entity) {
        return TEXTURE;
    }
}
