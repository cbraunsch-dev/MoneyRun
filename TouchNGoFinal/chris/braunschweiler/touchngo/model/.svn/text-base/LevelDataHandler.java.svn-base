package chris.braunschweiler.touchngo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.scene.Scene;

import chris.braunschweiler.touchngo.ai.GameMap;
import chris.braunschweiler.touchngo.ai.TileBasedMap;
import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;
import chris.braunschweiler.touchngo.dto.MoneyBagDTO;
import chris.braunschweiler.touchngo.dto.NpcDTO;
import chris.braunschweiler.touchngo.dto.PlayableCharacterDTO;
import chris.braunschweiler.touchngo.dto.PlayerDTO;
import chris.braunschweiler.touchngo.entities.Bank;
import chris.braunschweiler.touchngo.entities.Goal;
import chris.braunschweiler.touchngo.entities.ICollectable;
import chris.braunschweiler.touchngo.entities.ICollidable;
import chris.braunschweiler.touchngo.entities.IPlayableCharacter;
import chris.braunschweiler.touchngo.entities.IPlayer;
import chris.braunschweiler.touchngo.entities.ICollidedListener;
import chris.braunschweiler.touchngo.entities.IEntity;
import chris.braunschweiler.touchngo.entities.IObstacle;
import chris.braunschweiler.touchngo.entities.MoneyBag;
import chris.braunschweiler.touchngo.entities.PlayerMapModel;
import chris.braunschweiler.touchngo.entities.Switch;
import chris.braunschweiler.touchngo.entities.Wall;
import chris.braunschweiler.touchngo.entities.Water;
import chris.braunschweiler.touchngo.entities.IObstacle.ObstacleType;
import chris.braunschweiler.touchngo.exceptions.GameplayException;
import chris.braunschweiler.touchngo.exceptions.LevelLayerException;
import chris.braunschweiler.touchngo.exceptions.LevelLoadException;
import chris.braunschweiler.touchngo.exceptions.MapLoadException;
import chris.braunschweiler.touchngo.utils.PathPoint;
import chris.braunschweiler.touchngo.utils.TileUtilities;

/**
 * Handles the data of a level. The data includes the layers of a level, as well as the entities within the
 * level.
 * @author chrisbraunschweiler
 *
 */
public class LevelDataHandler implements ILevelDataHandlerTestView {

	private final String backgroundLayerName = "BackgroundLayer";
	private final String playerLayerName = "PlayerLayer";
	private final String moneyBagLayerName = "MoneyBagLayer";
	
	private List<TMXLayer> levelLayers;
	private List<ICollidable> levelCollidables;
	private List<IObstacle> levelObstacles;
	private List<ICollectable> levelCollectables;
	private TMXTiledMap levelMap;
	private int numberOfGoalsInLevel;
	private int numberOfWallsInLevel;
	private int numberOfSwitchesInLevel;
	private int numberOfBanksInLevel;
	private int numberOfWaterUnitsInLevel;
	
	public LevelDataHandler(){
		this.levelLayers = new ArrayList<TMXLayer>();
		this.levelCollidables = new ArrayList<ICollidable>();
		this.levelObstacles = new ArrayList<IObstacle>();
		this.levelCollectables = new ArrayList<ICollectable>();
		this.numberOfGoalsInLevel = 0;
		this.numberOfWallsInLevel = 0;
		this.numberOfSwitchesInLevel = 0;
		this.numberOfBanksInLevel = 0;
		this.numberOfWaterUnitsInLevel = 0;
	}
	
	/**
	 * The list of collidables in the current level.
	 * @return The list of collidables in the currenet level.
	 */
	public final List<ICollidable> getLevelCollidables() {
		return this.levelCollidables;
	}

	public final TMXLayer getLevelLayer(String layerName) throws LevelLayerException {
		for(TMXLayer layer:this.levelLayers){
			if(layer.getName().equals(layerName)){
				return layer;
			}
		}
		
		throw new LevelLayerException("The specified layer with the name " + layerName + " could not be found.");
	}

	public final List<TMXLayer> getLevelLayers() {
		return this.levelLayers;
	}

	public final boolean addLevelLayersToScene(Scene scene) throws LevelLayerException {
		if(this.existsLayerWithName(backgroundLayerName)){
			TMXLayer backgroundLayer = this.getLevelLayer(backgroundLayerName);
			scene.getBottomLayer().addEntity(backgroundLayer);
		}
		if(this.existsLayerWithName(playerLayerName)){
			TMXLayer playerLayer = this.getLevelLayer(playerLayerName);
			scene.getTopLayer().addEntity(playerLayer);
		}
		
		return true;
	}

	public boolean loadLevelEntities(ICollidedListener collidedListener) throws LevelLayerException, LevelLoadException{
		TMXLayer playerLayer = this.getLevelLayer(playerLayerName);
		
		int columnCount = playerLayer.getTileColumns();
		int rowCount = playerLayer.getTileRows();
		
		for( int column = 0; column < columnCount; column++ ) {
			for( int row = 0; row < rowCount; row++ ) {
				final TMXTile tile = playerLayer.getTMXTile(column, row);
	 
				ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
				
				loadLevelCollidable(collidedListener, playerLayer, column, row, tile, tileProperties);
				
				loadLevelObstacle(playerLayer, column, row, tile, tileProperties);
				
				loadLevelCollectable(playerLayer, column, row, tile, tileProperties);
		   }
		}
		
		return true;
	}

	private void loadLevelCollectable(TMXLayer playerLayer, int column,
			int row, TMXTile tile, ArrayList<TMXTileProperty> tileProperties) {
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "collectable")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "collectable").equals("true")){
				
			}
		}
	}

	private int loadNumberOfCoinsInBag(
			ArrayList<TMXTileProperty> tileProperties) throws LevelLoadException{
		int numberOfCoinsInBag = 0;
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "coins")){
			numberOfCoinsInBag = Integer.parseInt(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "coins"));
		}
		
		return numberOfCoinsInBag;
	}

	private void loadLevelObstacle(TMXLayer playerLayer, int column, int row,
			final TMXTile tile, ArrayList<TMXTileProperty> tileProperties) throws LevelLoadException{
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "obstacle")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "obstacle").equals("true")){
				loadWallObstacle(playerLayer, column, row, tile, tileProperties);
				loadWaterObstacle(playerLayer, column, row, tile, tileProperties);
			}
		}
	}

	private void loadWaterObstacle(TMXLayer playerLayer, int column, int row,
			TMXTile tile, ArrayList<TMXTileProperty> tileProperties)
			throws LevelLoadException{
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "water")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "water").equals("true")){
				int globalId = tile.getTileRow() * playerLayer.getTileColumns() + tile.getTileColumn();
				String objectIdentifier = this.loadObjectIdentifier(tileProperties, row, column);
				
				IObstacle water;
				if(objectIdentifier == null){
					water = new Water(column, row, globalId);
				}
				else{
					water = new Water(column, row, globalId, objectIdentifier);
				}
				
				this.levelObstacles.add(water);
				this.numberOfWaterUnitsInLevel++;
			}
		}
	}

	private void loadWallObstacle(TMXLayer playerLayer, int column, int row,
			final TMXTile tile, ArrayList<TMXTileProperty> tileProperties)
			throws LevelLoadException {
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "wall")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "wall").equals("true")){
				int globalId = tile.getTileRow() * playerLayer.getTileColumns() + tile.getTileColumn();
				String objectIdentifier = this.loadObjectIdentifier(tileProperties, row, column);
				
				IObstacle wall;
				if(objectIdentifier == null){
					wall = new Wall(column, row, globalId);
				}
				else{
					wall = new Wall(column, row, globalId, objectIdentifier);
				}
				
				this.levelObstacles.add(wall);
				this.numberOfWallsInLevel++;
			}
		}
	}

	private void loadLevelCollidable(ICollidedListener collidedListener,
			TMXLayer playerLayer, int column, int row, final TMXTile tile,
			ArrayList<TMXTileProperty> tileProperties) throws LevelLoadException {
		if (TileUtilities.listContainsTilePropertyWithName(tileProperties, "collidable")) {
			if (TileUtilities.getValueOfTilePropertyWithName(tileProperties, "collidable").equals("true")) {
				if (TileUtilities.listContainsTilePropertyWithName(tileProperties, "goal")) {
					if (TileUtilities.getValueOfTilePropertyWithName(tileProperties, "goal").equals("true")) {
						loadGoal(collidedListener, playerLayer, column, row, tile, tileProperties);
					}
				}
				else if (TileUtilities.listContainsTilePropertyWithName(tileProperties, "switch")) {
					if (TileUtilities.getValueOfTilePropertyWithName(tileProperties, "switch").equals("true")) {
						loadSwitch(collidedListener, playerLayer, column, row, tile, tileProperties);
					}
				}
				else if (TileUtilities.listContainsTilePropertyWithName(tileProperties, "bank")) {
					if (TileUtilities.getValueOfTilePropertyWithName(tileProperties, "bank").equals("true")) {
						loadBank(collidedListener, playerLayer, column, row, tile, tileProperties);
					}
				}
			}
		}
	}

	private void loadBank(ICollidedListener collidedListener, TMXLayer playerLayer, int column, int row, TMXTile tile,
			ArrayList<TMXTileProperty> tileProperties) throws LevelLoadException{
		int globalId = tile.getTileRow() * playerLayer.getTileColumns() + tile.getTileColumn();
		String objectIdentifier = loadObjectIdentifier(tileProperties, row, column);
		
		ICollidable bank;
		if(objectIdentifier == null){
			bank = new Bank(column, row, globalId);
		}
		else{
			bank = new Bank(column, row, globalId, objectIdentifier);
		}
		
		bank.registerCollidedListener(collidedListener);
		
		this.levelCollidables.add(bank);
		this.numberOfBanksInLevel++;
	}

	private void loadGoal(ICollidedListener collidedListener,TMXLayer playerLayer, int column, int row, final TMXTile tile,
			ArrayList<TMXTileProperty> tileProperties) throws LevelLoadException{
		int globalId = tile.getTileRow() * playerLayer.getTileColumns() + tile.getTileColumn();
		String objectIdentifier = loadObjectIdentifier(tileProperties, row, column);
		
		ICollidable goal;
		if(objectIdentifier == null){
			goal = new Goal(column, row, globalId);
		}
		else{
			goal = new Goal(column, row, globalId, objectIdentifier);
		}
		
		goal.registerCollidedListener(collidedListener);
		
		this.levelCollidables.add(goal);
		this.numberOfGoalsInLevel++;
	}
	
	private void loadSwitch(ICollidedListener collidedListener, TMXLayer playerLayer, int column, int row, final TMXTile tile, ArrayList<TMXTileProperty> tileProperties) throws LevelLoadException {
		int globalId = tile.getTileRow() * playerLayer.getTileColumns() + tile.getTileColumn();
		String objectIdentifier = loadObjectIdentifier(tileProperties, row, column);
		
		String idOfAffectedObjects = null;
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "affectedObjectsId")){
			idOfAffectedObjects = TileUtilities.getValueOfTilePropertyWithName(tileProperties, "affectedObjectsId");
			if(idOfAffectedObjects == null){
				throw new LevelLoadException("The switch at position [row, col]: [" + row + "," + column + "] does not have an idOfAffectedObjects property. It makes not sense to have a switch that doesn't have any affected objects.");
			}
			
			ICollidable theSwitch;
			if(objectIdentifier == null){
				theSwitch = new Switch(column, row, globalId, idOfAffectedObjects);
			}
			else{
				theSwitch = new Switch(column, row, globalId, idOfAffectedObjects, objectIdentifier);
			}
			
			theSwitch.registerCollidedListener(collidedListener);
			
			this.levelCollidables.add(theSwitch);
			this.numberOfSwitchesInLevel++;
		}
		else{
			throw new LevelLoadException("The switch at position [row, col]: [" + row + "," + column + "] does not have an idOfAffectedObjects property. It makes not sense to have a switch that doesn't have any affected objects.");
		}
	}

	private String loadObjectIdentifier(ArrayList<TMXTileProperty> tileProperties, int row, int column) throws LevelLoadException{
		String objectIdentifier = null;
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "oId")){
			return objectIdentifier = TileUtilities.getValueOfTilePropertyWithName(tileProperties, "oId");
		}
		
		throw new LevelLoadException("Object at position [row, col]: [" + row + "," + column + "] does not does not have an object id (oId) property!");
	}

	public boolean existsLayerWithName(String layerName) {
		for(TMXLayer layer:this.levelLayers){
			if(layer.getName().equals(layerName)){
				return true;
			}
		}
		
		return false;
	}

	public boolean setTMXLevelMap(TMXTiledMap levelMap) {
		this.clearDataHandler();
		this.levelMap = levelMap;
		if(this.levelMap!=null){
			for(TMXLayer layer:this.levelMap.getTMXLayers()){
				this.levelLayers.add(layer);
			}
		}
		
		return true;
	}
	
	public TileBasedMap loadNpcReadableMap() throws LevelLoadException, LevelLayerException{
		TMXLayer playerLayer = this.getLevelLayer(playerLayerName);
		
		int columnCount = playerLayer.getTileColumns();
		int rowCount = playerLayer.getTileRows();
		
		int[][] npcReadableMapData = new int[columnCount][rowCount];
		
		for( int column = 0; column < columnCount; column++ ) {
			for( int row = 0; row < rowCount; row++ ) {
				final TMXTile tile = playerLayer.getTMXTile(column, row);
				ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
				
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "water")){
					npcReadableMapData[column][row] = GameMap.WATER;
				}
				else if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "wall")){
					npcReadableMapData[column][row] = GameMap.WALL;
				}
				else{
					npcReadableMapData[column][row] = GameMap.FLATLAND;
				}
		   }
		}
		
		if(columnCount <= 0 || rowCount <= 0){
			throw new LevelLoadException("Unable to generate NPC Readable Map. The TMX layer with name '" + playerLayerName + "' does not contain any tiles.");
		}
		
		return new GameMap(npcReadableMapData);
	}
	
	public PlayerMapModel loadPlayerMapModel() throws LevelLoadException, LevelLayerException{
		TMXLayer playerLayer = this.getLevelLayer(playerLayerName);
		
		int columnCount = playerLayer.getTileColumns();
		int rowCount = playerLayer.getTileRows();
		
		int[][] mapData = new int[columnCount][rowCount];
		
		for( int column = 0; column < columnCount; column++ ) {
			for( int row = 0; row < rowCount; row++ ) {
				final TMXTile tile = playerLayer.getTMXTile(column, row);
				ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
				
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "water")){
					mapData[column][row] = PlayerMapModel.WATER;
				}
				else{
					mapData[column][row] = PlayerMapModel.LAND;
				}
		   }
		}
		
		if(columnCount <= 0 || rowCount <= 0){
			throw new LevelLoadException("Unable to generate Player Map Model for playable character. The TMX layer with name '" + playerLayerName + "' does not contain any tiles.");
		}
		
		return new PlayerMapModel(mapData);
	}

	private void checkForPlayerMoneyBagCollisions(IPlayableCharacter player, TMXLayer playerLayer,
			TMXTile collidableTile, ArrayList<TMXTileProperty> tileProperties) {
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "moneybag")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "moneybag").equals("true")){
				int tileRow = collidableTile.getTileRow();
				int tileColumns = playerLayer.getTileColumns();
				int tileColumn = collidableTile.getTileColumn();
				int tileId = tileRow * tileColumns + tileColumn;
				
				if(this.existsCollectableTileWithGlobalId(tileId)){
					ICollectable moneybag = this.getCollectableWithGlobalId(tileId);
					collidableTile.setTextureRegion(null);
					player.collect(moneybag);
				}
			}
		}
	}

	private void checkForPlayerLevelCollidableCollisions(IPlayableCharacter player, TMXLayer playerLayer,
			TMXTile collidableTile, ArrayList<TMXTileProperty> tileProperties) {
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "collidable")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "collidable").equals("true")){
				checkForPlayerGoalCollisions(player, playerLayer, collidableTile, tileProperties);
				checkForPlayerSwitchCollisions(player, playerLayer, collidableTile, tileProperties);
				checkForPlayerBankCollisions(player, playerLayer, collidableTile, tileProperties);
			}
		}
	}

	private void checkForPlayerBankCollisions(IPlayableCharacter player, TMXLayer playerLayer, TMXTile collidableTile,
			ArrayList<TMXTileProperty> tileProperties) {
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "bank")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "bank").equals("true")){
				int tileRow = collidableTile.getTileRow();
				int tileColumns = playerLayer.getTileColumns();
				int tileColumn = collidableTile.getTileColumn();
				int tileId = tileRow * tileColumns + tileColumn;
				
				if(this.existsCollidableTileWithGlobalId(tileId)){
					ICollidable bank = this.getCollidableWithGlobalId(tileId);
					bank.collideWith(player);
				}
			}
		}
	}

	private void checkForPlayerGoalCollisions(IPlayableCharacter player, TMXLayer playerLayer,
			TMXTile collidableTile, ArrayList<TMXTileProperty> tileProperties) {
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "goal")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "goal").equals("true")){
				int tileRow = collidableTile.getTileRow();
				int tileColumns = playerLayer.getTileColumns();
				int tileColumn = collidableTile.getTileColumn();
				int tileId = tileRow * tileColumns + tileColumn;
				
				if(this.existsCollidableTileWithGlobalId(tileId)){
					ICollidable goal = this.getCollidableWithGlobalId(tileId);
					goal.collideWith(player);
				}
			}
		}
	}
	
	private void checkForPlayerSwitchCollisions(IPlayableCharacter player, TMXLayer playerLayer,
			TMXTile collidableTile, ArrayList<TMXTileProperty> tileProperties) {
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "switch")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "switch").equals("true")){
				int tileRow = collidableTile.getTileRow();
				int tileColumns = playerLayer.getTileColumns();
				int tileColumn = collidableTile.getTileColumn();
				int tileId = tileRow * tileColumns + tileColumn;
				
				if(this.existsCollidableTileWithGlobalId(tileId)){
					ICollidable theSwitch = this.getCollidableWithGlobalId(tileId);
					theSwitch.collideWith(player);
				}
			}
		}
	}
	
	private ICollectable getCollectableWithGlobalId(int tileId) {
		for(ICollectable collectableTile : this.levelCollectables){
			if(collectableTile.getGlobalId() == tileId){
				return collectableTile;
			}
		}
		return null;
	}

	private ICollidable getCollidableWithGlobalId(int tileId) {
		for(ICollidable collidableTile : this.levelCollidables){
			if(collidableTile.getGlobalId() == tileId){
				return collidableTile;
			}
		}
		return null;
	}
	
	private IObstacle getObstacleWithGlobalId(int tileId){
		for(IObstacle obstacleTile : this.levelObstacles){
			if(obstacleTile.getGlobalId() == tileId){
				return obstacleTile;
			}
		}
		return null;
	}
	
	private IObstacle getObstacleAtPosition(int row, int col){
		for(IObstacle obstacleTile : this.levelObstacles){
			if(obstacleTile.getRow() == row && obstacleTile.getCol() == col){
				return obstacleTile;
			}
		}
		
		return null;
	}

	private boolean existsCollidableTileWithGlobalId(int tileId) {
		for(ICollidable collidableTile : this.levelCollidables){
			if(collidableTile.getGlobalId() == tileId){
				return true;
			}
		}
		return false;
	}
	
	private boolean existsCollectableTileWithGlobalId(int tileId) {
		for(ICollectable collectableTile : this.levelCollectables){
			if(collectableTile.getGlobalId() == tileId){
				return true;
			}
		}
		return false;
	}

	public int getNumberOfPlayableCharactersInLevel() throws LevelLayerException{
		TMXLayer playerLayer = this.getLevelLayer(this.playerLayerName);
		
		int numPlayersInLevel = 0;
		int columnCount = playerLayer.getTileColumns();
		int rowCount = playerLayer.getTileRows();
		
		for(int column = 0; column<columnCount; column++){
			for(int row = 0; row<rowCount; row++){
				TMXTile tile = playerLayer.getTMXTile(column, row);

				ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
				
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "player")){
					if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "player").equals("true")){
						numPlayersInLevel++;
					}
				}
			}
		}
		
		return numPlayersInLevel;
	}
	
	public int getNumberOfNonPlayableCharactersInLevel() throws LevelLayerException {
		TMXLayer playerLayer = this.getLevelLayer(this.playerLayerName);
		
		int numPlayersInLevel = 0;
		int columnCount = playerLayer.getTileColumns();
		int rowCount = playerLayer.getTileRows();
		
		for(int column = 0; column<columnCount; column++){
			for(int row = 0; row<rowCount; row++){
				TMXTile tile = playerLayer.getTMXTile(column, row);

				ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
				
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "npc")){
					if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "npc").equals("true")){
						numPlayersInLevel++;
					}
				}
			}
		}
		
		return numPlayersInLevel;
	}

	public PlayableCharacterDTO getPlayableCharacterData(int playerIndex) throws LevelLayerException, MapLoadException{
		TMXLayer playerLayer = this.getLevelLayer(this.playerLayerName);
		
		int playerCount = 0;
		int columnCount = playerLayer.getTileColumns();
		int rowCount = playerLayer.getTileRows();
		
		for(int column = 0; column<columnCount; column++){
			for(int row = 0; row<rowCount; row++){
				TMXTile tile = playerLayer.getTMXTile(column, row);

				ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
				
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "player")){
					if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "player").equals("true")){							
						if(playerCount==playerIndex){
							boolean isLocked = false;
							int lockedUntil = 0;
							PlayerAbility playerAbility = PlayerAbility.NONE;
							if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "lockeduntil")){
								isLocked = true;
								lockedUntil = Integer.parseInt(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "lockeduntil"));
							}
							if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "ability")){
								if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "ability").equals("swim")){
									playerAbility = PlayerAbility.SWIM;
								}
								if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "ability").equals("fly")){
									playerAbility = PlayerAbility.FLY;
								}
							}
							
							PlayableCharacterDTO dto = new PlayableCharacterDTO(tile.getTileX(), tile.getTileY(), isLocked, lockedUntil, playerAbility);
							return dto;
						}
						playerCount++;
					}
				}
			}
		}
		
		throw new MapLoadException("The player with index " + playerIndex + " does not have a player position... Uh oh...");
	}
	
	public NpcDTO getNonPlayableCharacterData(int playerIndex) throws LevelLayerException, MapLoadException {
		TMXLayer playerLayer = this.getLevelLayer(this.playerLayerName);
		
		int playerCount = 0;
		int columnCount = playerLayer.getTileColumns();
		int rowCount = playerLayer.getTileRows();
		
		for(int column = 0; column<columnCount; column++){
			for(int row = 0; row<rowCount; row++){
				TMXTile tile = playerLayer.getTMXTile(column, row);

				ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
				
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "npc")){
					if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "npc").equals("true")){							
						if(playerCount==playerIndex){
							List<PathPoint> patrolPathPoints = retrieveNpcPatrolPath(tileProperties);
							int chaseTargetDistance = -1;
							int changeTargetMidPathDistance = -1;
							PlayerAbility playerAbility = PlayerAbility.NONE;
							
							if (TileUtilities.listContainsTilePropertyWithName(tileProperties, "chasedistance")) {
								chaseTargetDistance = Integer.parseInt(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "chasedistance"));
							}
							if (TileUtilities.listContainsTilePropertyWithName(tileProperties, "changetargetdistance")) {
								changeTargetMidPathDistance = Integer.parseInt(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "changetargetdistance"));
							}
							if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "ability")){
								if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "ability").equals("swim")){
									playerAbility = PlayerAbility.SWIM;
								}
								if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "ability").equals("fly")){
									playerAbility = PlayerAbility.FLY;
								}
							}
							
							NpcDTO npcDTO = new NpcDTO(tile.getTileX(), tile.getTileY(), patrolPathPoints, chaseTargetDistance, changeTargetMidPathDistance, playerAbility);
							return npcDTO;
						}
						playerCount++;
					}
				}
			}
		}
		
		throw new MapLoadException("The NPC with index " + playerIndex + " does not have a player position... Uh oh...");
	}

	private List<PathPoint> retrieveNpcPatrolPath(
			ArrayList<TMXTileProperty> tileProperties) {
		List<PathPoint> patrolPathPoints = new ArrayList<PathPoint>();
		int numberOfPatrolPoints = Integer.parseInt(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "nrpoints"));
		for(int i = 0; i<numberOfPatrolPoints; i++){
			String patrolPathPoint = TileUtilities.getValueOfTilePropertyWithName(tileProperties, "pt" + i);
			StringTokenizer st = new StringTokenizer(patrolPathPoint, ";");
			int patrolPathPointX = Integer.parseInt(st.nextToken());
			int patrolPathPointY = Integer.parseInt(st.nextToken());
			patrolPathPoints.add(new PathPoint(patrolPathPointX, patrolPathPointY));
		}
		return patrolPathPoints;
	}

	public TMXTile getTMXTileAtPosition(float positionX, float positionY)
			throws LevelLayerException {
		TMXLayer playerLayer = this.getLevelLayer(this.playerLayerName);
		return playerLayer.getTMXTileAt(positionX, positionY);
	}
	
	private void clearDataHandler(){
		this.levelCollidables.clear();
		this.levelObstacles.clear();
		this.levelLayers.clear();
		this.numberOfGoalsInLevel = 0;
		this.numberOfWallsInLevel = 0;
		this.numberOfSwitchesInLevel = 0;
		this.numberOfBanksInLevel = 0;
	}

	public int getNumberOfGoalsOfCurrentLevel(){
		return this.numberOfGoalsInLevel;
	}

	public TMXTile getTMXTileAtColAndRow(int col, int row)
			throws LevelLayerException {
		TMXLayer playerLayer = this.getLevelLayer(this.playerLayerName);
		return playerLayer.getTMXTile(col, row);
	}

	public boolean existsGoalAtPosition(float positionX, float positionY) throws LevelLayerException {
		TMXLayer playerLayer = this.getLevelLayer(this.playerLayerName);
		TMXTile tile = playerLayer.getTMXTileAt(positionX, positionY);
		ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
		
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "goal")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "goal").equals("true")){							
				return true;
			}
		}
		
		return false;
	}

	public boolean checkForPlayableCharacterLevelEntityCollisions(float playerX,
			float playerY, IPlayableCharacter player) throws LevelLayerException {
		TMXLayer playerLayer = this.getLevelLayer(playerLayerName);

		TMXTile collidableTile = playerLayer.getTMXTileAt(playerX, playerY);
		
		ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(levelMap, collidableTile);
		
		checkForPlayerLevelCollidableCollisions(player, playerLayer, collidableTile, tileProperties);
		
		return true;
	}

	public boolean isObstacle(TMXTile nextTile, PlayerAbility playerAbility) {
		ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(levelMap, nextTile);
		
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "obstacle")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "obstacle").equals("true")){
				boolean tileIsObstacle = false;
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "water") &&
						(playerAbility != PlayerAbility.SWIM && playerAbility != PlayerAbility.FLY)){
					tileIsObstacle = true;
				}
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "wall") &&
						(playerAbility != PlayerAbility.FLY)){
					tileIsObstacle = true;
				}
				if(tileIsObstacle){
					int row = nextTile.getTileRow();
					int col = nextTile.getTileColumn();
					IObstacle obstacle = this.getObstacleAtPosition(row, col);
					if(obstacle != null && obstacle.isActive()){
						return true;
					}
				}
			}
		}
		
		return false;
	}

	public List<IEntity> getAffectedEntities(String idOfAffectedEntities) throws GameplayException{
		List<IEntity> affectedEntities = new ArrayList<IEntity>();
		for(IEntity collidable: this.levelCollidables){
			if(collidable.getObjectIdentifer() != null && collidable.getObjectIdentifer().equals(idOfAffectedEntities)){
				affectedEntities.add(collidable);
			}
		}
		
		for(IEntity obstacle: this.levelObstacles){
			if(obstacle.getObjectIdentifer() != null && obstacle.getObjectIdentifer().equals(idOfAffectedEntities)){
				affectedEntities.add(obstacle);
			}
		}
		
		if(affectedEntities.size() == 0){
			throw new GameplayException("There are no affected entities with id: " + idOfAffectedEntities + ". This is not a fatal error " +
					"but it makes little sense to have a switch which has no affected objects...");
		}
		
		return affectedEntities;
	}

	public MoneyBagDTO getMoneyBagAtStartingPosition(int moneyBagIndex) throws LevelLayerException, MapLoadException, LevelLoadException {
		TMXLayer moneybagLayer = this.getLevelLayer(this.moneyBagLayerName);
		
		int moneyBagCount = 0;
		int columnCount = moneybagLayer.getTileColumns();
		int rowCount = moneybagLayer.getTileRows();
		
		for(int column = 0; column<columnCount; column++){
			for(int row = 0; row<rowCount; row++){
				TMXTile tile = moneybagLayer.getTMXTile(column, row);

				ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
				
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "moneybag")){
					if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "moneybag").equals("true")){							
						if(moneyBagCount==moneyBagIndex){
							
							MoneyBagDTO moneyBag = loadMoneyBag(tile.getTileX(), tile.getTileY(), row, column, tileProperties);
							
							return moneyBag;
						}
						moneyBagCount++;
					}
				}
			}
		}
		
		throw new MapLoadException("The money bag with index " + moneyBagIndex + " does not have a starting position... Uh oh...");
	}

	private MoneyBagDTO loadMoneyBag(int xPos, int yPos, int row, int col,
			ArrayList<TMXTileProperty> tileProperties) throws LevelLoadException{
		String objectIdentifier = loadObjectIdentifier(tileProperties, row, col);
		
		int numberOfCoinsInBag = loadNumberOfCoinsInBag(tileProperties);
		
		MoneyBagDTO moneyBag = new MoneyBagDTO(objectIdentifier, numberOfCoinsInBag, xPos, yPos);
		
		return moneyBag;
	}

	public int getNumberOfMoneyBagsInLevel() throws LevelLayerException {
		TMXLayer moneyBagLayer = this.getLevelLayer(this.moneyBagLayerName);
		
		int numMoneyBagsInLevel = 0;
		int columnCount = moneyBagLayer.getTileColumns();
		int rowCount = moneyBagLayer.getTileRows();
		
		for(int column = 0; column<columnCount; column++){
			for(int row = 0; row<rowCount; row++){
				TMXTile tile = moneyBagLayer.getTMXTile(column, row);

				ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
				
				if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "moneybag")){
					if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "moneybag").equals("true")){
						numMoneyBagsInLevel++;
					}
				}
			}
		}
		
		return numMoneyBagsInLevel;
	}

	public ObstacleType getObstacleType(TMXTile tile) {
		int globalId = tile.getGlobalTileID();
		IObstacle obstacleAtTile = this.getObstacleWithGlobalId(globalId);
		if(obstacleAtTile != null){
			return obstacleAtTile.getType();
		}
		
		return null;
	}

	public boolean existsCollidableAtPosition(float positionX, float positionY)
			throws LevelLayerException {
		TMXLayer playerLayer = this.getLevelLayer(this.playerLayerName);
		TMXTile tile = playerLayer.getTMXTileAt(positionX, positionY);
		ArrayList<TMXTileProperty> tileProperties = TileUtilities.MakeTilePropertyArrayListForTile(this.levelMap, tile);
		
		if(TileUtilities.listContainsTilePropertyWithName(tileProperties, "collidable")){
			if(TileUtilities.getValueOfTilePropertyWithName(tileProperties, "collidable").equals("true")){							
				return true;
			}
		}
		
		return false;
	}
}
