package chris.braunschweiler.touchngo.entities;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.LoopShapeModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.Path;

import chris.braunschweiler.touchngo.ai.AIPath;
import chris.braunschweiler.touchngo.ai.AStarPathFinder;
import chris.braunschweiler.touchngo.ai.GameMap;
import chris.braunschweiler.touchngo.ai.Mover;
import chris.braunschweiler.touchngo.ai.PathFinder;
import chris.braunschweiler.touchngo.ai.TileBasedMap;
import chris.braunschweiler.touchngo.common.CommonDataManager;
import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;
import chris.braunschweiler.touchngo.exceptions.PlayerMovementException;
import chris.braunschweiler.touchngo.exceptions.TargetUnreachableException;
import chris.braunschweiler.touchngo.utils.MathUtilities;
import chris.braunschweiler.touchngo.utils.PathPoint;
import chris.braunschweiler.touchngo.utils.TileUtilities;

/**
 * Implementation of the INonPlayableCharacter interface.
 * @author chrisbraunschweiler
 *
 */
public class NonPlayableCharacterSprite extends PlayerSprite implements INonPlayableCharacter, Mover{
	
	private enum NPC_State{
		PATROL_STATE,
		PURSUIT_STATE,
		RETREAT_STATE
	}
	
	private List<PathPoint> patrolPathPoints;
	private TileBasedMap npcReadableLevelMap;
	private PathFinder finder;
	private NPC_State currentState;
	private boolean doneRetreating;
	private boolean reachedCurrentTarget;
	private boolean ableToChangeTargetMidPath;	//If this is true, the NPC can change his target
												//even if he hasn't reached his current target
	private int currentTargetXPosition;	//The x component of the current target position
	private int currentTargetYPosition; //The y component of the current target position
	
	private int chaseTargetDistance;
	private int changeTargetMidPathDistance;
	
	private List<IPlayableCharacter> playableCharacters;
	private AnimatedSprite stateIndicatorSprite;
	
	/**
	 * The default distance within which the NPC will chase his current target. The distance is in tiles.
	 */
	private static final int DEFAULT_CHASE_TARGET_DISTANCE = 4;
	
	/**
	 * The default distance between the NPC and the currently targeted player within which the NPC can change his
	 * destination mid-path (while he's still on his way to his current destination). This will prevent
	 * NPCs which are still really far away from the player to always change their destination when the
	 * user changes his destination. The distance is in tiles.
	 */
	private static final int DEFAULT_CHANGE_TARGET_MID_PATH_DISTANCE = 2;
	
	
	public NonPlayableCharacterSprite(float pX, 
			float pY, 
			TiledTextureRegion textureRegion,
			INonPlayableCharacterListener listener,
			List<PathPoint> patrolPathPoints,
			TileBasedMap npcReadableLevelMap, int chaseTargetDistance, int changeTargetMidPathDistance, AnimatedSprite stateIndicatorSprite, PlayerAbility playerAbility){
		super(pX, pY, textureRegion, listener, playerAbility);
		this.patrolPathPoints = patrolPathPoints;
		this.npcReadableLevelMap = npcReadableLevelMap;
		this.finder = new AStarPathFinder(this.npcReadableLevelMap, 500, false);
		this.currentState = NPC_State.PATROL_STATE;
		this.doneRetreating = false;
		this.reachedCurrentTarget = false;
		this.ableToChangeTargetMidPath = false;
		this.currentTargetXPosition = 0;
		this.currentTargetYPosition = 0;
		this.playableCharacters = new ArrayList<IPlayableCharacter>();
		if(chaseTargetDistance == -1){
			this.chaseTargetDistance = this.DEFAULT_CHASE_TARGET_DISTANCE;
		}
		else{
			this.chaseTargetDistance = chaseTargetDistance;
		}
		if(changeTargetMidPathDistance == -1){
			this.changeTargetMidPathDistance = this.DEFAULT_CHANGE_TARGET_MID_PATH_DISTANCE; 
		}
		else{
			this.changeTargetMidPathDistance = changeTargetMidPathDistance;
		}
		this.stateIndicatorSprite = stateIndicatorSprite;
		this.animateBasedOnDirection();
	}
	
	public void patrol() {
		this.currentState = NPC_State.PATROL_STATE;
		calculateAndSetNpcPatrolPath();
	}

	public void changeNpcTargetPosition(int selectedPlayerTargetX, int selectedPlayerTargetY) {
	}

	private void retreat() {
		//Debug.i("***** NPC is TRYING TO change to RETREAT state");
		boolean npcFoundAPath = this.moveTowardsXAndYPosition((int)this.originalX, (int)this.originalY);
		if(npcFoundAPath){
			this.currentState = NPC_State.RETREAT_STATE;
			this.doneRetreating = false;
			//Debug.i("***** NPC SUCCESSFULLY changed to RETREAT state");
		}
		else{
			//Debug.i("***** NPC FAILED TO change to RETREAT state");
		}
	}

	public void updatePlayerSprite() {
		super.updatePlayerSprite();
		updateStateIndicatorSpritePosition();
		try{
			IPlayableCharacter targetPlayableCharacter = this.getPlayableCharacterToAttack();
			if (targetPlayableCharacter != null) {
				if (this.closeEnoughToChaseTarget(targetPlayableCharacter)) {
					if (this.currentState == NPC_State.PURSUIT_STATE) {
						if (this.npcTargetPositionDifferentFromTargetPlayerTargetPosition(targetPlayableCharacter)) {
							if(this.reachedCurrentTarget){
								this.chaseTarget(targetPlayableCharacter);
							}
							else{
								if (this.closeEnoughToChangeTargetPositionMidPath(targetPlayableCharacter)) {
									this.chaseTarget(targetPlayableCharacter);
								}
							}
						}
					}
					else {
						this.chaseTarget(targetPlayableCharacter);
						this.stateIndicatorSprite.animate(new long[] { 1000, 1000}, 2, 3, false);
					}
				} 
				else if (!this.closeEnoughToChaseTarget(targetPlayableCharacter)) {
					this.tryToRetreat();
				}
				if (this.currentState == NPC_State.RETREAT_STATE) {
					if (this.doneRetreating) {
						this.doneRetreating = false;
						this.patrol();
					}
				}
			}
		}
		catch(TargetUnreachableException e){
			Debug.i(e.getStackTrace().toString());
			this.tryToRetreat();
		}
	}

	private void tryToRetreat() {
		if (this.currentState != NPC_State.RETREAT_STATE && this.currentState != NPC_State.PATROL_STATE) {
			if (!npcAtSpawningPosition()) {
				this.retreat();
				this.stateIndicatorSprite.animate(new long[] { 1000, 1000}, 4, 5, false);
			}
		}
	}

	private void updateStateIndicatorSpritePosition() {
		if(this.stateIndicatorSprite != null){
			this.stateIndicatorSprite.setPosition(this.getX(), this.getY());
		}
	}

	private boolean npcAtSpawningPosition() {
		int currentTileCol = TileUtilities.GetTilePosition((int) this.getX());
		int currentTileRow = TileUtilities.GetTilePosition((int) this.getY());
		int originalCol = TileUtilities.GetTilePosition((int) this.originalX);
		int originalRow = TileUtilities.GetTilePosition((int) this.originalY);
		
		return currentTileCol == originalCol && currentTileRow == originalRow;
	}
	
	// Obtains the playable character which is closest to the NPC and returns it. This way, the NPC will
	// always attack the playable character which is closest.
	private IPlayableCharacter getPlayableCharacterToAttack() {
		int playerIndex = 0;
		int shortestDistanceToAnyPlayer = 0;
		IPlayableCharacter closestPlayer = null;
		for(IPlayableCharacter character : this.playableCharacters){
			if(character.isAlive() && !character.isLocked()){
				int characterX = (int) character.getX();
				int characterY = (int) character.getY();
				
				int distanceToPlayer = MathUtilities.calculateDistanceBetween(
						TileUtilities.GetTilePosition((int)this.getX()), 
						TileUtilities.GetTilePosition((int)this.getY()), 
						TileUtilities.GetTilePosition(characterX), 
						TileUtilities.GetTilePosition(characterY));
				
				if(playerIndex == 0){
					shortestDistanceToAnyPlayer = distanceToPlayer;
					closestPlayer = character;
				}
				else{
					if(distanceToPlayer < shortestDistanceToAnyPlayer){
						shortestDistanceToAnyPlayer = distanceToPlayer;
						closestPlayer = character;
					}
				}
				
				playerIndex++;
			}
		}
		
		return closestPlayer;
	}

	private boolean npcTargetPositionDifferentFromTargetPlayerTargetPosition(IPlayableCharacter targetPlayableCharacter) {
		int currentTileCol = TileUtilities.GetTilePosition(this.currentTargetXPosition);
		int currentTileRow = TileUtilities.GetTilePosition(this.currentTargetYPosition);
		int targetCharacterTargetTileCol = TileUtilities.GetTilePosition(targetPlayableCharacter.getPlayerTargetPositionX());
		int targetCharacterTargetTileRow = TileUtilities.GetTilePosition(targetPlayableCharacter.getPlayerTargetPositionY());
		
		return (currentTileCol != targetCharacterTargetTileCol ||
				currentTileRow != targetCharacterTargetTileRow);
	}

	private boolean closeEnoughToChaseTarget(IPlayableCharacter targetPlayableCharacter){
		if(targetPlayableCharacter != null){
			int distanceToTargetPlayer = MathUtilities.calculateDistanceBetween(
					TileUtilities.GetTilePosition((int)this.getX()), 
					TileUtilities.GetTilePosition((int)this.getY()), 
					TileUtilities.GetTilePosition((int)targetPlayableCharacter.getX()), 
					TileUtilities.GetTilePosition((int)targetPlayableCharacter.getY()));
			if (distanceToTargetPlayer <= this.chaseTargetDistance) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean closeEnoughToChangeTargetPositionMidPath(IPlayableCharacter targetPlayableCharacter){
		if(targetPlayableCharacter != null){
			int distanceToTargetPlayer = MathUtilities.calculateDistanceBetween(
					TileUtilities.GetTilePosition((int)this.getX()), 
					TileUtilities.GetTilePosition((int)this.getY()), 
					TileUtilities.GetTilePosition((int)targetPlayableCharacter.getX()), 
					TileUtilities.GetTilePosition((int)targetPlayableCharacter.getY()));
			if (distanceToTargetPlayer <= this.changeTargetMidPathDistance) {
				return true;
			}
		}
		
		return false;
	}

	private void calculateAndSetNpcPatrolPath() {
		float[] xPoints = new float[this.patrolPathPoints.size()];
		float[] yPoints = new float[this.patrolPathPoints.size()];
		
		for(int i = 0; i<this.patrolPathPoints.size(); i++){
			xPoints[i] = this.patrolPathPoints.get(i).getXCoord();
			yPoints[i] = this.patrolPathPoints.get(i).getYCoord();
		}
		
		float timeNeededToMove = this.calculateTimeNeededToMoveAlongPath(xPoints, yPoints, 3);
		
		if(xPoints.length > 0 && yPoints.length > 0){
			try{
			if(xPoints.length > 1 && yPoints.length > 1){
				float nextPathPointX = xPoints[1];
				float nextPathPointY = yPoints[1];
				animatePlayerAccordingToDirectionOfMovement((int) nextPathPointX, (int) nextPathPointY);
			}
			
			List<Float> xPointsList = new ArrayList<Float>();
			List<Float> yPointsList = new ArrayList<Float>();
			for(int i = 0; i<xPoints.length - 1; i++){
				boolean includeSourcePosition = i == 0;
				this.retrievePathPointsBetweenSourceAndDestination(
						xPointsList, 
						yPointsList, 
						xPoints[i],
						yPoints[i],
						xPoints[i + 1],
						yPoints[i + 1], 
						includeSourcePosition);
			}
			
			float[] xPointsForPath = new float[xPointsList.size()];
			float[] yPointsForPath = new float[yPointsList.size()];
			
			copyValuesIntoFloatArrays(xPointsList, yPointsList, xPointsForPath, yPointsForPath);
			
			final Path patrolPath = new Path(xPointsForPath, yPointsForPath);
			final int pathLength = patrolPath.getSize();
			
			this.addShapeModifier(new LoopShapeModifier(new PathModifier(timeNeededToMove, patrolPath, null, new IPathModifierListener() {
	
				public void onWaypointPassed(PathModifier pPathModifier,
						IShape pShape, int pWaypointIndex) {
					determineDirectionOfMovement(patrolPath, pWaypointIndex);
					updateAnimationAccordingToPathPosition(pathLength, pWaypointIndex);
				}
			})));
			
			this.setIsMoving(true);
			}
			catch(PlayerMovementException e){
				Debug.e(e);
			}
		}
	}
	
	/**
	 * Calculates the time needed to move along the given path.
	 * @param xPoints The x coordinates that make up the path.
	 * @param yPoints The y coordinates that make up the path.
	 * @param multiplier The factor used to multiply the number of x and y points into a valid time unit.
	 * @return
	 */
	private float calculateTimeNeededToMoveAlongPath(float[] xPoints, float[] yPoints, int multiplier){
		if(xPoints.length == yPoints.length){
			return xPoints.length * multiplier;
		}
		
		return 0;
	}

	private void chaseTarget(IPlayableCharacter targetPlayableCharacter) throws TargetUnreachableException{
		//Debug.i("***** NPC is TRYING TO change to PURSUIT state");
		if(targetPlayableCharacter != null){
			boolean npcFoundPath = moveTowardsXAndYPosition(
					targetPlayableCharacter.getPlayerTargetPositionX(),
					targetPlayableCharacter.getPlayerTargetPositionY());
			if(this.targetUnreachable(targetPlayableCharacter)){
				throw new TargetUnreachableException("Target unreachable.");
			}
			else if(npcFoundPath){
				this.currentState = NPC_State.PURSUIT_STATE;
				//Debug.i("***** NPC SUCCESSFULLY changed to PURSUIT state");
			}
			else{
				//Debug.i("***** NPC FAILED to change to PURSUIT state");
			}
		}
	}

	private boolean targetUnreachable(IPlayableCharacter targetPlayableCharacter) {
		int targetTileProperty = this.npcReadableLevelMap.getTileProperty((int)targetPlayableCharacter.getX(), (int)targetPlayableCharacter.getY());
		
		if(this.getPlayerAbility() == PlayerAbility.NONE){
			if(targetTileProperty == GameMap.WATER){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Moves the NPC towards the given target x and y position.
	 * @param targetX The target position towards which the NPC will move.
	 * @param targetY The target position towards which the NPC will move.
	 * @return True if the NPC was able to find a path to the target, false otherwise.
	 */
	private boolean moveTowardsXAndYPosition(int targetX, int targetY) {
		if((this.getX() != targetX || this.getY() != targetY) && this.npcReadableLevelMap != null){
			AIPath foundPath = calculateBestPathToTarget(targetX, targetY);
			if(foundPath != null){				
				this.reachedCurrentTarget = false;
				this.clearShapeModifiers();
				
				List<Float> xPointsList = new ArrayList<Float>();
				List<Float> yPointsList = new ArrayList<Float>();
				
				organizeNpcPathData(foundPath, xPointsList, yPointsList);
				
				this.ensureSmoothPathTransition(xPointsList, yPointsList);
				
				float[] xPoints = new float[xPointsList.size()];
				float[] yPoints = new float[yPointsList.size()];
				
				generateAndEngineReadablePathData(xPointsList, yPointsList, xPoints, yPoints);
				
				if(xPoints.length > 1 && yPoints.length > 1){
					float nextPathPointX = xPoints[1];
					float nextPathPointY = yPoints[1];
					animatePlayerAccordingToDirectionOfMovement((int) nextPathPointX, (int) nextPathPointY);
				}
				
				final Path newPath = new Path(xPoints, yPoints);
				
				final float pathLength = newPath.getSize();
				float timeNeededToMove = this.calculateTimeNeededToMoveAlongPath(xPoints, yPoints, 1);
				this.addShapeModifier(new PathModifier(timeNeededToMove, newPath, null, new IPathModifierListener() {
		
					public void onWaypointPassed(
							PathModifier pPathModifier,
							IShape pShape, 
							int pWaypointIndex) {
						if (currentState == NPC_State.RETREAT_STATE) {
							if (pWaypointIndex == pathLength - 1) {
								doneRetreating = true;
							}
						}
						if (currentState == NPC_State.PURSUIT_STATE) {
							if (pWaypointIndex == pathLength - 1) {
								reachedCurrentTarget = true;
							}
						}
						if(pWaypointIndex == pathLength - 1){
							setIsMoving(false);
						}
						
						determineDirectionOfMovement(newPath, pWaypointIndex);
						updateAnimationAccordingToPathPosition(pathLength, pWaypointIndex);
					}
				}));
				
				this.currentTargetXPosition = targetX;
				this.currentTargetYPosition = targetY;
				this.setIsMoving(true);
				return true;
			}
		}
		return false;
	}
	
	private void determineDirectionOfMovement(Path newPath,
			int pWaypointIndex) {
		if(pWaypointIndex < newPath.getSize() - 1){
			float[] pathXPoints = newPath.getCoordinatesX();
			float[] pathYPoints = newPath.getCoordinatesY();
			float nextX = pathXPoints[pWaypointIndex + 1];
			float nextY = pathYPoints[pWaypointIndex + 1];
			float currentX = pathXPoints[pWaypointIndex];
			float currentY = pathYPoints[pWaypointIndex];
			
			if(currentX < nextX){
				facingRight = true;
				facingLeft = false;
				facingUp = false;
				facingDown = false;
			}
			else if(currentX > nextX){
				facingLeft = true;
				facingRight = false;
				facingUp = false;
				facingDown = false;
			}
			else if(currentY < nextY){
				facingDown = true;
				facingUp = false;
				facingLeft = false;
				facingRight = false;
			}
			else if(currentY > nextY){
				facingUp = true;
				facingDown = false;
				facingLeft = false;
				facingRight = false;
			}
		}
	}

	private void generateAndEngineReadablePathData(List<Float> xPointsList,
			List<Float> yPointsList, float[] xPoints, float[] yPoints) {
		for (int i = 0; i < xPointsList.size(); i++) {
			xPoints[i] = xPointsList.get(i);
		}
		for (int i = 0; i<yPointsList.size(); i++) {
			yPoints[i] = yPointsList.get(i);
		}
	}

	/**
	 * Ensures that the NPC will smoothly transition to his new path. This is achieved by checking whether the
	 * NPC is currently in the middle of a tile or if it is between two tiles. If it is between two tiles,
	 * the NPC will either ignore the first point in his calculated path (so that he doesn't backtrack in order to
	 * start his new path) or not. Regardless of whether the first point in the path is ignored, the NPC will always
	 * add his current position to the beginning of the path to ensure a smooth transition.
	 * @param yPointsList 
	 * @param xPointsList 
	 */
	private void ensureSmoothPathTransition(List<Float> xPointsList, List<Float> yPointsList) {
		if(xPointsList.size() > 0){
			if(this.getX() != xPointsList.get(0)){
				// NPC is between two tiles
				if(xPointsList.size() > 1){
					// NPC's path to destination is longer than just one hop
					if((this.getX() > xPointsList.get(0) && this.getX() < xPointsList.get(1)) ||
							(this.getX() < xPointsList.get(0) && this.getX() > xPointsList.get(1))){
						// NPC is right between the first two points of his new path to destination.
						// Remove first entry
						xPointsList.remove(0);
					}
				}
				xPointsList.add(0, this.getX());
			}
		}
		
		if(yPointsList.size() > 0){
			if(this.getY() != yPointsList.get(0)){
				// NPC is between two tiles
				if(yPointsList.size() > 1){
					// NPC's path to destination is longer than just one hop
					if((this.getY() > yPointsList.get(0) && this.getY() < yPointsList.get(1)) ||
							(this.getY() < yPointsList.get(0) && this.getY() > yPointsList.get(1))){
						// NPC is right between the first two points of his new path to destination.
						// Remove first entry
						yPointsList.remove(0);
					}
				}
				yPointsList.add(0, this.getY());
			}
		}
		
		if(xPointsList.size() > yPointsList.size()){
			// Added NPC position's x component to x list. So add npc position's y component to y list.
			yPointsList.add(0, this.getY());
		}
		else if(yPointsList.size() > xPointsList.size()){
			// Vice versa
			xPointsList.add(0, this.getX());
		}
	}

	private void organizeNpcPathData(AIPath foundPath, List<Float> xPointsList, List<Float> yPointsList) {
		for(int i = 0; i < foundPath.getLength(); i++){
			xPointsList.add(new Float(foundPath.getStep(i).getX() * CommonDataManager.TILE_SIZE));
			yPointsList.add(new Float(foundPath.getStep(i).getY() * CommonDataManager.TILE_SIZE));
			
			//Block the tiles on the map through which the path traverses, so that other NPCs don't walk along
			//same path.
			if(i > 0 && i < foundPath.getLength() - 1){
				//Only block the tiles on the map which compose the middle of the path (ie: the tiles between the
				//first and last tile in the path, excluding first and last tiles).
				//this.npcReadableLevelMap.blockTile(foundPath.getStep(i).getX(), foundPath.getStep(i).getY(), GameMap.GUARD);
			}
		}
	}

	private AIPath calculateBestPathToTarget(int xPosOfMoneyBagThief,
			int yPosOfMoneyBagThief) {
		float currentX = this.getX();
		float currentY = this.getY();
		double currentTileColDouble = currentX / CommonDataManager.TILE_SIZE;
		double currentTileRowDouble = currentY / CommonDataManager.TILE_SIZE;

		int currentTileCol = TileUtilities.GetTilePosition((int)currentX);
		int currentTileRow = TileUtilities.GetTilePosition((int)currentY);
		
		float targetXFloat = xPosOfMoneyBagThief / CommonDataManager.TILE_SIZE;
		float targetYFloat = yPosOfMoneyBagThief / CommonDataManager.TILE_SIZE;
		
		int targetTileCol = TileUtilities.GetTilePosition(xPosOfMoneyBagThief);
		int targetTileRow = TileUtilities.GetTilePosition(yPosOfMoneyBagThief);
		
		checkForFatalErrorInPathPoints(currentTileCol, currentTileRow, targetTileCol,targetTileRow);
		
		AIPath foundPath = this.finder.findPath(this, currentTileCol, currentTileRow, targetTileCol, targetTileRow);
		return foundPath;
	}

	private void checkForFatalErrorInPathPoints(int currentTileCol,
			int currentTileRow, int targetX, int targetY) {
		if(currentTileCol < 0 || currentTileCol >= CommonDataManager.NUM_COLS ||
				currentTileRow < 0 || currentTileRow >= CommonDataManager.NUM_ROWS||
				targetX < 0 || targetX >= CommonDataManager.NUM_COLS ||
				targetY < 0 || targetY >= CommonDataManager.NUM_ROWS){
			Debug.e("ERROR! Start and/or End Position in PathFinding method is/are out of bounds!");
			Debug.e("Start Position (r,c): (" + currentTileRow + ", " + currentTileCol + ")");
			Debug.e("End Position (r, c): (" + targetY + ", " + targetX + ")");
		}
	}

	public void setNpcTargetPlayers(List<IPlayableCharacter> playableCharacters) {
		this.playableCharacters = playableCharacters;
	}

	
	protected boolean playerCurrentlyInWater() {
		int currentTileProperty = this.npcReadableLevelMap.getTileProperty((int)this.getX(), (int)this.getY());
		return currentTileProperty == GameMap.WATER;
	}
}
