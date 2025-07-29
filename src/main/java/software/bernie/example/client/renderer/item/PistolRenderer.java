package software.bernie.example.client.renderer.item;

import software.bernie.example.client.model.item.PistolModel;
import software.bernie.example.item.PistolItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;

import net.minecraft.client.Minecraft;
import net.geckominecraft.client.renderer.GlStateManager;
import java.util.Collections;

import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Renderer replicating the modern GeckoLib pistol item transforms.
 * Since we do not ship the item model JSON, the transforms are reproduced here.
 */
public class PistolRenderer extends GeoItemRenderer<PistolItem> {
    public PistolRenderer() {
        super(new PistolModel());
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        GL11.glPushMatrix();
        try {
            switch (type) {
                case EQUIPPED_FIRST_PERSON:
                    GL11.glTranslatef(-9.5f / 16f, -6.75f / 16f, -8.75f / 16f);
                    GL11.glRotatef(-90, 0, 1, 0);
                    break;
                case EQUIPPED:
                    GL11.glTranslatef(0, -1f / 16f, -0.25f / 16f);
                    GL11.glScalef(0.3f, 0.3f, 0.3f);
                    GL11.glRotatef(-90, 0, 1, 0);
                    break;
                case INVENTORY:
                    GL11.glTranslatef(-0.25f / 16f, -1.25f / 16f, 0);
                    GL11.glRotatef(32, 0, 0, 1);
                    GL11.glRotatef(-39, 0, 1, 0);
                    GL11.glRotatef(50, 1, 0, 0);
                    GL11.glScalef(0.7f, 0.7f, 0.7f);
                    break;
                case ENTITY:
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                    GL11.glRotatef(-90, 0, 1, 0);
                    break;
                default:
                    break;
            }

            // delegate to base renderer (without additional transforms)
            super.renderItem(type, stack, data);
        } finally {
            GL11.glPopMatrix();
        }
    }

    @Override
    public void render(PistolItem animatable, ItemStack stack) {
        // Override to avoid the +90� Y rotation from the parent implementation
        this.currentItemStack = stack;
        GeoModel model = this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelLocation(animatable));
        AnimationEvent<PistolItem> itemEvent = new AnimationEvent<>(animatable, 0, 0,
            Minecraft.getMinecraft().timer.renderPartialTicks, false, Collections.singletonList(stack));
        this.getGeoModelProvider().setLivingAnimations(animatable, this.getUniqueID(animatable), itemEvent);

        GlStateManager.pushMatrix();
        try {
            GlStateManager.translate(0, 0.01f, 0);
            GlStateManager.translate(0.5f, 0.5f, 0.5f);

            Minecraft.getMinecraft().renderEngine.bindTexture(getTextureLocation(animatable));
            Color renderColor = getRenderColor(animatable, 0f);
            render(model, animatable, 0,
                (float) renderColor.getRed() / 255f,
                (float) renderColor.getGreen() / 255f,
                (float) renderColor.getBlue() / 255f,
                (float) renderColor.getAlpha() / 255f);
        } finally {
            GlStateManager.popMatrix();
        }
    }
}
