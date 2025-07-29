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
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

/**
 * Simplified example based on the modern GeckoLib pistol item.
 */
public class PistolItem extends Item implements GeoItem, ISyncable {
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "controller";
    public static final int ANIM_FIRE = 0;

    public PistolItem() {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(201);
        this.setCreativeTab(GeckoLibMod.getGeckolibItemGroup());
        this.setFull3D();
        GeckoLibNetwork.registerSyncable(this);
    }

    private <P extends Item & GeoItem> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        // Update every tick so the firing animation triggers immediately
        data.addAnimationController(new AnimationController<PistolItem>(this, controllerName, 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            GeckoLibNetwork.syncAnimationToAll(this, GeoItem.getOrAssignId(stack), ANIM_FIRE);
        }
        return super.onItemRightClick(stack, worldIn, player);
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM_FIRE) {
            AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
            if (controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("firing", false));
            }
        }
    }
}
