package software.bernie.geckolib3.network;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Minimal network manager mirroring newer GeckoLib versions.
 */
public class GeckoLibNetwork {
    private static final Map<String, Supplier<ISyncable>> SYNCABLES = new HashMap<String, Supplier<ISyncable>>();
    private static final SimpleNetworkWrapper CHANNEL = new SimpleNetworkWrapper("geckolib_sync");

    public static void registerMessages() {
        CHANNEL.registerMessage(PacketSyncAnimation.class, PacketSyncAnimation.class, 4, Side.CLIENT);
    }

    public static void syncAnimationToAll(ISyncable syncable, int id, int state) {
        CHANNEL.sendToAll(new PacketSyncAnimation(syncable.getSyncKey(), id, state));
    }

    public static void syncAnimationToPlayer(ISyncable syncable, int id, int state, cpw.mods.fml.common.network.simpleimpl.MessageContext ctx) {
        CHANNEL.sendTo(new PacketSyncAnimation(syncable.getSyncKey(), id, state), ctx.getServerHandler().playerEntity);
    }

    public static ISyncable getSyncable(String key) {
        Supplier<ISyncable> supplier = SYNCABLES.get(key);
        return supplier == null ? null : supplier.get();
    }

    public static void registerSyncable(ISyncable syncable) {
        SYNCABLES.putIfAbsent(syncable.getSyncKey(), () -> syncable);
    }
}
