package chris.braunschweiler.touchngo.dto;

import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;

public class PlayableCharacterDTO extends PlayerDTO{
	private boolean isLocked;
	private int numberOfCoinsNeededToUnlockPlayableCharacter;
	
	
	public PlayableCharacterDTO(int xPos, int yPos, boolean isLocked, int numberOfCoinsNeededToUnlock, PlayerAbility playerAbility){
		super(xPos, yPos, playerAbility);
		this.isLocked = isLocked;
		this.numberOfCoinsNeededToUnlockPlayableCharacter = numberOfCoinsNeededToUnlock;
	}
	
	public boolean isLocked(){
		return this.isLocked;
	}
	
	public int getNumberOfCoinsNeededToUnlockPlayableCharacter(){
		return this.numberOfCoinsNeededToUnlockPlayableCharacter;
	}
}
