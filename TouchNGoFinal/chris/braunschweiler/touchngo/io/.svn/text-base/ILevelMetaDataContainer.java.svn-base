package chris.braunschweiler.touchngo.io;

import java.util.List;

import chris.braunschweiler.touchngo.exceptions.LevelNotFoundException;

/**
 * The level meta data container used by Touch N Go. This container contains meta data about the game levels.
 * Such meta data includes the number of coins required to unlock a level, the level map which will be loaded in
 * the level, the level's data key used to keep track of saved level state information etc...
 * @author chrisbraunschweiler
 *
 */
public interface ILevelMetaDataContainer {

	/**
	 * Returns the index of the next level to be loaded.
	 * @return Index of the next level to be loaded.
	 */
	public int getIndexOfNextLevel();
	
	/**
	 * Returns all the LevelDTOs.
	 * @return The level DTOs managed by this container.
	 */
	public List<LevelDTO> getLevels();
	
	/**
	 * Loads a level.
	 * @return
	 */
	public LevelDTO loadLevel();
	
	/**
	 * Loads the level with the specified level map.
	 * @param levelMap The level map for which the level should be loaded.
	 * @return The LevelDTO containing the specified level map.
	 * @throws LevelNotFoundException if there is no level with the given level map.
	 */
	public LevelDTO loadLevel(String levelMap) throws LevelNotFoundException;
	
	/**
	 * Returns the number of coins required to unlock the level with the given index.
	 * @param levelIndex The index of the level.
	 * @return The number of coins required to unlock the level with the given index.
	 */
	public int getNrOfCoinsRequiredToUnlockLevel(int levelIndex);
	
	/**
	 * Gets the level map of the nth level where n is the levelIndex passed in the params.
	 * @param levelIndex The level index.
	 * @return The corresponding level map.
	 */
	public String getLevelMap(int levelIndex);
	
	/**
	 * Returns the level data key of the level with the given index.
	 * @param levelIndex The index of the level for which the data key should be retrieved.
	 * @return The corresponding data key used to load the given level's saved progress.
	 */
	public String getLevelDataKey(int levelIndex);
	
	/**
	 * Returns a string value equivalent to true if the level with the given index has a how-to screen that needs to shown.
	 * @param levelIndex Index of the level.
	 * @return A value equivalent to true if a how-to screen should be shown before loading the level with the given index.
	 */
	public String showHowToScreen(int levelIndex);
	
	/**
	 * Gets the name of the how to image of the given level.
	 * @param levelIndex Index of the given level.
	 * @return The name of the how to image of the given level.
	 */
	public String getHowToImageName(int levelIndex);
	
	/**
	 * Returns the toast message that's displayed when the level with the given index is started.
	 * @param levelIndex The index of the level.
	 * @return The toast message that's displayed when the given level is started.
	 */
	public String getToastMessage(int levelIndex);
}
