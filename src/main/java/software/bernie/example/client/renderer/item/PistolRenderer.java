package software.bernie.example.client.renderer.item;

import software.bernie.example.client.model.item.PistolModel;
import software.bernie.example.item.PistolItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
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
                    break;
                case EQUIPPED:
                    GL11.glTranslatef(0, -1f / 16f, -0.25f / 16f);
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
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
                    break;
                default:
                    break;
            }

            // delegate to base renderer applying default rotation
            super.renderItem(type, stack, data);
        } finally {
            GL11.glPopMatrix();
        }
    }
}
