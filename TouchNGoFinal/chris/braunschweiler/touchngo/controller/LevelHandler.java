package chris.braunschweiler.touchngo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.PathModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.StrokeFont;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.Path;

import chris.braunschweiler.touchngo.ai.TileBasedMap;
import chris.braunschweiler.touchngo.common.CommonDataManager;
import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;
import chris.braunschweiler.touchngo.container.PlayerMovementDataContainer;
import chris.braunschweiler.touchngo.dto.MoneyBagDTO;
import chris.braunschweiler.touchngo.dto.NpcDTO;
import chris.braunschweiler.touchngo.dto.PlayableCharacterDTO;
import chris.braunschweiler.touchngo.entities.ICollectable;
import chris.braunschweiler.touchngo.entities.ICollidable;
import chris.braunschweiler.touchngo.entities.INonPlayableCharacter;
import chris.braunschweiler.touchngo.entities.INonPlayableCharacterListener;
import chris.braunschweiler.touchngo.entities.IPlayableCharacter;
import chris.braunschweiler.touchngo.entities.IPlayableCharacterListener;
import chris.braunschweiler.touchngo.entities.IPlayer;
import chris.braunschweiler.touchngo.entities.ICollidedListener;
import chris.braunschweiler.touchngo.entities.IEntity;
import chris.braunschweiler.touchngo.entities.IPlayerListener;
import chris.braunschweiler.touchngo.entities.MoneyBag;
import chris.braunschweiler.touchngo.entities.NonPlayableCharacterSprite;
import chris.braunschweiler.touchngo.entities.PlayableCharacterSprite;
import chris.braunschweiler.touchngo.entities.PlayerMapModel;
import chris.braunschweiler.touchngo.entities.PlayerSprite;
import chris.braunschweiler.touchngo.entities.Switch;
import chris.braunschweiler.touchngo.entities.IObstacle.ObstacleType;
import chris.braunschweiler.touchngo.exceptions.GameplayException;
import chris.braunschweiler.touchngo.exceptions.LevelLayerException;
import chris.braunschweiler.touchngo.exceptions.LevelLoadException;
import chris.braunschweiler.touchngo.exceptions.LevelNotFoundException;
import chris.braunschweiler.touchngo.exceptions.MapLoadException;
import chris.braunschweiler.touchngo.exceptions.PlayerLoadException;
import chris.braunschweiler.touchngo.exceptions.PlayerMovementException;
import chris.braunschweiler.touchngo.io.ILevelMetaDataContainer;
import chris.braunschweiler.touchngo.io.LevelDTO;
import chris.braunschweiler.touchngo.io.LevelMetaDataContainerImpl;
import chris.braunschweiler.touchngo.model.ILevelDataHandler;
import chris.braunschweiler.touchngo.model.LevelDataHandler;
import chris.braunschweiler.touchngo.utils.MathUtilities;
import chris.braunschweiler.touchngo.utils.SortableNpcContainer;

/**
 * Handles everything pertaining to the game level so that the Game Activity doesn't have to.
 * @author chrisbraunschweiler
 *
 */
public class LevelHandler implements ILevelHandler, ICollidedListener{
	private ILevelMetaDataContainer levelLoader;
	private ILevelDataHandler levelDataHandler;
	private String currentLevel;
	private ILevelHandlerListener levelHandlerListener;
	
	private float currentPlayerX;
	private float currentPlayerY;
	private ArrayList<IPlayableCharacter> players;
	private IPlayableCharacter selectedPlayer;
	
	private List<MoneyBag> moneyBags;
	private int numberOfCoinsCollectedConsideringBagsAlreadyCollectedInPreviousGame;
	private int numberOfCoinsCollectedRegardlessOfBagsAlreadyCollectedInPreviousGame;
	
	private TileBasedMap enemyReadableLevelMap;
	private ArrayList<INonPlayableCharacter> enemies;
	
	private int totalNrOfCoinsCollected;
	
	private List<MoneyBagDTO> collectedMoneyBags;

	public LevelHandler(ILevelHandlerListener levelHandlerListener) {
		this.levelLoader = new LevelMetaDataContainerImpl();
		this.levelDataHandler = new LevelDataHandler();
		this.currentLevel = null;
		this.levelHandlerListener = null;
		this.currentPlayerX = 0;
		this.currentPlayerY = 0;
		this.players = new ArrayList<IPlayableCharacter>();
		this.selectedPlayer = null;
		this.moneyBags = new ArrayList<MoneyBag>();
		this.numberOfCoinsCollectedConsideringBagsAlreadyCollectedInPreviousGame = 0;
		this.numberOfCoinsCollectedRegardlessOfBagsAlreadyCollectedInPreviousGame = 0;
		this.levelHandlerListener = levelHandlerListener;
		this.enemyReadableLevelMap = null;
		this.enemies = new ArrayList<INonPlayableCharacter>();
		this.totalNrOfCoinsCollected = 0;
		this.collectedMoneyBags = new ArrayList<MoneyBagDTO>();
	}
	
	public void setTotalNrOfCoinsCollected(int totalNrOfCoinsCollected){
		this.totalNrOfCoinsCollected = totalNrOfCoinsCollected;
	}
	
	public void loadLevel(){
		LevelDTO level = this.levelLoader.loadLevel();
		this.currentLevel = level.getLevelMap();
	}

	public String getCurrentLevel() {
		return currentLevel;
	}

	public void loadLevel(String levelMap) throws LevelNotFoundException {
		LevelDTO level = this.levelLoader.loadLevel(levelMap);
		this.currentLevel = level.getLevelMap();
	}

	public void addLevelLayersToScene(Scene scene) throws LevelLayerException {
		this.levelDataHandler.addLevelLayersToScene(scene);
	}

	public void setTMXLevelMap(TMXTiledMap levelMap) {
		this.levelDataHandler.setTMXLevelMap(levelMap);
	}
	
	public void generateNpcReadableMap() throws LevelLoadException, LevelLayerException{
		this.enemyReadableLevelMap = this.levelDataHandler.loadNpcReadableMap();
	}

	public void loadLevelCollidables() throws LevelLayerException, LevelLoadException {
		this.levelDataHandler.loadLevelEntities(this);
	}
	
	public IPlayableCharacter loadPlayableCharacter(PlayableCharacterDTO playerDTO, TiledTextureRegion playerTexture, Sprite swimmingIndicatorSprite, AnimatedSprite waterEffectsSprite, AnimatedSprite lockedPlayerSprite, StrokeFont smallTextFont, AnimatedSprite selectedPlayerSprite) throws LevelLoadException, LevelLayerException {
		float playerXPos = playerDTO.getXPos();
		float playerYPos= playerDTO.getYPos();
		IPlayableCharacterListener playerListener = this.initializePlayableCharacterListener();
		PlayerMapModel playerMapModel = this.levelDataHandler.loadPlayerMapModel();
		IPlayableCharacter player = new PlayableCharacterSprite(playerXPos, playerYPos, playerDTO.isLocked(), playerDTO.getNumberOfCoinsNeededToUnlockPlayableCharacter(), playerTexture, playerListener, playerDTO.getPlayerAbility(), playerMapModel, lockedPlayerSprite, smallTextFont, selectedPlayerSprite);
		if(playerDTO.getPlayerAbility() == PlayerAbility.SWIM){
			player.setSwimmingAbilityIndicatorSprite(swimmingIndicatorSprite);
			player.setWaterEffectsAnimatedSprite(waterEffectsSprite);
		}
		player.tryToUnlock(this.totalNrOfCoinsCollected);
		player.setPlayerTargetPositionX((int) playerXPos);
		player.setPlayerTargetPositionY((int) playerYPos);
		this.players.add(player);

		if(!player.isLocked()){
			this.selectedPlayer = player;
			this.currentPlayerX = player.getX();
			this.currentPlayerY = player.getY();
		}
		
		return player;
	}
	
	public INonPlayableCharacter loadNonPlayableCharacter(NpcDTO playerDTO, TiledTextureRegion playerTexture, Sprite swimmingIndicatorSprite, AnimatedSprite waterEffectsSprite, AnimatedSprite stateIndicatorSprite) { 
		int currentX = playerDTO.getXPos();
		int currentY = playerDTO.getYPos();
		INonPlayableCharacterListener playerListener = this.initializeNonPlayableCharacterListener();
		
		int chaseDistance = playerDTO.getChaseTargetDistance();
		int changeTargetDistance = playerDTO.getChangeTargetMidPathDistance();
		
		INonPlayableCharacter player = new NonPlayableCharacterSprite(currentX, currentY, playerTexture, playerListener, playerDTO.getPatrolPathPoints(), this.enemyReadableLevelMap, chaseDistance, changeTargetDistance, stateIndicatorSprite, playerDTO.getPlayerAbility());
		if(playerDTO.getPlayerAbility() == PlayerAbility.SWIM){
			player.setSwimmingAbilityIndicatorSprite(swimmingIndicatorSprite);
			player.setWaterEffectsAnimatedSprite(waterEffectsSprite);
		}
		player.setNpcTargetPlayers(this.players);
		this.enemies.add(player);
		player.patrol();
		
		return player;
	}

	public List<MoneyBag> loadMoneyBags(List<TiledTextureRegion> moneyBagTextures, List<MoneyBagDTO> bagsWhichHaveAlreadyBeenCollected) throws MapLoadException, LevelLayerException, LevelLoadException{
		List<MoneyBag> moneyBagsForActivity = new ArrayList<MoneyBag>();
		int numMoneyBagsInLevel = this.levelDataHandler.getNumberOfMoneyBagsInLevel();
		for(int bagIndex = 0; bagIndex<numMoneyBagsInLevel; bagIndex++){
			MoneyBagDTO bag = this.levelDataHandler.getMoneyBagAtStartingPosition(bagIndex);
			
			int currentBagX = bag.getXPos();
			int currentBagY = bag.getYPos();
			
			if(!this.bagAlreadyBeenCollected(bag, bagsWhichHaveAlreadyBeenCollected)){
				MoneyBag moneyBag = new MoneyBag(currentBagX, currentBagY, moneyBagTextures.get(bagIndex), bag.getNumberOfCoins());
				this.moneyBags.add(moneyBag);
				moneyBagsForActivity.add(moneyBag);
			}
		}
		
		this.updateListOfCollectedBagsWithBagDataFromSavedGameProgress(bagsWhichHaveAlreadyBeenCollected);		
		this.updateNumberOfCoinsCollectedBasedOnMoneyBagsAlreadyCollected(bagsWhichHaveAlreadyBeenCollected);
		
		return moneyBagsForActivity;
	}

	private void updateNumberOfCoinsCollectedBasedOnMoneyBagsAlreadyCollected(
			List<MoneyBagDTO> bagsWhichHaveAlreadyBeenCollected) {
		for(MoneyBagDTO moneyBag : bagsWhichHaveAlreadyBeenCollected){
			this.numberOfCoinsCollectedConsideringBagsAlreadyCollectedInPreviousGame += moneyBag.getNumberOfCoins();
		}
	}
	
	/**
	 * Adds the bags which have already been collected (the bags retrieved from the saved game
	 * progress) to the level handler's list of alreadyCollected bags.
	 * @param bagsWhichHaveAlreadyBeenCollected
	 */
	private void updateListOfCollectedBagsWithBagDataFromSavedGameProgress(
			List<MoneyBagDTO> bagsWhichHaveAlreadyBeenCollected) {
		for(MoneyBagDTO bag : bagsWhichHaveAlreadyBeenCollected){
			this.collectedMoneyBags.add(bag);
		}
	}

	private boolean bagAlreadyBeenCollected(MoneyBagDTO bag,
			List<MoneyBagDTO> bagsWhichHaveAlreadyBeenCollected) {
		for(MoneyBagDTO bagWhichAlreadyCollected : bagsWhichHaveAlreadyBeenCollected){
			if(bagWhichAlreadyCollected.getXPos() == bag.getXPos() &&
					bagWhichAlreadyCollected.getYPos() == bag.getYPos()){
				return true;
			}
		}
		
		return false;
	}

	@Deprecated
	public List<INonPlayableCharacter> loadNonPlayableCharacters(
			List<TiledTextureRegion> playerTextures)
			throws LevelLayerException, MapLoadException, PlayerLoadException {
		/*List<INonPlayableCharacter> playersForActivity = new ArrayList<INonPlayableCharacter>();
		int numPlayersInLevel = this.levelDataHandler.getNumberOfNonPlayableCharactersInLevel();
		for(int playerIndex = 0; playerIndex<numPlayersInLevel; playerIndex++){
			NpcDTO playerDTO = this.levelDataHandler.getNonPlayableCharacterData(playerIndex);
			
			int currentX = playerDTO.getXPos();
			int currentY = playerDTO.getYPos();
			INonPlayableCharacterListener playerListener = this.initializeNonPlayableCharacterListener();
			
			int chaseDistance = playerDTO.getChaseTargetDistance();
			int changeTargetDistance = playerDTO.getChangeTargetMidPathDistance();
			
			INonPlayableCharacter player = new NonPlayableCharacterSprite(currentX, currentY, playerTextures.get(playerIndex), playerListener, playerDTO.getPatrolPathPoints(), this.enemyReadableLevelMap, chaseDistance, changeTargetDistance);
			player.setNpcTargetPlayers(this.players);
			this.enemies.add(player);
			playersForActivity.add(player);
			player.patrol();
		}

		return playersForActivity;*/
		return null;
	}

	public void handleTouchEvent(float mTouchX, float mTouchY) throws LevelLayerException, PlayerMovementException, GameplayException{
		TMXTile destinationTile = this.levelDataHandler.getTMXTileAtPosition(mTouchX, mTouchY);
	    if(destinationTile != null){
			if(this.selectedNewPlayer(destinationTile)){
		    	this.currentPlayerX = this.selectedPlayer.getX();
		    	this.currentPlayerY = this.selectedPlayer.getY();
		    	this.highlightSelectedPlayer();
		    	this.updateTargetPositionsOfNonSelectedPlayers();
		    }
		    else{
		    	if(this.selectedPlayer.isAlive() && !this.selectedPlayer.isLocked()){
		    		if(destinationTile.getTileX() != this.selectedPlayer.getX() || 
		    			destinationTile.getTileY() != this.selectedPlayer.getY()){
		                if(!this.selectedPlayer.isMoving()){
		                	PlayerMovementDataContainer movementData = preparePlayerMovementDataForOnAxisMovement(destinationTile);
	
			                this.selectedPlayer.movePlayer(movementData.getDestinationX(), movementData.getDestinationY(), movementData.getTimeNeededToMove());
			                this.currentPlayerX = movementData.getDestinationX();
			                this.currentPlayerY = movementData.getDestinationY();
		                }
		                else if(this.selectedPlayer.isMoving()){
		                	if(destinationTile.getTileX() == this.selectedPlayer.getX() || 
		                			destinationTile.getTileY() == this.selectedPlayer.getY()){
		                		// If new destination is on same line/axis as player
		                		PlayerMovementDataContainer movementData = preparePlayerMovementDataForOnAxisMovement(destinationTile);
			                	
			                	this.selectedPlayer.movePlayerAlongSameAxisMidMove(movementData.getDestinationX(), movementData.getDestinationY(), movementData.getTimeNeededToMove());
			                	this.currentPlayerX = movementData.getDestinationX();
				                this.currentPlayerY = movementData.getDestinationY();
		                	}
		                	else{
		                		// IF new destination is off of the axis of the player's current path
			                	// Almost there. Need to make sure that destination tile is only within range of player's current path...
			                	TMXTile sourceTile = null;
			                	sourceTile = tryToDetermineSourceTile(destinationTile, sourceTile);
			                	
			                	destinationTile = this.getDestinationTileConsideringObstacles(sourceTile, destinationTile, this.selectedPlayer.getPlayerAbility());
			                	
			                	float timeNeededToMove = this.calculateTimeNeededToMove(sourceTile, destinationTile);
			                	
			                	int sourceX = sourceTile.getTileX();
			                	int sourceY = sourceTile.getTileY();
			                	int destinationX = destinationTile.getTileX();
			                	int destinationY = destinationTile.getTileY();
			                	
			                	this.selectedPlayer.movePlayerOffAxisMidMove(sourceX, sourceY, destinationX, destinationY, timeNeededToMove);
			                	this.currentPlayerX = destinationX;
				                this.currentPlayerY = destinationY;
		                	}
		                }
		    		}
		    	}
		    }
	    }
	}

	private TMXTile tryToDetermineSourceTile(TMXTile destinationTile,
			TMXTile sourceTile) throws LevelLayerException,
			PlayerMovementException {
		if(this.selectedPlayer.getPlayerTargetPositionX() == this.selectedPlayer.getX()){
			// If player is currently moving vertically
			sourceTile = this.levelDataHandler.getTMXTileAtPosition(this.selectedPlayer.getX(), destinationTile.getTileY());
			
			if(this.selectedPlayer.getPlayerTargetPositionY() > this.selectedPlayer.getY()){
				if(destinationTile.getTileY() > this.selectedPlayer.getPlayerTargetPositionY() ||
						destinationTile.getTileY() < this.selectedPlayer.getY()){
					throw new PlayerMovementException("Invalid mid-movement move.");
				}
			}
			if(this.selectedPlayer.getPlayerTargetPositionY() < this.selectedPlayer.getY()){
				if(destinationTile.getTileY() < this.selectedPlayer.getPlayerTargetPositionY() ||
						destinationTile.getTileY() > this.selectedPlayer.getY()){
					throw new PlayerMovementException("Invalid mid-movement move.");
				}
			}
		}
		else if(this.selectedPlayer.getPlayerTargetPositionY() == this.selectedPlayer.getY()){
			// If player is currently moving horizontally
			sourceTile = this.levelDataHandler.getTMXTileAtPosition(destinationTile.getTileX(), this.selectedPlayer.getY());
			
			if(this.selectedPlayer.getPlayerTargetPositionX() > this.selectedPlayer.getX()){
				if(destinationTile.getTileX() > this.selectedPlayer.getPlayerTargetPositionX() ||
						destinationTile.getTileX() < this.selectedPlayer.getX()){
					throw new PlayerMovementException("Invalid mid-movement move.");
				}
			}
			if(this.selectedPlayer.getPlayerTargetPositionX() < this.selectedPlayer.getX()){
				if(destinationTile.getTileX() < this.selectedPlayer.getPlayerTargetPositionX() ||
						destinationTile.getTileX() > this.selectedPlayer.getX()){
					throw new PlayerMovementException("Invalid mid-movement move.");
				}
			}
		}
		return sourceTile;
	}

	private PlayerMovementDataContainer preparePlayerMovementDataForOnAxisMovement(
			TMXTile destinationTile) throws LevelLayerException,
			PlayerMovementException, GameplayException {
		PlayerMovementDataContainer movementData = new PlayerMovementDataContainer();
		int playerTileCol = (int)this.selectedPlayer.getX() / CommonDataManager.TILE_SIZE;
		int playerTileRow = (int)this.selectedPlayer.getY() / CommonDataManager.TILE_SIZE;
		int playerX = playerTileCol * CommonDataManager.TILE_SIZE;
		int playerY = playerTileRow * CommonDataManager.TILE_SIZE;
		TMXTile sourceTile = this.levelDataHandler.getTMXTileAtPosition(playerX, playerY);
		destinationTile = this.getDestinationTileConsideringObstacles(sourceTile, destinationTile, this.selectedPlayer.getPlayerAbility());
		
		float timeNeededToMove = this.calculateTimeNeededToMove(sourceTile, destinationTile);
		
		int destinationX = destinationTile.getTileX();
		int destinationY = destinationTile.getTileY();
		
		movementData.setDestinationX(destinationX);
		movementData.setDestinationY(destinationY);
		movementData.setTimeNeededToMove(timeNeededToMove);
		return movementData;
	}

	// Updates the target positions of the players which aren't selected to the current position of the players.
	// This will allow NPCs to chase players which aren't selected.
	private void updateTargetPositionsOfNonSelectedPlayers() {
		for(IPlayableCharacter player : this.players){
			if(!player.equals(this.selectedPlayer)){
				player.setPlayerTargetPositionX((int) player.getX());
				player.setPlayerTargetPositionY((int) player.getY());		
			}
		}
	}

	public boolean collided(ICollidable collidable, IPlayer player) {
		switch(collidable.getType()){
		}
		
		return true;
	}
	
	public void collisionBetweenPlayerAndBank(ICollidable bank,
			IPlayableCharacter player) {
		handlePlayerBankCollision(player);
	}

	public void collisionBetweenPlayerAndGoal(ICollidable goal,
			IPlayableCharacter player) {
		handlePlayerGoalCollision();
	}

	public float getCurrentPlayerX() {
		return this.currentPlayerX;
	}

	public float getCurrentPlayerY() {
		return this.currentPlayerY;
	}

	public void updateLevelHandler() {
		for(int i = 0; i < this.enemies.size(); i++){
			this.enemies.get(i).updatePlayerSprite();
		}
		for(int i = 0; i < this.players.size(); i++){
			this.players.get(i).updatePlayerSprite();
		}
	}

	/**
	 * Calculates the destination tile considering all of the obstacles between the given source tile and the given destination tile.
	 * @param sourceTile The source tile.
	 * @param destinationTile The ideal destination tile (the destination if no obstacles are in the way).
	 * @param playerAbility 
	 * @return The actual destination tile considering the obstacles that might be in the way between the given source and the given destination tile.
	 */
	private TMXTile getDestinationTileConsideringObstacles(TMXTile sourceTile, TMXTile destinationTile, PlayerAbility playerAbility) throws PlayerMovementException, LevelLayerException, GameplayException{
		this.checkIfValidMove(sourceTile, destinationTile);
		
		if(this.isAHorizontalMove(sourceTile.getTileY(), destinationTile.getTileY())){
			if(isMovingToTheRight(sourceTile, destinationTile)){
				TMXTile newDestinationTile = retrieveDestinationTileOnTheRight(sourceTile, destinationTile, playerAbility);
				this.checkIfValidMove(sourceTile, newDestinationTile);
				return newDestinationTile;
			}
			else if(isMovingToTheLeft(sourceTile, destinationTile)){
				TMXTile newDestinationTile = retrieveDestinationTileOnTheLeft(sourceTile, destinationTile, playerAbility);
				this.checkIfValidMove(sourceTile, newDestinationTile);
				return newDestinationTile;
			}
		}
		else if(this.isAVerticalMove(sourceTile.getTileX(), destinationTile.getTileX())){
			if(isMovingDown(sourceTile, destinationTile)){
				TMXTile newDestinationTile = retrieveDestinationTileFromBelow(sourceTile, destinationTile, playerAbility);
				this.checkIfValidMove(sourceTile, newDestinationTile);
				return newDestinationTile;
			}
			else if(isMovingUp(sourceTile, destinationTile)){
				TMXTile newDestinationTile = retrieveDestinationTileFromAbove(sourceTile, destinationTile, playerAbility);
				this.checkIfValidMove(sourceTile, newDestinationTile);
				return newDestinationTile;
			}
		}
		
		throw new GameplayException("An error occurred trying to retrieve the destination tile.");
	}

	private TMXTile retrieveDestinationTileFromAbove(TMXTile sourceTile, TMXTile destinationTile, PlayerAbility playerAbility) throws LevelLayerException{
		TMXTile currentTile = sourceTile;
		while(currentTile.getTileY() > destinationTile.getTileY()){
			int nextTileY = currentTile.getTileY() - this.levelDataHandler.TILE_SIZE;
			TMXTile nextTile = this.levelDataHandler.getTMXTileAtPosition(currentTile.getTileX(), nextTileY);
			//Check if next tile is an obstacle
			if(!this.levelDataHandler.isObstacle(nextTile, playerAbility)){
				currentTile = nextTile;
			}
			else{
				return currentTile;
			}
		}
		
		return currentTile;
	}

	private TMXTile retrieveDestinationTileFromBelow(TMXTile sourceTile, TMXTile destinationTile, PlayerAbility playerAbility) throws LevelLayerException{
		TMXTile currentTile = sourceTile;
		while(currentTile.getTileY() < destinationTile.getTileY()){
			int nextTileY = currentTile.getTileY() + this.levelDataHandler.TILE_SIZE;
			TMXTile nextTile = this.levelDataHandler.getTMXTileAtPosition(currentTile.getTileX(), nextTileY);
			//Check if next tile is an obstacle
			if(!this.levelDataHandler.isObstacle(nextTile, playerAbility)){
				currentTile = nextTile;
			}
			else{
				return currentTile;
			}
		}
		
		return currentTile;
	}

	private boolean isMovingUp(TMXTile sourceTile, TMXTile destinationTile) {
		return destinationTile.getTileY() - sourceTile.getTileY() < 0;
	}

	private boolean isMovingDown(TMXTile sourceTile, TMXTile destinationTile) {
		return destinationTile.getTileY() - sourceTile.getTileY() > 0;
	}

	private TMXTile retrieveDestinationTileOnTheLeft(TMXTile sourceTile, TMXTile destinationTile, PlayerAbility playerAbility) throws LevelLayerException {
		TMXTile currentTile = sourceTile;
		while(currentTile.getTileX() > destinationTile.getTileX()){
			int nextTileX = currentTile.getTileX() - this.levelDataHandler.TILE_SIZE;
			TMXTile nextTile = this.levelDataHandler.getTMXTileAtPosition(nextTileX, currentTile.getTileY());
			//Check if next tile is an obstacle
			if(!this.levelDataHandler.isObstacle(nextTile, playerAbility)){
				currentTile = nextTile;
			}
			else{
				return currentTile;
			}
		}
		
		return currentTile;
	}

	private TMXTile retrieveDestinationTileOnTheRight(TMXTile sourceTile, TMXTile destinationTile, PlayerAbility playerAbility) throws LevelLayerException {
		TMXTile currentTile = sourceTile;
		while(currentTile.getTileX() < destinationTile.getTileX()){
			int nextTileX = currentTile.getTileX() + this.levelDataHandler.TILE_SIZE;
			TMXTile nextTile = this.levelDataHandler.getTMXTileAtPosition(nextTileX, currentTile.getTileY());
			//Check if next tile is an obstacle
			if(!this.levelDataHandler.isObstacle(nextTile, playerAbility)){
				currentTile = nextTile;
			}
			else{
				return currentTile;
			}
		}
		
		return currentTile;
	}

	private boolean isMovingToTheLeft(TMXTile sourceTile,
			TMXTile destinationTile) {
		return destinationTile.getTileX() - sourceTile.getTileX() < 0;
	}

	private boolean isMovingToTheRight(TMXTile sourceTile,
			TMXTile destinationTile) {
		return destinationTile.getTileX() - sourceTile.getTileX() > 0;
	}

	private void checkIfValidMove(TMXTile sourceTile, TMXTile destinationTile) throws PlayerMovementException{
		int sourceX = sourceTile.getTileX();
		int sourceY = sourceTile.getTileY();
		int destinationX = destinationTile.getTileX();
		int destinationY = destinationTile.getTileY();
		
		if(this.isAHorizontalMove(sourceY, destinationY)){
			if(sourceX - destinationX != 0){
				return;
			}
		}
		
		if(this.isAVerticalMove(sourceX, destinationX)){
			if(sourceY - destinationY != 0){
				return;
			}
		}
		
		throw new PlayerMovementException("Invalid move.");
	}
	
	private boolean isAHorizontalMove(int sourceY, int destinationY) {
		return (sourceY - destinationY == 0);
	}

	private boolean isAVerticalMove(int sourceX, int destinationX) {
		return (sourceX - destinationX == 0);
	}

	/**
	 * Checks for collisions between the player and the money bags.
	 * @param positionX The current x position of the player.
	 * @param positionY The current y position of the player.
	 * @param player The player.
	 */
	private void checkForPlayerMoneyBagCollisions(float positionX,
			float positionY, IPlayableCharacter player) {
		for(int i = 0; i < this.moneyBags.size(); i++){
			MoneyBag currentBag = this.moneyBags.get(i);
			if(currentBag.collidesWith(player)){
				player.collectMoneyBag(currentBag);
			}
		}
	}

	private IPlayableCharacterListener initializePlayableCharacterListener() {
		IPlayableCharacterListener playerPositionChangedListener = new IPlayableCharacterListener(){

			public void onPositionChanged(float positionX, float positionY,
					IPlayer player) {
				if(player.isAlive()){
					try {
						levelDataHandler.checkForPlayableCharacterLevelEntityCollisions(positionX, positionY, (IPlayableCharacter) player);
						checkForPlayerMoneyBagCollisions(positionX, positionY, (IPlayableCharacter) player);
						checkForPlayerOpponentCollisions(positionX, positionY, (IPlayableCharacter) player);
						
						if(((IPlayableCharacter) player).getPlayerMoneyBag() != null){
							((IPlayableCharacter) player).getPlayerMoneyBag().setPosition(positionX, positionY);
						}
					} catch (LevelLayerException e) {
						Debug.e(e);
						e.printStackTrace();
					}
				}
			}

			public void onCollectableCollected(ICollectable collectable) {
				/*int numberOfCoinsInBag = ((MoneyBag)collectable).getNumberOfCoinsInBag();
				numberOfCoinsCollected += numberOfCoinsInBag;*/
			}

			public void onMoneyBagCollected(MoneyBag moneyBag, IPlayableCharacter player) {
				if(player.isAlive()){
					if(!moneyBag.isAlreadyDeposited() && !moneyBag.isPickedUp() && player.getPlayerMoneyBag() == null){
						player.setPlayerMoneyBag(moneyBag);
						moneyBag.setPickedUp(true);
						moneyBag.animate(new long[]{200, 200}, 2, 3, true);
					}
				}
			}

			public void onDying(IPlayableCharacter player) {
				if(!player.hasAlreadyBeenKilled()){
					if (player.getPlayerMoneyBag() != null) {
						player.dropMoneyBag();
					}
					
					player.setAlreadyKilled(true);
					
					//Check whether all of the players have died
					if(allUnlockedPlayableCharactersAreDead()){
						//levelHandlerListener.onLeaveLevel(0, null);
						levelHandlerListener.onAllPlayersDied();
					}
				}
			}

			private boolean allUnlockedPlayableCharactersAreDead() {
				boolean allAreDead = true;
				for(IPlayableCharacter playableCharacter : players){
					if(playableCharacter.isAlive() && !playableCharacter.isLocked()){
						allAreDead = false;
					}
				}
				
				return allAreDead;
			}
			
			private void dropMoneyBag() {
				
			}
		};
		
		return playerPositionChangedListener;
	}
	
	/**
	 * Checks whether a playable player has collided with an NPC.
	 * @param positionX
	 * @param positionY
	 * @param player The playable character which is checked for collision.
	 */
	private void checkForPlayerOpponentCollisions(float positionX, float positionY, IPlayableCharacter player) {
		for(int i = 0; i < this.enemies.size(); i++){
			INonPlayableCharacter currentEnemy = this.enemies.get(i);
			if(currentEnemy.collidesWith(player)){
				player.die();
			}
		}
	}
	
	private INonPlayableCharacterListener initializeNonPlayableCharacterListener() {
		INonPlayableCharacterListener npcListener = new INonPlayableCharacterListener(){
			public void onPositionChanged(float positionX, float positionY,
					IPlayer player) {
				//Check for collisions between NPC and players
				checkForNpcPlayableCharacterCollisions(positionX, positionY, (INonPlayableCharacter) player);
			}
		};
		
		return npcListener;
	}
	
	/**
	 * Checks whether an NPC has collided with a playable player.
	 * @param positionX
	 * @param positionY
	 * @param player The NPC which is to be checked for collision with a playable character.
	 */
	private void checkForNpcPlayableCharacterCollisions(float positionX, float positionY, INonPlayableCharacter player) {
		for(int i = 0; i < this.players.size(); i++){
			IPlayableCharacter currentPlayer = this.players.get(i);
			if(currentPlayer.collidesWith(player)){
				currentPlayer.die();
			}
		}
	}
	
	private float calculateTimeNeededToMove(TMXTile sourceTile, TMXTile destinationTile) {
		float diffX = Math.abs(destinationTile.getTileRow() - sourceTile.getTileRow());
		float diffY = Math.abs(destinationTile.getTileColumn() - sourceTile.getTileColumn());
		return diffX > 0 ? diffX/2 : diffY/2;
	}
	
	/**
	 * Highlights the currently selected player.
	 */
	public void highlightSelectedPlayer() {
		this.selectedPlayer.highlightPlayer();
		for(IPlayableCharacter player:this.players){
			if(!player.equals(this.selectedPlayer) && player.isAlive()){
				player.unHighlightPlayer();
			}
		}
	}
	
	/**
	 * Returns true if the user selected a new player, false otherwise.
	 * @param destinationTile The tile the user has selected with his finger.
	 * @return True if a player is on the tile which the player selected with his finger (ie: True if the player
	 * has selected a new player).
	 */
	private boolean selectedNewPlayer(TMXTile destinationTile) {		
		try {
			for (IPlayableCharacter player : this.players) {
				if (!this.selectedPlayer.equals(player) && !player.isMoving() && player.isAlive() && !player.isLocked()) {
					TMXTile playerTile = this.levelDataHandler.getTMXTileAtPosition(player.getX(), player.getY());
					if (destinationTile.getTileX() == playerTile.getTileX() &&
							destinationTile.getTileY() == playerTile.getTileY()){
						this.selectedPlayer = player;
						return true;
					}
				}
			}
		}
		catch(LevelLayerException e){
			Debug.e(e);
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void handlePlayerBankCollision(IPlayableCharacter player) {
		MoneyBag moneyBag = player.getPlayerMoneyBag();
		if(moneyBag != null && !moneyBag.isAlreadyDeposited()){
			this.numberOfCoinsCollectedConsideringBagsAlreadyCollectedInPreviousGame += moneyBag.getNumberOfCoinsInBag();
			this.numberOfCoinsCollectedRegardlessOfBagsAlreadyCollectedInPreviousGame += moneyBag.getNumberOfCoinsInBag();
			moneyBag.setAlreadyDeposited(true);
			moneyBag.setVisible(false);
			player.setPlayerMoneyBag(null);
			this.collectedMoneyBags.add(new MoneyBagDTO((int)moneyBag.getStartingPositionX(), (int)moneyBag.getStartingPositionY(), moneyBag.getNumberOfCoinsInBag()));
			Debug.i("Deposited " + moneyBag.getNumberOfCoinsInBag() + " coins.");
						
			tryToUnlockOtherPlayers(player);
			
			this.levelHandlerListener.onMoneyBagDeposited(this.numberOfCoinsCollectedConsideringBagsAlreadyCollectedInPreviousGame);
		}
	}

	private void tryToUnlockOtherPlayers(IPlayableCharacter player) {
		for(int i = 0; i<this.players.size(); i++){
			if(!players.get(i).equals(player) && players.get(i).isAlive()){
				players.get(i).tryToUnlock(this.totalNrOfCoinsCollected + this.numberOfCoinsCollectedConsideringBagsAlreadyCollectedInPreviousGame);
			}
		}
	}
	
	private void handlePlayerSwitchCollision(ICollidable theSwitch) {
		try {
			String idOfAffectedObjects = ((Switch)theSwitch).getIdOfAffectedObjects();
			List<IEntity> affectedEntities = this.levelDataHandler.getAffectedEntities(idOfAffectedObjects);
			for(IEntity affectedObject : affectedEntities){
				affectedObject.toggleActiveFlag();
			}
		} catch (GameplayException e) {
			// TODO Auto-generated catch block
			Debug.i(e.getMessage());
			e.printStackTrace();
		}
	}

	private void handlePlayerGoalCollision() {
		if(this.levelHandlerListener != null){
			this.levelHandlerListener.onLeaveLevel(this.numberOfCoinsCollectedRegardlessOfBagsAlreadyCollectedInPreviousGame, this.collectedMoneyBags);
		}
	}

	public int getTotalNumberOfCoinsInLevel(List<MoneyBagDTO> bagsWhichHaveAlreadyBeenCollected) {
		int totalNrOfCoinsInLevel = 0;
		// Add up the nr of coins of the bags which haven't been collected yet
		for(MoneyBag bag : this.moneyBags){
			totalNrOfCoinsInLevel += bag.getNumberOfCoinsInBag();
		}
		
		// Add up the nr of coins of the bags which have already been collected
		for(MoneyBagDTO bag : bagsWhichHaveAlreadyBeenCollected){
			totalNrOfCoinsInLevel += bag.getNumberOfCoins();
		}
		
		return totalNrOfCoinsInLevel;
	}

	public int getNumberOfPlayableCharactersInLevel() throws LevelLayerException {
		return this.levelDataHandler.getNumberOfPlayableCharactersInLevel();
	}

	public PlayableCharacterDTO getPlayableCharacterData(int playerIndex) throws LevelLayerException, MapLoadException {
		return this.levelDataHandler.getPlayableCharacterData(playerIndex);
	}

	public NpcDTO getNonPlayableCharacterData(int playerIndex) throws LevelLayerException, MapLoadException {
		return this.levelDataHandler.getNonPlayableCharacterData(playerIndex);
	}

	public int getNumberOfNonPlayableCharactersInLevel() throws LevelLayerException {
		return this.levelDataHandler.getNumberOfNonPlayableCharactersInLevel();
	}

	public void setLevelEntityMarkerSprite(
			AnimatedSprite levelEntityMarkerSprite) {
		// TODO Implement this in the next release! This sprite marks the important level entites such as the bank,
		// money bag etc when the user triggers certain events. For example, when the user collects a money bag, this
		// sprite could visually highlight the bank to indicate to the user that he has to deposit the bag in the bank.
		
	}
}