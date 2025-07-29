package software.bernie.example.client.renderer.item;

import software.bernie.example.client.model.item.PistolModel;
import software.bernie.example.item.PistolItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class PistolRenderer extends GeoItemRenderer<PistolItem> {
    public PistolRenderer() {
        super(new PistolModel());
        this.withScale(1.0f);
    }

    // Default handling from GeoItemRenderer provides the correct transform
}
