package software.bernie.geckolib3.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/**
 * Basic {@link IAnimatable} extension for items with persistent animation data.
 * Provides unique id assignment per ItemStack similar to newer GeckoLib versions.
 */
public interface GeoItem extends IAnimatable {
    String ID_TAG = "GeckoLibItemID";

    /**
     * Gets the unique ID stored on the stack or -1 if not present.
     */
    static int getId(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(ID_TAG)) {
            return stack.getTagCompound().getInteger(ID_TAG);
        }
        return -1;
    }

    /**
     * Gets a unique ID for this stack, assigning a new one if none exists.
     */
    static int getOrAssignId(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tag = stack.getTagCompound();
        if (!tag.hasKey(ID_TAG)) {
            int id = (int) (System.nanoTime() & 0x7FFFFFFF);
            tag.setInteger(ID_TAG, id);
        }
        return tag.getInteger(ID_TAG);
    }

    /**
     * Convenience for retrieving the factory from the animatable instance.
     */
    @Override
    AnimationFactory getFactory();
}
