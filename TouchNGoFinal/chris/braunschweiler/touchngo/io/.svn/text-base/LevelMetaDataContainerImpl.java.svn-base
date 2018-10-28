package chris.braunschweiler.touchngo.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import chris.braunschweiler.touchngo.common.CommonDataManager;
import chris.braunschweiler.touchngo.exceptions.LevelNotFoundException;

public class LevelMetaDataContainerImpl implements ILevelMetaDataContainer{

	private ArrayList<LevelDTO> levels;
	private int currentLevelIndex;
	
	public LevelMetaDataContainerImpl(){
		levels = new ArrayList<LevelDTO>();
		currentLevelIndex = 0;
		this.initializeGameLevels();
	}
	
	public int getIndexOfNextLevel(){
		return this.currentLevelIndex;
	}
	
	public List<LevelDTO> getLevels(){
		return this.levels;
	}
	
	public LevelDTO loadLevel() {
		if(currentLevelIndex<levels.size()){
			LevelDTO level = this.levels.get(this.currentLevelIndex);
			this.currentLevelIndex++;
			return level;
		}
		
		return null;
	}
	
	private void initializeGameLevels(){
		levels.add(new LevelDTO(CommonDataManager.LEVEL_1_COINS_REQUIRED, CommonDataManager.LEVEL_1_MAP, CommonDataManager.LEVEL_1_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_TRUE, "gfx/howto1.png", "Touch a tile to move your character. No diagonals."));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_2_COINS_REQUIRED, CommonDataManager.LEVEL_2_MAP, CommonDataManager.LEVEL_2_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_3_COINS_REQUIRED, CommonDataManager.LEVEL_3_MAP, CommonDataManager.LEVEL_3_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_TRUE, "gfx/howto2.png", "Timing is key!"));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_4_COINS_REQUIRED, CommonDataManager.LEVEL_4_MAP, CommonDataManager.LEVEL_4_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_5_COINS_REQUIRED, CommonDataManager.LEVEL_5_MAP, CommonDataManager.LEVEL_5_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_6_COINS_REQUIRED, CommonDataManager.LEVEL_6_MAP, CommonDataManager.LEVEL_6_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_7_COINS_REQUIRED, CommonDataManager.LEVEL_7_MAP, CommonDataManager.LEVEL_7_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_TRUE, "gfx/howto3.png", "There is no I in teamwork!"));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_8_COINS_REQUIRED, CommonDataManager.LEVEL_8_MAP, CommonDataManager.LEVEL_8_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_9_COINS_REQUIRED, CommonDataManager.LEVEL_9_MAP, CommonDataManager.LEVEL_9_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_10_COINS_REQUIRED, CommonDataManager.LEVEL_10_MAP, CommonDataManager.LEVEL_10_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_TRUE, "gfx/howto4.png", "Collect enough coins to bail out your friend!"));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_11_COINS_REQUIRED, CommonDataManager.LEVEL_11_MAP, CommonDataManager.LEVEL_11_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_12_COINS_REQUIRED, CommonDataManager.LEVEL_12_MAP, CommonDataManager.LEVEL_12_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_13_COINS_REQUIRED, CommonDataManager.LEVEL_13_MAP, CommonDataManager.LEVEL_13_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_14_COINS_REQUIRED, CommonDataManager.LEVEL_14_MAP, CommonDataManager.LEVEL_14_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
		levels.add(new LevelDTO(CommonDataManager.LEVEL_15_COINS_REQUIRED, CommonDataManager.LEVEL_15_MAP, CommonDataManager.LEVEL_15_DATAKEY, CommonDataManager.SHOW_HOW_TO_MENU_FALSE, CommonDataManager.INVISIBLE_IMAGE_NAME, CommonDataManager.DEFAULT_TOAST_MESSAGE));
	}

	public LevelDTO loadLevel(String levelMap) throws LevelNotFoundException{
		int indexCount = 1;
		for(LevelDTO level:levels){
			if(level.getLevelMap().equals(levelMap)){
				this.currentLevelIndex = indexCount;
				return level;
			}
			indexCount++;
		}
		
		throw new LevelNotFoundException("Couldn't find a level with the given level map.");
	}

	public int getNrOfCoinsRequiredToUnlockLevel(int levelIndex) {
		for(int i = 0; i < this.levels.size(); i++){
			if(levelIndex == i){
				return this.levels.get(i).getNumberOfCoinsRequiredToUnlockLevel();
			}
		}
		
		return CommonDataManager.INVALID_INT_VALUE;
	}

	public String getLevelDataKey(int levelIndex) {
		for(int i = 0; i < this.levels.size(); i++){
			if(levelIndex == i){
				return this.levels.get(i).getLevelDataKey();
			}
		}
		
		return CommonDataManager.INVALID_INT_VALUE + "";
	}

	public String getLevelMap(int levelIndex) {
		for(int i = 0; i < this.levels.size(); i++){
			if(levelIndex == i){
				return this.levels.get(i).getLevelMap();
			}
		}
		
		return CommonDataManager.INVALID_INT_VALUE + "";
	}

	public String showHowToScreen(int levelIndex) {
		for(int i = 0; i < this.levels.size(); i++){
			if(levelIndex == i){
				return this.levels.get(i).showHowToScreenOnFirstVisit();
			}
		}
		
		return CommonDataManager.INVALID_STRING_VALUE;
	}

	public String getHowToImageName(int levelIndex) {
		for(int i = 0; i < this.levels.size(); i++){
			if(levelIndex == i){
				return this.levels.get(i).getHowToImageName();
			}
		}
		
		return CommonDataManager.INVALID_STRING_VALUE;
	}

	public String getToastMessage(int levelIndex) {
		for(int i = 0; i < this.levels.size(); i++){
			if(levelIndex == i){
				return this.levels.get(i).getToastMessage();
			}
		}
		
		return CommonDataManager.INVALID_STRING_VALUE;
	}
}
