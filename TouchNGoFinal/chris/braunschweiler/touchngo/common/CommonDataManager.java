package chris.braunschweiler.touchngo.common;

/**
 * This class manages all of the data shared between different parts of the game application.
 * @author chrisbraunschweiler
 *
 */
public class CommonDataManager {
	//Total number of coins in the game
	public static final int TOTAL_NUMBER_OF_COINS_IN_GAME = 220;
	public static final int TOTAL_NUMBER_OF_LEVELS = 15;
	
	// Keys used to store and retrieve values from preferences and Intent parameters
	public static final String START_NEW_GAME_KEY = "StartNewGame";
	public static final String NO_KEY = "No Key";
	public static final String OVERALL_GAME_PROGRESS_KEY = "OverallGameProgress";
	public static final String TOTAL_NUMBER_OF_COINS_COLLECTED_KEY = "TotalNrOfCoinsCollected";
	public static final String SELECTED_LEVEL_KEY = "SelectedLevel";
	public static final String NR_OF_COINS_COLLECTED_IN_LEVEL_KEY = "NrOfCoinsCollectedInLevel";
	public static final String LEVEL_STATE_DATA_KEY = "LevelStateDataKey";
	public static final String SHOW_HOW_TO_MENU_KEY = "ShowHowToMenuKey";
	public static final String HOW_TO_IMAGE_KEY = "HowToImageKey";
	public static final String TOAST_MESSAGE_KEY = "ToastMessageKey";
	public static final String LEVEL_ALREADY_VISITED_KEY = "LevelAlreadyVisitedKey";
	public static final String GAME_META_DATA_KEY = "GameMetaDataKey";
	
	//The level maps
	public static final String LEVEL_1_MAP = "tmx/gamelevel1map.tmx";
	public static final String LEVEL_2_MAP = "tmx/gamelevel2map.tmx";
	public static final String LEVEL_3_MAP = "tmx/gamelevel3map.tmx";
	public static final String LEVEL_4_MAP = "tmx/gamelevel4map.tmx";
	public static final String LEVEL_5_MAP = "tmx/gamelevel5map.tmx";
	public static final String LEVEL_6_MAP = "tmx/gamelevel6map.tmx";
	public static final String LEVEL_7_MAP = "tmx/gamelevel7map.tmx";
	public static final String LEVEL_8_MAP = "tmx/gamelevel8map.tmx";
	public static final String LEVEL_9_MAP = "tmx/gamelevel9map.tmx";
	public static final String LEVEL_10_MAP = "tmx/gamelevel10map.tmx";
	public static final String LEVEL_11_MAP = "tmx/gamelevel11map.tmx";
	public static final String LEVEL_12_MAP = "tmx/gamelevel12map.tmx";
	public static final String LEVEL_13_MAP = "tmx/gamelevel13map.tmx";
	public static final String LEVEL_14_MAP = "tmx/gamelevel14map.tmx";
	public static final String LEVEL_15_MAP = "tmx/gamelevel15map.tmx";
	
	//The coins required for each level
	public static final int LEVEL_1_COINS_REQUIRED = 0;
	public static final int LEVEL_2_COINS_REQUIRED = 10;
	public static final int LEVEL_3_COINS_REQUIRED = 20;
	public static final int LEVEL_4_COINS_REQUIRED = 30;
	public static final int LEVEL_5_COINS_REQUIRED = 40;
	public static final int LEVEL_6_COINS_REQUIRED = 50;
	public static final int LEVEL_7_COINS_REQUIRED = 60;
	public static final int LEVEL_8_COINS_REQUIRED = 70;
	public static final int LEVEL_9_COINS_REQUIRED = 80;
	public static final int LEVEL_10_COINS_REQUIRED = 95;
	public static final int LEVEL_11_COINS_REQUIRED = 120;
	public static final int LEVEL_12_COINS_REQUIRED = 140;
	public static final int LEVEL_13_COINS_REQUIRED = 150;
	public static final int LEVEL_14_COINS_REQUIRED = 165;
	public static final int LEVEL_15_COINS_REQUIRED = 190;
	
	//The data keys for the different levels
	public static final String LEVEL_1_DATAKEY = "Level1DK";
	public static final String LEVEL_2_DATAKEY = "Level2DK";
	public static final String LEVEL_3_DATAKEY = "Level3DK";
	public static final String LEVEL_4_DATAKEY = "Level4DK";
	public static final String LEVEL_5_DATAKEY = "Level5DK";
	public static final String LEVEL_6_DATAKEY = "Level6DK";
	public static final String LEVEL_7_DATAKEY = "Level7DK";
	public static final String LEVEL_8_DATAKEY = "Level8DK";
	public static final String LEVEL_9_DATAKEY = "Level9DK";
	public static final String LEVEL_10_DATAKEY = "Level10DK";
	public static final String LEVEL_11_DATAKEY = "Level11DK";
	public static final String LEVEL_12_DATAKEY = "Level12DK";
	public static final String LEVEL_13_DATAKEY = "Level13DK";
	public static final String LEVEL_14_DATAKEY = "Level14DK";
	public static final String LEVEL_15_DATAKEY = "Level15DK";
	
	public static final String LEVEL_COMPLETED_KEY = "LevelCompleted";
	public static final String LEVEL_COMPLETED_TRUE = "LevelCompleted_True";
	public static final String LEVEL_COMPLETED_FALSE = "LevelCompleted_False";
	
	//Miscellaneous
	public static final int TILE_SIZE = 80;
	public static final int NUM_COLS = 6;
	public static final int NUM_ROWS = 10;
	
	public static final int INVALID_INT_VALUE = -1;
	public static final String INVALID_STRING_VALUE = "Invalid Value";
	
	public static final String SHOW_HOW_TO_MENU_TRUE = "ShowHowToMenuTrue";
	public static final String SHOW_HOW_TO_MENU_FALSE = "ShowHowToMenuFalse";
	public static final String INVISIBLE_IMAGE_NAME = "gfx/invisibleimg.png";
	public static final String DEFAULT_TOAST_MESSAGE = "Collect and deposit the bags!";
	public static final String LEVEL_ALREADY_VISITED_TRUE = "LevelAlreadyVisitedTrue";
	public static final String START_NEW_GAME_TRUE = "StartNewGameTrue";
	public static final String START_NEW_GAME_FALSE = "StartNewGameFalse";
	
	public enum PlayerAbility{
		SWIM,
		FLY,
		NONE
	}
}
