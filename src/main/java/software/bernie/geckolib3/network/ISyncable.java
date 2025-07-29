package software.bernie.geckolib3.network;

/**
 * Simple contract for objects that support synced animations.
 */
public interface ISyncable {
    /**
     * Handle a synced animation update.
     *
     * @param id    Unique animation holder id
     * @param state Animation state identifier
     */
    void onAnimationSync(int id, int state);

    /**
     * Unique key used to look up this syncable on the client.
     */
    default String getSyncKey() {
        return this.getClass().getName();
    }
}
