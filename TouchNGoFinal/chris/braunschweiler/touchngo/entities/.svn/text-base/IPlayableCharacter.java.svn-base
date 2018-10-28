package chris.braunschweiler.touchngo.entities;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.Text;

import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;

/**
 * The interface for a playable character.
 * @author chrisbraunschweiler
 *
 */
public interface IPlayableCharacter extends IPlayer{
	/**
	 * Called when the player needs to collect the given collectable.
	 * @param collectable to be collected.
	 */
	void collect(ICollectable collectable);
	
	/**
	 * Called when the player collects a money bag.
	 * @param moneyBag The money bag which the player collects.
	 */
	void collectMoneyBag(MoneyBag moneyBag);
	
	/**
	 * The player drops his money bag. The money bag then goes back to its original position.
	 */
	void dropMoneyBag();
	
	/**
	 * Gets the money bag the player is currently carrying.
	 * @return The moneybag which the player is currently carrying.
	 */
	MoneyBag getPlayerMoneyBag();
	
	/**
	 * Sets the money bag the player is currently carrying.
	 * @param moneyBag The moneybag the player is currently carrying.
	 */
	void setPlayerMoneyBag(MoneyBag moneyBag);
	
	/**
	 * Returns true if the given playable character is locked. Locked means, the player appears in the level but
	 * it cannot be moved until a given number of coins has been collected.
	 * @return True if the given playable character is locked, false otherwise.
	 */
	boolean isLocked();
	
	/**
	 * Attempts to unlock the given playable character. The total number of coins collected thus far is passed into the
	 * method. The method then determines if the passed amount is enough to unlock the playable character.
	 * @param currentNumberOfCoinsCollected The total number of coins collected thus far in the game.
	 */
	void tryToUnlock(int currentNumberOfCoinsCollected);
	
	/**
	 * Returns the player's player ability (ie: swimming, flying etc).
	 * @return The player's ability.
	 */
	PlayerAbility getPlayerAbility();
	
	/**
	 * Returns the player's target x position.
	 * @return The player's target x position.
	 */
	int getPlayerTargetPositionX();
	
	/**
	 * Returns the player's target y position.
	 * @return The player's target y position.
	 */
	int getPlayerTargetPositionY();
	
	/**
	 * Stores the player's target position in the player.
	 * @param positionX The target position.
	 */
	void setPlayerTargetPositionX(int positionX);
	
	/**
	 * Stores the player's target position in the player.
	 * @param positionY The target position.
	 */
	void setPlayerTargetPositionY(int positionY);
	
	/**
	 * Highlights the Playayable Character.
	 */
	void highlightPlayer();
	
	/**
	 * Unhighlights (couldn't think of a better word) the playable character.
	 */
	void unHighlightPlayer();
	
	/**
	 * Shows the locked animation of the playable character.
	 */
	void showLockedAnimation();
	
	/**
	 * Moves the playable character to the given destination in the given time.
	 * @param destinationX The x component of the destination.
	 * @param destinationY The y component of the destination.
	 * @param timeNeededToMove The time needed by the playable character to move to the given destination.
	 */
	void movePlayer(int destinationX, int destinationY, float timeNeededToMove);
	
	/**
	 * Moves the player off of its current axis of movement while the player is still on his way to his current destination.
	 * @param destinationX The destination x position. This is between the player's current x position and his destination x position.
	 * @param destinationY The destination y position. This is between the player's current y position and his destination y position.
	 * @param timeNeededToMove The time needed by the playable character to move to the given destination.
	 */
	void movePlayerOffAxisMidMove(int sourceX, int sourceY, int destinationX, int destinationY, float timeNeededToMove);
	
	/**
	 * Moves the player along the same axis of movement while the player is stil on his way to his current destination.
	 * @param destinationX The destination x position.
	 * @param destinationY The destination y position.
	 * @param timeNeededToMove
	 */
	void movePlayerAlongSameAxisMidMove(int destinationX, int destinationY, float timeNeededToMove);

	public void updatePlayerSprite();
	
	/**
	 * Returns the text that displays the nr of coins required to unlock the player.
	 * @return The text used to display the nr of coins required to unlock the player.
	 */
	public Text getNrOfCoinsRequiredToUnlockPlayerText();
}
