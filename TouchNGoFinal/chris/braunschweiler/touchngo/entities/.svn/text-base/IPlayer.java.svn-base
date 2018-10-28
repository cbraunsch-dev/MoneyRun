package chris.braunschweiler.touchngo.entities;

import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;

import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;

/**
 * A player that can handle collisions with other entities.
 * @author chrisbraunschweiler
 *
 */
public interface IPlayer extends IShape {
	
	/**
	 * True if the player is currently moving, false otherwise.
	 * @return True if the player is moving, false otherwise.
	 */
	boolean isMoving();
	
	/**
	 * Sets the player's moving status.
	 * @param moving The status to which the player's moving status should be set.
	 */
	void setIsMoving(boolean moving);
	
	/**
	 * Returns true if the player is alive, false otherwise.
	 * @return True if the player is alive.
	 */
	boolean isAlive();
	
	/**
	 * Kills the Playable Character.
	 */
	void die();
	
	/**
	 * True if the player has already been killed, false otherwise.
	 * @return True if the player has already been killed.
	 */
	boolean hasAlreadyBeenKilled();
	
	/**
	 * Sets the player's already killed status to the one passed in the params.
	 * @param alreadyKilled The new already-killed status of the player.
	 */
	void setAlreadyKilled(boolean alreadyKilled);
	
	/**
	 * Every character sprite which has the ability to swim has the ability to contain a water effects animated sprite.
	 * This sprite handles the animation that shows the player swimming in the water. This prevents the need for
	 * the player's sprite image to contain frames for displaying the swimming animation.
	 * @param waterEffectsSprite
	 */
	void setWaterEffectsAnimatedSprite(AnimatedSprite waterEffectsSprite);
	
	/**
	 * Every character that can swim has a little sprite indicating that it can swim over it. This is that sprite.
	 * @param swimmingAbilityIndicatorSprite
	 */
	void setSwimmingAbilityIndicatorSprite(Sprite swimmingAbilityIndicatorSprite);
	
	/**
	 * Updates the PlayerSprite.
	 */
	void updatePlayerSprite();

	public PlayerAbility getPlayerAbility();
}
