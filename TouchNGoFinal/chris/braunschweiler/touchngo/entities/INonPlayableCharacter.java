package chris.braunschweiler.touchngo.entities;

import java.util.List;

import chris.braunschweiler.touchngo.ai.TileBasedMap;

/**
 * Interface for a non playable character (NPC).
 * @author chrisbraunschweiler
 *
 */
public interface INonPlayableCharacter extends IPlayer{
	/**
	 * Puts the NPC in the patrol state.
	 */
	void patrol();
	
	/**
	 * Sets the NPC's list of playable characters. This list is the list of players which the NPC will attack.
	 * @param playableCharacters The list of playable characters the NPC will attack.
	 */
	void setNpcTargetPlayers(List<IPlayableCharacter> playableCharacters);
	
	/**
	 * Updates the NPC.
	 */
	void updatePlayerSprite();
}
