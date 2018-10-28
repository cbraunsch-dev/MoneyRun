package chris.braunschweiler.touchngo.callbackinterfaces;

import chris.braunschweiler.touchngo.level.LevelEntity;

/**
 * A GameEngineListener listens to requests coming from the Game Engine.
 * @author chrisbraunschweiler
 *
 */
public interface GameEngineListener {
	
	/**
	 * Causes the listener to load the next level for the game engine.
	 */
	public LevelEntity loadNextLevel();
	
	/**
	 * Moves the level loader to the specified level. Useful for reloading a particular level.
	 * @return
	 */
	public void moveToLevel(String levelId);
}
