package chris.braunschweiler.touchngo.entities;

/**
 * Listens for collisions with a collidable.
 * @author chrisbraunschweiler
 *
 */
public interface ICollidedListener {
	
	/**
	 * Notifies the Listener that a collision between the given player and the given collidable has occurred.
	 * @param collidable that was collided with.
	 * @param player that collided with the collidable.
	 * @returns True if successful, false otherwise.
	 */
	public boolean collided(ICollidable collidable, IPlayer player);
	
	/**
	 * Notifies listener that a playable character collided with a bank.
	 * @param bank
	 * @param player
	 */
	public void collisionBetweenPlayerAndBank(ICollidable bank, IPlayableCharacter player);
	
	/**
	 * Notifies listene that a playable character collided with a goal.
	 * @param goal
	 * @param player
	 */
	public void collisionBetweenPlayerAndGoal(ICollidable goal, IPlayableCharacter player);
}
