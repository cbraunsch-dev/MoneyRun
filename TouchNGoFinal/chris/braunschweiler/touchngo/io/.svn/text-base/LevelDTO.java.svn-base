package chris.braunschweiler.touchngo.io;

/**
 * Contains the TMXMap file path and the number of permissible moves a player can make in the corresponding
 * level map.
 * @author chrisbraunschweiler
 *
 */
public class LevelDTO {
	
	private int coinsRequiredToUnlock;
	private String levelMap;
	private String levelDataKey;	// Used to retrieve level's saved state from preferences
	private String showHowToScreenOnFirstVisit;	//Holds the value equivalent to true if a how-to-play screen should be shown the first time the level is played
	private String howToImageName;	//The name of the image shown in the how-to screen
	private String toastMessage;	//The toast message that's displayed when the level is started.
	
	public LevelDTO(int coinsRequiredToUnlock, String levelMap, String levelDataKey, String showHowToScreenOnFirstVisit, String howToImageName, String toastMessage){
		this.setNumberOfCoinsRequiredToUnlockLevel(coinsRequiredToUnlock);
		this.setLevelMap(levelMap);
		this.setLevelDataKey(levelDataKey);
		this.setShowHowToScreenOnFirstVisit(showHowToScreenOnFirstVisit);
		this.setHowToImageName(howToImageName);
		this.setToastMessage(toastMessage);
	}
	
	private void setNumberOfCoinsRequiredToUnlockLevel(int numberOfCoinsRequiredToUnlock) {
		this.coinsRequiredToUnlock = numberOfCoinsRequiredToUnlock;
	}
	
	public int getNumberOfCoinsRequiredToUnlockLevel() {
		return coinsRequiredToUnlock;
	}
	
	private void setLevelMap(String levelMap) {
		this.levelMap = levelMap;
	}
	
	public String getLevelMap() {
		return levelMap;
	}
	
	public String getLevelDataKey(){
		return this.levelDataKey;
	}
	
	public void setLevelDataKey(String levelDataKey){
		this.levelDataKey = levelDataKey;
	}
	
	public String showHowToScreenOnFirstVisit(){
		return this.showHowToScreenOnFirstVisit;
	}
	
	public void setShowHowToScreenOnFirstVisit(String showHowToScreenOnFirstVisit){
		this.showHowToScreenOnFirstVisit = showHowToScreenOnFirstVisit;
	}
	
	public String getHowToImageName(){
		return this.howToImageName;
	}
	
	public void setHowToImageName(String howToImageName){
		this.howToImageName = howToImageName;
	}
	
	public String getToastMessage(){
		return this.toastMessage;
	}
	
	public void setToastMessage(String toastMessage){
		this.toastMessage = toastMessage;
	}
}
