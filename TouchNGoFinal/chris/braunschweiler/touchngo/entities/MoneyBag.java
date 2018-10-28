package chris.braunschweiler.touchngo.entities;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

/**
 * A money bag which can be picked up by a player.
 * @author chrisbraunschweiler
 *
 */
public class MoneyBag extends AnimatedSprite{
	
	private int numberOfCoinsInBag;
	private boolean alreadyDeposited;
	private boolean pickedUp;
	private float startingPositionX;
	private float startingPositionY;
	
	public MoneyBag(){
		super(0, 0, 0, 0, null);
		this.numberOfCoinsInBag = 0;
		this.alreadyDeposited = false;
		this.pickedUp = false;
		this.startingPositionX = 0;
		this.startingPositionY = 0;
		animate(new long[]{200, 200}, 0, 1, true);
	}
	
	public MoneyBag(float pX, float pY, TiledTextureRegion textureRegion, int numberOfCoinsInBag){
		super(pX, pY, textureRegion);
		this.numberOfCoinsInBag = numberOfCoinsInBag;
		this.alreadyDeposited = false;
		this.pickedUp = false;
		this.startingPositionX = pX;
		this.startingPositionY = pY;
		animate(new long[]{200, 200}, 0, 1, true);
	}
	
	public int getNumberOfCoinsInBag(){
		return this.numberOfCoinsInBag;
	}
	
	public boolean isAlreadyDeposited(){
		return this.alreadyDeposited;
	}
	
	public void setAlreadyDeposited(boolean alreadyDeposited){
		this.alreadyDeposited = alreadyDeposited;
	}
	
	public boolean isPickedUp(){
		return this.pickedUp;
	}
	
	public void setPickedUp(boolean pickedUp){
		this.pickedUp = pickedUp;
	}

	public float getStartingPositionX() {
		return startingPositionX;
	}

	public float getStartingPositionY() {
		return startingPositionY;
	}
}
