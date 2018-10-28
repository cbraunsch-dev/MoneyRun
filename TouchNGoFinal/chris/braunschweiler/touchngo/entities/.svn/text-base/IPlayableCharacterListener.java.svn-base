package chris.braunschweiler.touchngo.entities;

/**
 * Defines a listener interface for the Playable Characters.
 * @author chrisbraunschweiler
 *
 */
public interface IPlayableCharacterListener extends IPlayerListener{
	/**
	 * Notifies the listener that the player collected a collectable.
	 * @param collectable
	 */
	void onCollectableCollected(ICollectable collectable);
	
	/**
	 * Notifies the listener that the player collected a moneybag.
	 * @param moneyBag The moneybag which was collected.
	 * @param player The player who collected the bag.
	 */
	void onMoneyBagCollected(MoneyBag moneyBag, IPlayableCharacter player);
	
	/**
	 * Notifies the listener that the player is dying.
	 * @param player The player which has died.
	 */
	void onDying(IPlayableCharacter player);
}
