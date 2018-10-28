package chris.braunschweiler.touchngo.entities;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.PathModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.StrokeFont;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.Path;

import chris.braunschweiler.touchngo.common.CommonDataManager;
import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;
import chris.braunschweiler.touchngo.exceptions.PlayerMovementException;

/**
 * Implementation of the playable character interface.
 * @author chrisbraunschweiler
 *
 */
public class PlayableCharacterSprite extends PlayerSprite implements IPlayableCharacter{
	private IPlayableCharacterListener listener;
	private MoneyBag moneyBag;
	private boolean isLocked;
	private int numberOfCoinsNeededToUnlockPlayableCharacter;
	private int currentTargetXPosition;
	private int currentTargetYPosition;
	private PlayerMapModel playerMapModel;
	private AnimatedSprite lockedPlayerSprite;
	private StrokeFont smallTextFont;
	private Text nrOfCoinsRequiredToUnlockText;
	private AnimatedSprite selectedPlayerSprite;
	private boolean changeDestinationOnSameAxisMidMove;	//True if the player should change his destination mid move and if the destination is on the same axis
	private boolean changeDestinationOffAxisMidMove;	//True if player should change destination off of his current axis while he's still on his way to his current target destination
	private float timeNeededToMoveToProspectiveTarget;	//Time required to move to the prospective target position
	private int prospectiveSourceXForMovementOffAxis;	//The source x position from which the player will begin his mid-movement move off of his current axis of movement
	private int prospectiveSourceYForMovementOffAxis;	//Ditto except the y position
	private int prospectiveTargetX;	//The prospective x position that the sprite will move to once it has reached its current destination
	private int prospectiveTargetY;	//The prospective y position that the sprite will move to once it has reached its current destination
	private boolean playingDyingAnimation;
	
	public PlayableCharacterSprite(float pX, 
			float pY,
			boolean isLocked,
			int numberOfCoinsNeededToUnlock,
			TiledTextureRegion textureRegion,
			IPlayableCharacterListener listener,
			PlayerAbility playerAbility, PlayerMapModel playerMapModel, AnimatedSprite lockedPlayerSprite, StrokeFont smallTextFont, AnimatedSprite selectedPlayerSprite){
		super(pX, pY, textureRegion, listener, playerAbility);
		this.isLocked = isLocked;
		this.numberOfCoinsNeededToUnlockPlayableCharacter = numberOfCoinsNeededToUnlock;
		this.listener = listener;
		this.moneyBag = null;
		this.playerMapModel = playerMapModel;
		this.lockedPlayerSprite = lockedPlayerSprite;
		this.smallTextFont = smallTextFont;
		this.selectedPlayerSprite = selectedPlayerSprite;
		this.playingDyingAnimation = false;
		
		this.generateCoinsRequiredToUnlockPlayerText();
	}
	
	private void generateCoinsRequiredToUnlockPlayerText() {
		this.nrOfCoinsRequiredToUnlockText = new Text(this.getX(), this.getY(), this.smallTextFont, "x" + this.numberOfCoinsNeededToUnlockPlayableCharacter, HorizontalAlign.CENTER);
		float textPosX = this.getX() + this.getWidth() / 2 - this.nrOfCoinsRequiredToUnlockText.getWidth() / 2;
		float textPosY = this.getY() + this.getHeight() /2 - this.nrOfCoinsRequiredToUnlockText.getHeight() / 2;
		this.nrOfCoinsRequiredToUnlockText.setPosition(textPosX, textPosY);
		
		if(!this.isLocked){
			this.nrOfCoinsRequiredToUnlockText.setVisible(false);
		}
	}

	public void collect(ICollectable collectable) {
		// React to collision depending on what collectable was collected.
	}

	public void collectMoneyBag(MoneyBag moneyBag) {
		if(this.listener != null){
			this.listener.onMoneyBagCollected(moneyBag, this);
		}
	}
	
	public void dropMoneyBag(){
		int playerDyingTileCol = (int) this.getX() / CommonDataManager.TILE_SIZE;
		int playerDyingTileRow = (int) this.getY() / CommonDataManager.TILE_SIZE;
		int playerDyingX = playerDyingTileCol * CommonDataManager.TILE_SIZE;
		int playerDyingY = playerDyingTileRow * CommonDataManager.TILE_SIZE;
		this.getPlayerMoneyBag().setPosition(playerDyingX, playerDyingY);
		this.getPlayerMoneyBag().animate(new long[]{200, 200}, 0, 1, true);
		this.getPlayerMoneyBag().setPickedUp(false);
		this.setPlayerMoneyBag(null);
	}

	public MoneyBag getPlayerMoneyBag() {
		return this.moneyBag;
	}

	public void setPlayerMoneyBag(MoneyBag moneyBag) {
		this.moneyBag = moneyBag;
	}
	
	public void die() {
		if(!this.isLocked){
			super.die();
			
			//Display dying animation of player
			if(!this.playingDyingAnimation){
				this.animate(new long[]{100, 100, 100, 100}, 0, 3, false);
				this.playingDyingAnimation = true;
			}
			this.selectedPlayerSprite.animate(new long[]{200, 200}, 2, 3, true);
			this.stopWaterAnimation();
			if(this.listener != null){
				this.listener.onDying(this);
			}
		}
	}

	public boolean isLocked() {
		return this.isLocked;
	}

	public void tryToUnlock(int currentNumberOfCoinsCollected) {
		if (this.isLocked && currentNumberOfCoinsCollected >= this.numberOfCoinsNeededToUnlockPlayableCharacter) {
			this.isLocked = false;
			
			//make cage animation invisible
			this.lockedPlayerSprite.animate(new long[]{200, 200}, 2, 3, true);
			this.nrOfCoinsRequiredToUnlockText.setVisible(false);
		}
		else if(this.isLocked){
			this.lockedPlayerSprite.animate(new long[]{200, 200}, 0, 1, true);
			animateBasedOnDirection();
		}
		else if(!this.isLocked){
			this.lockedPlayerSprite.animate(new long[]{200, 200}, 2, 3, true);
		}
	}

	public int getPlayerTargetPositionX() {
		return this.currentTargetXPosition;
	}

	public int getPlayerTargetPositionY() {
		return this.currentTargetYPosition;
	}

	public void setPlayerTargetPositionX(int positionX) {
		this.currentTargetXPosition = positionX;
	}

	public void setPlayerTargetPositionY(int positionY) {
		this.currentTargetYPosition = positionY;
	}

	public void highlightPlayer() {
		this.selectedPlayerSprite.animate(new long[]{500, 500}, 0, 1, true);
		this.animateBasedOnDirection();
	}

	public void unHighlightPlayer() {
		this.selectedPlayerSprite.animate(new long[]{200, 200}, 2, 3, true);
		this.animateBasedOnDirection();
	}

	@Deprecated
	public void showLockedAnimation() {
		/*this.lockedPlayerSprite.animate(new long[]{200, 200}, 0, 1, true);
		if(facingUp){
			animate(new long[]{200, 200}, 2, 3, true);
		}
		else if(facingDown){
			animate(new long[]{200, 200}, 16, 17, true);
		}
		else if(facingLeft){
			animate(new long[]{200, 200}, 6, 7, true);
		}
		else if(facingRight){
			animate(new long[]{200, 200}, 10, 11, true);
		}*/
	}
	
	public void updatePlayerSprite() {
		super.updatePlayerSprite();
		
		if(this.lockedPlayerSprite != null){
			this.lockedPlayerSprite.setPosition(this.getX(), this.getY());
		}
		if(this.selectedPlayerSprite != null){
			this.selectedPlayerSprite.setPosition(this.getX(), this.getY());
		}
		
		if(!this.isMoving()){
			// If player reached end of current path before it could process the fact that it had to
			// change its destination mid-move, change the path now (even though the player is no longer
			// moving).
			this.changePlayerDirectionMidMoveAlongSameAxisIfNecessary();
			this.changePlayerDirectionOffAxisMidMoveIfNecessary();
		}
	}

	// Moves the player to a new destination along the same axis on which the player is currently
	// moving while the player is still moving towards his current destination if necessary.
	private void changePlayerDirectionMidMoveAlongSameAxisIfNecessary() {
		if(this.isAlive() && this.changeDestinationOnSameAxisMidMove && !this.changeDestinationOffAxisMidMove){
			// Player needs to change target destination immediately
			this.setPlayerTargetPositionX(prospectiveTargetX);
			this.setPlayerTargetPositionY(prospectiveTargetY);
			this.clearShapeModifiers();
			this.movePlayer(prospectiveTargetX, prospectiveTargetY, timeNeededToMoveToProspectiveTarget);
			this.changeDestinationOnSameAxisMidMove = false;
		}
	}
	
	// Moves the player to a new destination off of the player's current axis of movement while
	// the player is still moving towards his current destination if necessary.
	private void changePlayerDirectionOffAxisMidMoveIfNecessary(){
		if(this.isAlive() && this.changeDestinationOffAxisMidMove && !this.changeDestinationOnSameAxisMidMove){
			if(this.getX() == this.prospectiveSourceXForMovementOffAxis &&
					this.getY() == this.prospectiveSourceYForMovementOffAxis){
				this.setPlayerTargetPositionX(this.prospectiveTargetX);
				this.setPlayerTargetPositionY(this.prospectiveTargetY);
				this.clearShapeModifiers();
				this.movePlayer(prospectiveTargetX, prospectiveTargetY, timeNeededToMoveToProspectiveTarget);
				this.changeDestinationOffAxisMidMove = false;
			}
		}
	}

	public void movePlayer(int destinationX, int destinationY,
			float timeNeededToMove) {
		try{
			this.setPlayerTargetPositionX(destinationX);
			this.setPlayerTargetPositionY(destinationY);
			
			animatePlayerAccordingToDirectionOfMovement(destinationX, destinationY);
			
			List<Float> xPointsList = new ArrayList<Float>();
			List<Float> yPointsList = new ArrayList<Float>();
			this.retrievePathPointsBetweenSourceAndDestination(xPointsList, yPointsList, this.getX(), this.getY(), this.getPlayerTargetPositionX(), this.getPlayerTargetPositionY(), true);
			float[] xPoints = new float[xPointsList.size()];
			float[] yPoints = new float[yPointsList.size()];
			
			if(xPoints.length > 0 && yPoints.length > 0){
				for(int i = 0; i < xPoints.length; i++){
					Debug.i(">>>>xPoint in path: " + xPoints[i]);
				}
				
				copyValuesIntoFloatArrays(xPointsList, yPointsList, xPoints, yPoints);
				
				float timeNeededToMove2 = this.calculateTimeNeededToMove(destinationX, destinationY);
				Path newPath = new Path(xPoints, yPoints);
				this.setPlayerPath(newPath, timeNeededToMove2);
				this.setIsMoving(true);
			}
		}
		catch(PlayerMovementException e){
			Debug.e(e);
			this.setIsMoving(false);
		}
	}
	
	private float calculateTimeNeededToMove(int destinationX, int destinationY) {
		int sourceCol = (int) this.getX() / CommonDataManager.TILE_SIZE;
		int sourceRow = (int) this.getY() / CommonDataManager.TILE_SIZE;
		int destinationCol = destinationX / CommonDataManager.TILE_SIZE;
		int destinationRow = destinationY / CommonDataManager.TILE_SIZE;
		float diffX = Math.abs(destinationCol - sourceCol);
		float diffY = Math.abs(destinationRow - sourceRow);
		return diffX > 0 ? diffX/2 : diffY/2;
	}
	
	private void setPlayerPath(Path newPath, float timeNeededToMove) {
		final float pathLength = newPath.getSize();
		this.addShapeModifier(new PathModifier(timeNeededToMove, newPath, null, new IPathModifierListener() {

			public void onWaypointPassed(PathModifier pPathModifier, IShape pShape, int pWaypointIndex) {
				if(pWaypointIndex == pathLength - 1){
					setIsMoving(false);
				}
				else{
					changePlayerDirectionMidMoveAlongSameAxisIfNecessary();
					changePlayerDirectionOffAxisMidMoveIfNecessary();
				}
				
				updateAnimationAccordingToPathPosition(pathLength, pWaypointIndex);
			}
		}));
	}
	
	/**
	 * Returns true if the player is currently in water, false otherwise.
	 * @return True if the player is in water, false otherwise.
	 */
	protected boolean playerCurrentlyInWater() {
		int currentTileProperty = this.playerMapModel.getTileProperty((int)this.getX(), (int)this.getY());
		return currentTileProperty == PlayerMapModel.WATER;
	}

	public void movePlayerOffAxisMidMove(int sourceX, int sourceY, int destinationX, int destinationY,
			float timeNeededToMove) {
		this.prospectiveSourceXForMovementOffAxis = sourceX;
		this.prospectiveSourceYForMovementOffAxis = sourceY;
		this.prospectiveTargetX = destinationX;
		this.prospectiveTargetY = destinationY;
		this.timeNeededToMoveToProspectiveTarget = timeNeededToMove;
		this.changeDestinationOnSameAxisMidMove = false;
		this.changeDestinationOffAxisMidMove = true;
	}

	public void movePlayerAlongSameAxisMidMove(int destinationX,
			int destinationY, float timeNeededToMove) {
		this.prospectiveTargetX = destinationX;
		this.prospectiveTargetY = destinationY;
		this.timeNeededToMoveToProspectiveTarget = timeNeededToMove;
		this.changeDestinationOffAxisMidMove = false;
		this.changeDestinationOnSameAxisMidMove = true;
	}

	public Text getNrOfCoinsRequiredToUnlockPlayerText() {
		return this.nrOfCoinsRequiredToUnlockText;
	}
}
