package chris.braunschweiler.touchngo.entities;

import java.util.List;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import chris.braunschweiler.touchngo.common.CommonDataManager;
import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;
import chris.braunschweiler.touchngo.exceptions.PlayerMovementException;

/**
 * An animated sprite which can notify any listeners of its movement.
 * @author chrisbraunschweiler
 */
public abstract class PlayerSprite extends AnimatedSprite implements IPlayer{
	
	private IPlayerListener listener;
	private boolean moving;
	private MoneyBag moneyBag;
	private boolean alive;
	private boolean alreadyKilled;
	protected float originalX;
	protected float originalY;
	private Sprite swimmingAbilityIndicatorSprite;
	private AnimatedSprite waterEffectsAnimatedSprite;
	protected boolean facingRight;
	protected boolean facingLeft;
	protected boolean facingUp;
	protected boolean facingDown;
	private boolean animatingWaterEffectRight;
	private boolean animatingWaterEffectLeft;
	private boolean animatingWaterEffectUp;
	private boolean animatingWaterEffectDown;
	private boolean animatingRight;
	private boolean animatingLeft;
	private boolean animatingUp;
	private boolean animatingDown;
	private PlayerAbility playerAbility;
	
	public PlayerSprite(){
		super(0, 0, 0, 0, null);
		this.moneyBag = null;
		this.moving = false;
		this.listener = null;
		this.alive = true;
		this.alreadyKilled = false;
		this.originalX = 0;
		this.originalY = 0;
		this.swimmingAbilityIndicatorSprite = null;
		this.waterEffectsAnimatedSprite = null;
		this.facingRight = false;
		this.facingLeft = false;
		this.facingUp = true;
		this.facingDown = false;
		this.animatingWaterEffectRight = false;
		this.animatingWaterEffectLeft = false;
		this.animatingWaterEffectUp = false;
		this.animatingWaterEffectDown = false;
		this.animatingRight = false;
		this.animatingLeft = false;
		this.animatingUp = false;
		this.animatingDown = false;
		this.playerAbility = PlayerAbility.NONE;
	}
	
	public PlayerSprite(float pX, 
			float pY, 
			TiledTextureRegion textureRegion,
			IPlayerListener listener, PlayerAbility playerAbility){
		super(pX, pY, textureRegion);
		this.listener = listener;
		this.moving = false;
		this.moneyBag = null;
		this.alive = true;
		this.alreadyKilled = false;
		this.originalX = pX;
		this.originalY = pY;
		this.facingRight = false;
		this.facingLeft = false;
		this.facingUp = true;
		this.facingDown = false;
		this.playerAbility = playerAbility;
	}
	
	public boolean isMoving(){
		return this.moving;
	}
	
	public void setIsMoving(boolean moving){
		this.moving = moving;
	}
	
	
	protected void onPositionChanged(){
		if(this.listener!=null){
			//this.listener.onPositionChanged(this.mX, this.mY);
			this.listener.onPositionChanged(this.mX, this.mY, this);
		}
	}

	public void die() {
		this.alive = false;
		if(this.waterEffectsAnimatedSprite != null){
			this.waterEffectsAnimatedSprite.setVisible(false);
		}
		if(this.swimmingAbilityIndicatorSprite != null){
			this.swimmingAbilityIndicatorSprite.setVisible(false);
		}
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void setAlreadyKilled(boolean alreadyKilled) {
		this.alreadyKilled = alreadyKilled;
	}

	public boolean hasAlreadyBeenKilled() {
		return this.alreadyKilled;
	}

	public void setWaterEffectsAnimatedSprite(AnimatedSprite waterEffectsSprite) {
		this.waterEffectsAnimatedSprite = waterEffectsSprite;
	}
	
	public void setSwimmingAbilityIndicatorSprite(Sprite swimmingAbilityIndicatorSprite){
		this.swimmingAbilityIndicatorSprite = swimmingAbilityIndicatorSprite;
	}
	
	public void updatePlayerSprite() {
		if(this.waterEffectsAnimatedSprite != null){
			this.waterEffectsAnimatedSprite.setPosition(this.getX(), this.getY());
		}
		if(this.swimmingAbilityIndicatorSprite != null){
			this.swimmingAbilityIndicatorSprite.setPosition(this.getX(), this.getY());
		}
	}

	protected void animatePlayerAccordingToDirectionOfMovement(int destinationX,
			int destinationY) {
				boolean movingRight = this.isPlayerMovingTowardsTheRight(destinationX);
				boolean movingLeft = this.isPlayerMovingTowardsLeft(destinationX);
				boolean movingDown = this.isPlayerMovingDownwards(destinationY);
				boolean movingUp = this.isPlayerMovingUpwards(destinationY);
				
				if(movingRight){
					this.facingRight = true;
					this.facingLeft = false;
					this.facingDown = false;
					this.facingUp = false;
					this.animate(new long[]{200, 200, 200, 200}, 12, 15, true);
				}
				else if(movingLeft){
					this.facingLeft = true;
					this.facingRight = false;
					this.facingDown = false;
					this.facingUp = false;
					this.animate(new long[]{200, 200, 200, 200}, 16, 19, true);
				}
				else if(movingDown){
					this.facingDown = true;
					this.facingUp = false;
					this.facingLeft = false;
					this.facingRight = false;
					this.animate(new long[]{200, 200, 200}, 4, 6, true);
				}
				else if(movingUp){
					this.facingUp = true;
					this.facingDown = false;
					this.facingLeft = false;
					this.facingRight = false;
					this.animate(new long[]{200, 200, 200}, 8, 10, true);
				}
			}

	private boolean isPlayerMovingUpwards(int destinationY) {
		return this.getY() > destinationY;
	}

	private boolean isPlayerMovingTowardsLeft(int destinationX) {
		return this.getX() > destinationX;
	}

	private boolean isPlayerMovingDownwards(int destinationY) {
		return this.getY() < destinationY;
	}

	private boolean isPlayerMovingTowardsTheRight(int destinationX) {
		return this.getX() < destinationX;
	}

	protected void animateWaterEffectWhenMovingRight() {
		if(playerCurrentlyInWater()){
			if(waterEffectsAnimatedSprite != null && !animatingWaterEffectRight){
				waterEffectsAnimatedSprite.animate(new long[]{200, 200}, 2, 3, true);
				animatingWaterEffectRight = true;
				animatingWaterEffectLeft = false;
				animatingWaterEffectUp = false;
				animatingWaterEffectDown = false;
			}
		}
		else{
			stopWaterAnimation();
		}
	}

	protected void animateWaterEffectWhenMovingLeft() {
		if(playerCurrentlyInWater()){
			if(waterEffectsAnimatedSprite != null && !animatingWaterEffectLeft){
				waterEffectsAnimatedSprite.animate(new long[]{200, 200}, 2, 3, true);
				animatingWaterEffectRight = false;
				animatingWaterEffectLeft = true;
				animatingWaterEffectUp = false;
				animatingWaterEffectDown = false;
			}
		}
		else{
			stopWaterAnimation();
		}
	}

	protected void animateWaterEffectWhenMovingDown() {
		if(playerCurrentlyInWater()){
			if (waterEffectsAnimatedSprite != null && !animatingWaterEffectDown) {
				waterEffectsAnimatedSprite.animate(new long[] { 200, 200}, 2, 3, true);
				animatingWaterEffectRight = false;
				animatingWaterEffectLeft = false;
				animatingWaterEffectUp = false;
				animatingWaterEffectDown = true;
			}
		}
		else{
			stopWaterAnimation();
		}
	}

	protected void animateWaterEffectWhenMovingUp() {
		if(playerCurrentlyInWater()){
			if (waterEffectsAnimatedSprite != null && !animatingWaterEffectUp) {
				waterEffectsAnimatedSprite.animate(new long[] { 200, 200}, 2, 3, true);
				animatingWaterEffectRight = false;
				animatingWaterEffectLeft = false;
				animatingWaterEffectUp = true;
				animatingWaterEffectDown = false;
			}
		}
		else{
			stopWaterAnimation();
		}
	}
	
	protected void stopWaterAnimation() {
		if (waterEffectsAnimatedSprite != null) {
			waterEffectsAnimatedSprite.animate(new long[] { 200, 200}, 0, 1, false);
			animatingWaterEffectRight = false;
			animatingWaterEffectLeft = false;
			animatingWaterEffectUp = false;
			animatingWaterEffectDown = false;
		}
	}
	
	protected abstract boolean playerCurrentlyInWater();

	protected void updateAnimationAccordingToPathPosition(final float pathLength, int pWaypointIndex) {
		if(this.alive){
			if(pWaypointIndex == pathLength - 1){
				//TODO: Stop movement animation when end of waypoint reached.
				this.animateBasedOnDirection();
			}
			else{
				this.animateBasedOnDirection();
			}
		}
	}

	private void animatePlayerMovingRight() {
		if(!animatingRight){
			animate(new long[]{200, 200, 200, 200}, 12, 15, true);
			animatingRight = true;
			animatingLeft = false;
			animatingUp = false;
			animatingDown = false;
		}
	}

	private void animatePlayerMovingLeft() {
		if(!animatingLeft){
			animate(new long[]{200, 200, 200, 200}, 16, 19, true);
			animatingLeft = true;
			animatingRight = false;
			animatingUp = false;
			animatingDown = false;
		}
	}

	private void animatePlayerMovingDown() {
		if(!animatingDown){
			animate(new long[]{200, 200, 200}, 4, 6, true);
			animatingDown = true;
			animatingUp = false;
			animatingLeft = false;
			animatingRight = false;
		}
	}

	private void animatePlayerMovingUp() {
		if(!animatingUp){
			animate(new long[]{200, 200, 200}, 8, 10, true);
			animatingUp = true;
			animatingDown = false;
			animatingLeft = false;
			animatingRight = false;
		}
	}

	/**
	 * Retrieves the path points between the player's current position and the destination.
	 * The path points are all of the tile positions between the current position and the destination.
	 * For example, if the player is moving from 0,80 to 0,320, then the path points would include
	 * points: 0,80, 0,160, 0,240, and 0,320.
	 * @param xPointsList The list which will contain the x components of the points.
	 * @param yPointsList The list which will contain the y components of the points.
	 * @param includeSourcePosition True if the source position should be included in the resulting path between source and destination. 
	 */
	protected void retrievePathPointsBetweenSourceAndDestination(
			List<Float> xPointsList, List<Float> yPointsList, float sourceX, float sourceY, float targetX, float targetY, boolean includeSourcePosition) throws PlayerMovementException {
		if(sourceX < targetX){
			float currentX = sourceX;
			while(currentX <= targetX){
				if(currentX == sourceX){
					if(includeSourcePosition){
						xPointsList.add(currentX);
						yPointsList.add(sourceY);
					}
				}
				else{
					xPointsList.add(currentX);
					yPointsList.add(sourceY);
				}
				currentX += CommonDataManager.TILE_SIZE;
			}
		}
		else if(sourceX > targetX){
			float currentX = sourceX;
			while(currentX >= targetX){
				if(currentX == sourceX){
					if(includeSourcePosition){
						xPointsList.add(currentX);
						yPointsList.add(sourceY);
					}
				}
				else{
					xPointsList.add(currentX);
					yPointsList.add(sourceY);
				}
				currentX -= CommonDataManager.TILE_SIZE;
			}
		}
		else if(sourceY < targetY){
			float currentY = sourceY;
			while(currentY <= targetY){
				if(currentY == sourceY){
					if(includeSourcePosition){
						yPointsList.add(currentY);
						xPointsList.add(sourceX);
					}
				}
				else{
					yPointsList.add(currentY);
					xPointsList.add(sourceX);
				}
				currentY += CommonDataManager.TILE_SIZE;
			}
		}
		else if(sourceY > targetY){
			float currentY = sourceY;
			while(currentY >= targetY){
				if(currentY == sourceY){
					if(includeSourcePosition){
						yPointsList.add(currentY);
						xPointsList.add(sourceX);
					}
				}
				else{
					yPointsList.add(currentY);
					xPointsList.add(sourceX);
				}
				currentY -= CommonDataManager.TILE_SIZE;
			}
		}
		else{
			throw new PlayerMovementException("Destination cannot be the same as the source.");
		}
	}

	protected void copyValuesIntoFloatArrays(List<Float> xPointsList, List<Float> yPointsList, float[] xPoints, float[] yPoints) {
		for (int i = 0; i < xPointsList.size(); i++) {
			xPoints[i] = xPointsList.get(i);
		}
		for (int i = 0; i<yPointsList.size(); i++) {
			yPoints[i] = yPointsList.get(i);
		}
	}

	protected void animateBasedOnDirection() {
		if (facingUp) {
			animateWaterEffectWhenMovingUp();
			if(this.isMoving()){
				animatePlayerMovingUp();
			}
			else{
				stopAnimationBasedOnDirection();
			}
		}
		else if (facingDown) {
			animateWaterEffectWhenMovingDown();
			if(this.isMoving()){
				animatePlayerMovingDown();
			}
			else{
				stopAnimationBasedOnDirection();
			}
		}
		else if(facingLeft){
			animateWaterEffectWhenMovingLeft();
			if(this.isMoving()){
				animatePlayerMovingLeft();
			}
			else{
				stopAnimationBasedOnDirection();
			}
		}
		else if(facingRight){
			animateWaterEffectWhenMovingRight();
			if(this.isMoving()){
				animatePlayerMovingRight();
			}
			else{
				stopAnimationBasedOnDirection();
			}
		}
	}

	private void stopAnimationBasedOnDirection() {
		if(this.facingUp){
			this.stopAnimation(8);
		}
		if(this.facingDown){
			this.stopAnimation(4);
		}
		if(this.facingLeft){
			this.stopAnimation(16);
		}
		if(this.facingRight){
			this.stopAnimation(12);
		}
		
		animatingUp = false;
		animatingDown = false;
		animatingLeft = false;
		animatingRight = false;
	}

	public PlayerAbility getPlayerAbility() {
		return this.playerAbility;
	}
}
