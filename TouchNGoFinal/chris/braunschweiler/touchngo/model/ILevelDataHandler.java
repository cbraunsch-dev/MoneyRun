package chris.braunschweiler.touchngo.model;

import java.util.List;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.scene.Scene;

import chris.braunschweiler.touchngo.ai.TileBasedMap;
import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;
import chris.braunschweiler.touchngo.dto.MoneyBagDTO;
import chris.braunschweiler.touchngo.dto.NpcDTO;
import chris.braunschweiler.touchngo.dto.PlayableCharacterDTO;
import chris.braunschweiler.touchngo.entities.ICollidable;
import chris.braunschweiler.touchngo.entities.IPlayableCharacter;
import chris.braunschweiler.touchngo.entities.IPlayer;
import chris.braunschweiler.touchngo.entities.ICollidedListener;
import chris.braunschweiler.touchngo.entities.IEntity;
import chris.braunschweiler.touchngo.entities.PlayerMapModel;
import chris.braunschweiler.touchngo.entities.IObstacle.ObstacleType;
import chris.braunschweiler.touchngo.exceptions.GameplayException;
import chris.braunschweiler.touchngo.exceptions.LevelLayerException;
import chris.braunschweiler.touchngo.exceptions.LevelLoadException;
import chris.braunschweiler.touchngo.exceptions.MapLoadException;

/**
 * The interface for the level data handler.
 * @author chrisbraunschweiler
 *
 */
public interface ILevelDataHandler {
	
	/**
	 * The size (width and height) of the tiles of the TMX tiled map.
	 */
	final int TILE_SIZE = 80;
	
	final int NUM_ROWS = 10;
	
	final int NUM_COLS = 6;
	
	/**
	 * Returns all tmx level layers of the current level.
	 * @return All TMX level layers of the current level.
	 */
	List<TMXLayer> getLevelLayers();
	
	/**
	 * Retrieves a specific level layer of the current level.
	 * @param layerName The name of the layer which should be retrieved.
	 * @return The specified level layer of the current level.
	 * @throws LevelLayerException if the specified layer could not be found.
	 */
	TMXLayer getLevelLayer(String layerName) throws LevelLayerException;
	
	/**
	 * The list of collidable objects in the current level.
	 * @return The list of collidable objects in the current level.
	 */
	List<ICollidable> getLevelCollidables();
	
	/**
	 * Returns the number of players in the current level.
	 * @return The number of players in the current level.
	 * @throws LevelLayerException If the layer on which the players reside does not exist.
	 */
	int getNumberOfPlayableCharactersInLevel() throws LevelLayerException;
	
	/**
	 * Returns the number of NPCs in the current level.
	 * @return The number of NPCs in the current level.
	 * @throws LevelLayerException If the layer on which the players reside does not exist.
	 */
	int getNumberOfNonPlayableCharactersInLevel() throws LevelLayerException;
	
	/**
	 * Returns the number of moneybags in the current level.
	 * @return The number of money bags in the current level.
	 * @throws LevelLayerException If the layer on which the moneybag resides does not exist.
	 */
	int getNumberOfMoneyBagsInLevel() throws LevelLayerException;
	
	/**
	 * Adds the current level's TMX layers to the given scene.
	 * @param scene The scene to which the level layers should be added.
	 * @throws LevelLayerException if the current level does not contain a layer for the collidables.
	 */
	boolean addLevelLayersToScene(Scene scene) throws LevelLayerException;
	
	/**
	 * Sets the level handler's TMXTiledMap to the tiled map associated with the current level.
	 * @param levelMap The TMXTiledMap associated with the current level.
	 */
	boolean setTMXLevelMap(TMXTiledMap levelMap);
	
	/**
	 * The NPC readable map is generated from the TMX map and returned to the LevelHandler.
	 * @return An NPC-readable map that corresponds to the current level's TMX map.
	 * @throws A LevelLoadException if the NPC-readable map could not be generated.
	 * @throws A LevelLayerException if the layer on which the obstacles reside does not exist.
	 */
	TileBasedMap loadNpcReadableMap() throws LevelLoadException, LevelLayerException;
	
	/**
	 * Loads the player map model of the current level. This allows players to deal with the level in a simplified
	 * form (ie: to see whether or not they're on water, land or whatever).
	 * @return The loaded player map model that corresponds to the current level's tmx map.
	 * @throws LevelLoadException If the player map model could not be loaded.
	 * @throws LevelLayerException If the layer on which the obstacles reside does not exist.
	 */
	PlayerMapModel loadPlayerMapModel() throws LevelLoadException, LevelLayerException;
	
	boolean loadLevelEntities(ICollidedListener collidedListener) throws LevelLayerException, LevelLoadException;
	
	/**
	 * Returns the nth player at its starting position. n is the current index in the params.
	 * @param playerIndex The index of the player for which the starting position should be retrieved.
	 * @return The nth player at his corresponding starting position.
	 * @throws LevelLayerException If the layer on which the player is does not exist.
	 * @throws MapLoadException If an error occurred with the map.
	 */
	PlayableCharacterDTO getPlayableCharacterData(int playerIndex) throws LevelLayerException, MapLoadException;
	
	/**
	 * Returns the nth NPC. n is the current index in the params.
	 * @param playerIndex The index of the npc for which the starting position should be retrieved.
	 * @return The nth npc's starting position.
	 * @throws LevelLayerException If the layer on which the npc is does not exist.
	 * @throws MapLoadException If an error occurred with the map.
	 */
	NpcDTO getNonPlayableCharacterData(int playerIndex) throws LevelLayerException, MapLoadException;
	
	/**
	 * Returns the nth moneybag's starting position. n is the current index in the params.
	 * @param moneyBagIndex The index of the moneybag for which the starting position should be retrieved.
	 * @return The nth moneybag's starting position.
	 * @throws LevelLayerException If the layer on which the money bag is does not exist.
	 * @throws MapLoadException If an error occurred with the map.
	 * @throws LevelLoadException if the money bag has missing properties (such as a missing coins property or a missing identifier property).
	 */
	MoneyBagDTO getMoneyBagAtStartingPosition(int moneyBagIndex) throws LevelLayerException, MapLoadException, LevelLoadException;

	/**
	 * Returns the TMX Tile at the specified position.
	 * @param positionX The x coordinate.
	 * @param positionY The y coordinate.
	 * @return The TMX Tile at the given position.
	 * @throws LevelLayerException if the player layer doesn't exist.
	 */
	TMXTile getTMXTileAtPosition(float positionX, float positionY) throws LevelLayerException;
	
	TMXTile getTMXTileAtColAndRow(int col, int row) throws LevelLayerException;
	
	int getNumberOfGoalsOfCurrentLevel();
	
	/**
	 * Checks whether there exists a goal at the given position.
	 * @param positionX The given x position.
	 * @param positionY The given y position.
	 * @return True if there exists a goal at the specified position, false otherwise.
	 * @throws LevelLayerException If the layer on which the goal resides does not exist on the current level map.
	 */
	boolean existsGoalAtPosition(float positionX, float positionY) throws LevelLayerException;
	
	boolean existsCollidableAtPosition(float positionX, float positionY) throws LevelLayerException;
	
	/**
	 * Checks whether the collidable player passed is colliding with any level entities.
	 * @param positionX The x position of the collidable player.
	 * @param positionY The y position of the collidable player.
	 * @param player The collidable player.
	 * @throws LevelLayerException If the layer on which the collidable resides does not exist on the current level map.
	 */
	boolean checkForPlayableCharacterLevelEntityCollisions(float playerX, float playerY, IPlayableCharacter player) throws LevelLayerException;

	boolean isObstacle(TMXTile nextTile, PlayerAbility playerAbility);
	
	/**
	 * Returns all of the entities with the given id.
	 * @param idOfAffectedEntities The id of the entities which should be returned.
	 * @return All of the entities which have the given id.
	 * @throws GameplayException If no entities exist which have the given id.
	 */
	List<IEntity> getAffectedEntities(String idOfAffectedEntities) throws GameplayException;
	
	/**
	 * Returns the type of the obstalce that's located at the given tile.
	 * @param tile The tile to be checked for.
	 * @return The type of obstacle that resides on the given tile.
	 */
	ObstacleType getObstacleType(TMXTile tile);
}
