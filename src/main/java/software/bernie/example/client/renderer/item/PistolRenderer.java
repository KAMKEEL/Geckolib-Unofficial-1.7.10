package software.bernie.example.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;
import software.bernie.example.client.model.item.PistolModel;
import software.bernie.example.item.PistolItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class PistolRenderer extends GeoItemRenderer<PistolItem> {
    public PistolRenderer() {
        super(new PistolModel());
        this.withScale(1.0f);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
        GL11.glPushMatrix();
        try {
            if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslatef(-0.6f, -0.42f, -0.55f);
            } else if (type == ItemRenderType.EQUIPPED) {
                GL11.glTranslatef(0f, -0.0625f, -0.0156f);
                GL11.glScalef(0.3f, 0.3f, 0.3f);
            }
            super.renderItem(type, itemStack, data);
        } finally {
            GL11.glPopMatrix();
        }
    }
}
