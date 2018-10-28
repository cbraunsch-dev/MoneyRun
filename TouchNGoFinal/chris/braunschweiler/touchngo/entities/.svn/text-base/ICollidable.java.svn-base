package chris.braunschweiler.touchngo.entities;

/**
 * Defines an entity with which the player can collide.
 * @author chrisbraunschweiler
 *
 */
public interface ICollidable extends IEntity{
	
	/**
	 * The type of collidable (ie: Goal, switch etc).
	 * @author chrisbraunschweiler
	 *
	 */
	enum CollidableType{
		GOAL,
		SWITCH,
		BANK
	}
	
	/**
	 * Returns the type of the collidable.
	 * @return
	 */
	public CollidableType getType();
	
	/**
	 * Called when playable character passed in parameters collides with collidable.
	 */
	public void collideWith(IPlayableCharacter player);
	
	/**
	 * Registers the given listener.
	 * @param listener The listener which should listen for collisions with the collidable.
	 */
	public void registerCollidedListener(ICollidedListener listener);
}
