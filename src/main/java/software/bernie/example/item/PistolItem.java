package software.bernie.example.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

/**
 * Simplified example based on the modern GeckoLib pistol item.
 */
public class PistolItem extends Item implements GeoItem {
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "controller";

    public PistolItem() {
        super();
        this.setMaxStackSize(1);
        this.setCreativeTab(GeckoLibMod.getGeckolibItemGroup());
    }

    private <P extends Item & GeoItem> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<PistolItem>(this, controllerName, 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player) {
        if (worldIn.isRemote) {
            AnimationController<?> controller = GeckoLibUtil.getControllerForStack(this.factory, stack, controllerName);
            if (controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("firing", false));
            }
        }
        GeoItem.getOrAssignId(stack);
        return super.onItemRightClick(stack, worldIn, player);
    }
}
