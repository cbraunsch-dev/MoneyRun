package chris.braunschweiler.touchngo.entities;

/**
 * A collectable entity such as a powerup.
 * @author chrisbraunschweiler
 *
 */
public interface ICollectable extends IEntity{
	
	/**
	 * The type of the collectable.
	 * @author chrisbraunschweiler
	 *
	 */
	enum CollectableType{
		MONEYBAG
	}
	
	/**
	 * Gets the type of the collectable.
	 * @return
	 */
	CollectableType getType();
}
