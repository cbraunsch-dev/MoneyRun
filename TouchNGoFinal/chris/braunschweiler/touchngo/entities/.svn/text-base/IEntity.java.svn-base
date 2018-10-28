package chris.braunschweiler.touchngo.entities;

/**
 * Any kind of entity in a level such as an obstacle, a collectable or a collidable entity.
 * @author chrisbraunschweiler
 *
 */
public interface IEntity {
	
	/**
	 * Returns the row of the collidable.
	 * @return The row in the tmx map at which the collidable is located.
	 */
	public int getRow();
	
	/**
	 * Returns the column of the collidable.
	 * @return The column in the tmx map at which the collidable is located.
	 */
	public int getCol();
	
	/**
	 * Returns the global id of the collidable.
	 * @return The global id.
	 */
	public int getGlobalId();
	
	/*
	 * An identifier of the entity used to find it in case its state needs to be changed during the game.
	 * This is used when the player runs over a switch and triggers the toggling of the active flag of the affected entities. The affected
	 * entities are identified by their ObjectIdentifier.
	 */
	public String getObjectIdentifer();
	
	/**
	 * Toggles the entity's active flag.
	 */
	public void toggleActiveFlag();
	
	/**
	 * Returns true if the entity is active, false otherwise.
	 * @return True if the entity is active, false otherwise.
	 */
	public boolean isActive();
}
