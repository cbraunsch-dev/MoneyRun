package chris.braunschweiler.touchngo.entities;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

/**
 * Listens for position changes of a MoveableAnimatedSprite.
 * @author chrisbraunschweiler
 *
 */
public interface IPlayerListener {	
	/**
	 * Notifies the listener of the change in position of the given collidable player.
	 * @param positionX The current x position of the sprite.
	 * @param positionY The current y position of the sprite.
	 * @param player The collidable player whose position has changed.
	 */
	void onPositionChanged(float positionX, float positionY, IPlayer player);
}
