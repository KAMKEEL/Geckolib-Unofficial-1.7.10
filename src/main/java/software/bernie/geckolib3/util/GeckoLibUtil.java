package software.bernie.geckolib3.util;

import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoItem;

import java.util.Objects;

public class GeckoLibUtil {
    public static int getIDFromStack(ItemStack stack) {
        if (stack.getItem() instanceof GeoItem) {
            return GeoItem.getOrAssignId(stack);
        }
        return Objects.hash(stack.getItem(), stack.stackSize,
            stack.hasTagCompound() ? stack.getTagCompound().toString() : 1);
    }

    @SuppressWarnings("rawtypes")
    public static AnimationController getControllerForStack(AnimationFactory factory, ItemStack stack,
                                                            String controllerName) {
        return factory.getOrCreateAnimationData(getIDFromStack(stack)).getAnimationControllers().get(controllerName);
    }
}
