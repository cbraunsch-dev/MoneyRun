package chris.braunschweiler.touchngo.controller;

import java.util.List;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.StrokeFont;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import chris.braunschweiler.touchngo.dto.MoneyBagDTO;
import chris.braunschweiler.touchngo.dto.NpcDTO;
import chris.braunschweiler.touchngo.dto.PlayableCharacterDTO;
import chris.braunschweiler.touchngo.entities.INonPlayableCharacter;
import chris.braunschweiler.touchngo.entities.IPlayableCharacter;
import chris.braunschweiler.touchngo.entities.IPlayer;
import chris.braunschweiler.touchngo.entities.ICollidedListener;
import chris.braunschweiler.touchngo.entities.IEntity;
import chris.braunschweiler.touchngo.entities.MoneyBag;
import chris.braunschweiler.touchngo.entities.PlayerSprite;
import chris.braunschweiler.touchngo.exceptions.GameplayException;
import chris.braunschweiler.touchngo.exceptions.LevelLayerException;
import chris.braunschweiler.touchngo.exceptions.LevelLoadException;
import chris.braunschweiler.touchngo.exceptions.LevelNotFoundException;
import chris.braunschweiler.touchngo.exceptions.MapLoadException;
import chris.braunschweiler.touchngo.exceptions.PlayerLoadException;
import chris.braunschweiler.touchngo.exceptions.PlayerMovementException;

/**
 * Handles everything pertaining to the game level so that the Game Activity doesn't have to.
 * @author chrisbraunschweiler
 *
 */
public interface ILevelHandler {
	
	/**
	 * Passes the total nr of coins collected in the game thus far, to the level handler.
	 * This allows the level handler to unlock playable characters once enough coins have been collected.
	 * @param totalNrOfCoinsCollected The total nr of coins collected thus far in the game.
	 */
	void setTotalNrOfCoinsCollected(int totalNrOfCoinsCollected);
	
	/**
	 * Loads the next level into the handler's memory.
	 */
	public void loadLevel();
	
	/**
	 * Loads the level with the given level map into the handler's memory.
	 * @param levelMap
	 */
	public void loadLevel(String levelMap) throws LevelNotFoundException;
	
	/**
	 * Sets the level handler's TMXTiledMap to the tiled map associated with the current level.
	 * This map can then be used by the NPCs to navigate the map.
	 * @param levelMap The TMXTiledMap associated with the current level.
	 */
	void setTMXLevelMap(TMXTiledMap levelMap);
	
	/**
	 * An NPC-readable map is generated from the current level's TMX map and stored inside the level handler.
	 */
	void generateNpcReadableMap() throws LevelLoadException, LevelLayerException;
	
	/**
	 * Retrieves the level map currently stored in the handler's memory.
	 * @return
	 */
	public String getCurrentLevel();
	
	/**
	 * Returns the current player's x position so that the activity can display a rectangle at that position.
	 * The rectangle is simply used to highlight the current player's position.
	 * @return Returns the current player's x position.
	 */
	public float getCurrentPlayerX();
	
	/**
	 * Returns the current player's y position so that the activity can display a rectangle at that position.
	 * The rectangle is simply used to highlight the current player's position.
	 * @return Returns the current player's y position.
	 */
	public float getCurrentPlayerY();
	
	/**
	 * Returns the total number of coins that can be collected in the level. This value does not consider
	 * the money bags which have already been collected. The total number of coins in a level will always remain
	 * the same for a given level, regardless of how many coins have already been collected. However, the
	 * list of bags whic have already been collected is needed in order to calculate the total nr of coins in the level.
	 * @param The bags which have already been collected. This is needed in order to calculate said value since
	 * it has to add up the number of coins of the bags already collected and add that to the nr of coins of the
	 * bags which haven't been collected yet to get the total nr of coins of the level.
	 * @return The total number of coins that can be collected in the level.
	 */
	public int getTotalNumberOfCoinsInLevel(List<MoneyBagDTO> bagsWhichHaveAlreadyBeenCollected);
	
	/**
	 * Adds all the tmx level layers in the current level to the scene passed.
	 * @param scene The scene to which the layers should be added.
	 */
	void addLevelLayersToScene(Scene scene) throws LevelLayerException;
	
	/**
	 * Loads the level's collidable entities and registers the passed listener to them.
	 * @param collidedListener The listener which listens for collision events from the collidables.
	 * @throws LevelLayerException If the level layer that contains the collidables doesn't exist.
	 */
	void loadLevelCollidables() throws LevelLayerException, LevelLoadException;
	
	/**
	 * Loads and returns the current level's money bags to the activity so that they can be added to the scene.
	 * @param moneyBagTextures The textures that will be used to display the money bags.
	 * @param bagsWhichAlreadyCollected 
	 * @return The current level's list of money bags.
	 * @throws MapLoadException
	 * @throws LevelLayerException
	 * @throws LevelLoadException
	 */
	List<MoneyBag> loadMoneyBags(List<TiledTextureRegion> moneyBagTextures, List<MoneyBagDTO> bagsWhichAlreadyCollected) throws MapLoadException, LevelLayerException, LevelLoadException;
	
	/**
	 * Loads and returns the current level's non-playable characters to the activity so that they can be added to the scene.
	 * @param playerTextures The textuers that will be used to display the players.
	 * @return The current level's list of non playable characters.
	 * @throws LevelLayerException If the TMX layer on which the NPCs are stored is missing or corrupt.
	 * @throws MapLoadException
	 * @throws PlayerLoadException
	 */
	List<INonPlayableCharacter> loadNonPlayableCharacters(List<TiledTextureRegion> playerTextures) throws LevelLayerException, MapLoadException, PlayerLoadException;
	
	/**
	 * Handles the touch event. This gets called when the user touches the screen at the specified x and y position.
	 * @param mTouchX The position where the user touched the screen.
	 * @param mTouchY the position where the user touched the screen.
	 * @throws LevelLayerException
	 * @throws PlayerMovementException
	 * @throws GameplayException
	 */
	void handleTouchEvent(float mTouchX, float mTouchY) throws LevelLayerException, PlayerMovementException, GameplayException;
	
	/**
	 * Updates the level handler.
	 */
	void updateLevelHandler();

	/**
	 * Returns the number of playable players in the level.
	 * @return The number of playable characters in the level.
	 * @throws LevelLayerException 
	 */
	int getNumberOfPlayableCharactersInLevel() throws LevelLayerException;

	/**
	 * Returns a player DTO with the player data of the nth player.
	 * @param playerIndex The index of the player for which to load the data.
	 * @return A player DTO containing all the necessary information to load the nth player.
	 * @throws MapLoadException 
	 * @throws LevelLayerException 
	 */
	PlayableCharacterDTO getPlayableCharacterData(int playerIndex) throws LevelLayerException, MapLoadException;

	/**
	 * Loads the player given the player data and the player's texture.
	 * @param playerDTO The player data used to load the player.
	 * @param playerTexture The texture of the player.
	 * @param swimmingIndicatorSprite Indicates whether the player can swim.
	 * @param waterEffectsSprite The sprite used to display the swimming-in-water-animation. This is passed but only used if the player hast he ability to swim.
	 * @param lockedPlayerSprite The sprite used to display that the player is locked.
	 * @param smallTextFont Font used for the text that displays the number of coins required to unlock the player (if the player is locked).
	 * @param selectedPlayerSprite The sprite used to display that the given player is currently selected.
	 * @return A loaded PlayableCharacter which can be added to the scene.
	 * @throws LevelLayerException 
	 * @throws LevelLoadException 
	 */
	IPlayableCharacter loadPlayableCharacter(PlayableCharacterDTO playerDTO,
			TiledTextureRegion playerTexture, Sprite swimmingIndicatorSprite, AnimatedSprite waterEffectsSprite, AnimatedSprite lockedPlayerSprite, StrokeFont smallTextFont, AnimatedSprite selectedPlayerSprite) throws LevelLoadException, LevelLayerException;

	/**
	 * Highlights the selected player.
	 */
	void highlightSelectedPlayer();

	/**
	 * Returns the number of NPCs in the level.
	 * @return The number of npcs in the current level.
	 * @throws LevelLayerException 
	 */
	int getNumberOfNonPlayableCharactersInLevel() throws LevelLayerException;

	/**
	 * Returns an NPC DTO with the data of the nth NPC.
	 * @param playerIndex The index of the NPC for which to retrieve the NPC data required to fully load the NPC.
	 * @return The NPC data used to fully load the nth NPC.
	 * @throws MapLoadException 
	 * @throws LevelLayerException 
	 */
	NpcDTO getNonPlayableCharacterData(int playerIndex) throws LevelLayerException, MapLoadException;

	/**
	 * Loads the NPC given the NPC data and the texture.
	 * @param playerDTO The data used to fully load the NPC.
	 * @param playerTexture The texture used to display the NPC once it's loaded.
	 * @param swimmingIndicatorSprite Indicates whether the player can swim. 
	 * @param waterEffectsSprite The sprite used for the water animation (when the NPC is in the water).
	 * @param stateIndicatorSprite The sprite used to display an animation when the NPC changes state (ie: from patrol state to pursuit state etc...)
	 * @return A loaded NPC which can be added to the scene.
	 */
	INonPlayableCharacter loadNonPlayableCharacter(NpcDTO playerDTO, TiledTextureRegion playerTexture, Sprite swimmingIndicatorSprite, AnimatedSprite waterEffectsSprite, AnimatedSprite stateIndicatorSprite);

	/**
	 * Passes the level entity marker sprite to the level handler.
	 * @param levelEntityMarkerSprite
	 */
	void setLevelEntityMarkerSprite(AnimatedSprite levelEntityMarkerSprite);
}
