package chris.braunschweiler.touchngo.activities;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.anddev.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.shape.modifier.MoveModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.StrokeFont;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;

import chris.braunschweiler.touchngo.common.CommonDataManager;
import chris.braunschweiler.touchngo.common.MenuHelper;
import chris.braunschweiler.touchngo.gui.MenuItemContainer;
import chris.braunschweiler.touchngo.gui.PositionalMenuAnimator;
import chris.braunschweiler.touchngo.io.ILevelMetaDataContainer;
import chris.braunschweiler.touchngo.io.LevelDTO;
import chris.braunschweiler.touchngo.io.LevelMetaDataContainerImpl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.widget.Toast;

public class LevelSelectionGameActivity extends BaseGameActivity implements IOnMenuItemClickListener{

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;

	protected static final int MENU_START_LEVEL = 0;
	protected static final int INVALID_MENU_SELECTION = 1;
	protected static final int CONFIRM_HOWTO_MENU_SELECTION = 2;
	
	private static final int SELECTOR_HEIGHT = 300;
	
	private static final int LOCKED_SELECTOR_FONT_SIZE = 36;
	private static final int UNLOCKED_SELECTOR_FONT_SIZE = 48;
	private static final int COINS_COLLECTED_FONT_SIZE = 48;
	
	private static final int NR_COINS_COLLECTED_HUD_X = 95;
	private static final int NR_COINS_COLLECTED_HUD_Y = 20;
	
	//Identifier to identify the GameActivity.
	private final int PLAY_LEVEL = 0;
	
	//The total number of coins collected in the game so far
	private int totalNumberOfCoinsCollected = 0;
	
	//Contains meta data of levels
	private ILevelMetaDataContainer levelMetaDataContainer;

	// ===========================================================
	// Fields
	// ===========================================================

	protected Camera mCamera;

	protected Scene mMainScene;

	protected MenuScene mMenuScene;
	protected MenuScene howToMenuScene;

	private Texture levelSelectorTexture;
	private Texture howToMenuTexture;
	private TextureRegion howToMenuStartLevelTextureRegion;
	private TextureRegion howToImageTextureRegion;
	
	private Texture coinsCollectedFontTexture;
	private Texture lockedLevelSelectorFontTexture;
	private Texture unlockedLevelSelectorFontTexture;
	private StrokeFont coinsCollectedStrokeFont;
	private StrokeFont lockedLevelStrokeFont;
	private StrokeFont unlockedLevelStrokeFont;
	
	private ChangeableText coinsCollectedText;
	
	private List<MenuItemContainer> menuItemContainers = new ArrayList<MenuItemContainer>();
	
	private boolean spawnHowToMenu = false;
	private boolean showingHowToMenu = false;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	
	public void onLoadResources() {
		this.coinsCollectedFontTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		this.lockedLevelSelectorFontTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		this.unlockedLevelSelectorFontTexture = new Texture(256, 256, TextureOptions.DEFAULT);

		this.howToMenuTexture = new Texture(512, 512, TextureOptions.DEFAULT);
		this.howToImageTextureRegion = TextureRegionFactory.createFromAsset(this.howToMenuTexture, this, "gfx/levelselecthowto.png", 0, 100);
		this.howToMenuStartLevelTextureRegion = TextureRegionFactory.createFromAsset(this.howToMenuTexture, this, "gfx/btnok.png", 0, 0);
		
		this.coinsCollectedStrokeFont = new StrokeFont(this.coinsCollectedFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), COINS_COLLECTED_FONT_SIZE, true, Color.WHITE, 2, Color.BLACK);
		this.lockedLevelStrokeFont = new StrokeFont(this.lockedLevelSelectorFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), LOCKED_SELECTOR_FONT_SIZE, true, Color.WHITE, 2, Color.BLACK);
		this.unlockedLevelStrokeFont = new StrokeFont(this.unlockedLevelSelectorFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), UNLOCKED_SELECTOR_FONT_SIZE, true, Color.WHITE, 2, Color.BLACK);
		
		this.levelSelectorTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		
		this.mEngine.getTextureManager().loadTexture(this.howToMenuTexture);
		this.mEngine.getTextureManager().loadTexture(this.levelSelectorTexture);
		this.mEngine.getTextureManager().loadTexture(this.coinsCollectedFontTexture);
		this.mEngine.getTextureManager().loadTexture(this.lockedLevelSelectorFontTexture);
		this.mEngine.getTextureManager().loadTexture(this.unlockedLevelSelectorFontTexture);
		this.mEngine.getFontManager().loadFont(this.coinsCollectedStrokeFont);
		this.mEngine.getFontManager().loadFont(this.lockedLevelStrokeFont);
		this.mEngine.getFontManager().loadFont(this.unlockedLevelStrokeFont);
	}
	
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.levelMetaDataContainer = new LevelMetaDataContainerImpl();
		
		deleteGameProgressIfNecessary();
        
        this.loadGameProgress();
		
		this.createMenuScene();

		/* Just a simple scene with an animated face flying around. */
		this.mMainScene = new Scene(1);
		this.mMainScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		/*ColorBackground backgrnd = new ColorBackground(0,0,0);
		backgrnd.setColor(86,184, 252);
		this.mMainScene.setBackground(backgrnd);*/
		this.addHUDToScene();
		
		return this.mMainScene;
	}
	
    private void addHUDToScene() {
    	this.coinsCollectedText = new ChangeableText(this.NR_COINS_COLLECTED_HUD_X, this.NR_COINS_COLLECTED_HUD_Y, this.coinsCollectedStrokeFont, "Coins: 00/" + CommonDataManager.TOTAL_NUMBER_OF_COINS_IN_GAME, "Coins: 000/000".length());
    	this.mMainScene.getTopLayer().addEntity(this.coinsCollectedText);
	}


	/**
     * Called when the game activity needs to return data to this activity. The data that the game activity
     * returns is the number of coins collected in the level that was played in the game activity.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if (requestCode == PLAY_LEVEL) {
    		if (resultCode == RESULT_OK) {
    			int nrOfCoinsCollectedInLevel = Integer.parseInt(data.getExtras().getString(CommonDataManager.NR_OF_COINS_COLLECTED_IN_LEVEL_KEY));
    			this.totalNumberOfCoinsCollected += nrOfCoinsCollectedInLevel;
    			this.saveGameProgress();
    		}
    	}
    }
    
    private void saveGameProgress() {
    	SharedPreferences levelProgress = getSharedPreferences(CommonDataManager.OVERALL_GAME_PROGRESS_KEY, 0);
	    SharedPreferences.Editor editor = levelProgress.edit();
	    editor.putString(CommonDataManager.TOTAL_NUMBER_OF_COINS_COLLECTED_KEY, "" + this.totalNumberOfCoinsCollected);
	    editor.commit();
	}

	// Deletes any game progress if the user started a new game.
	private void deleteGameProgressIfNecessary() {
    	boolean newGame = userWantsToStartNewGame();
    	
    	if(newGame){
	    	this.rememberThatUserJustStartedNewGame();
    		this.deleteGameProgress();
    		this.spawnHowToMenu = true;
    	}
	}

    private boolean userWantsToStartNewGame() {
    	SharedPreferences prefs = getSharedPreferences(CommonDataManager.GAME_META_DATA_KEY,0);
		String startNewGame = prefs.getString(CommonDataManager.START_NEW_GAME_KEY, CommonDataManager.INVALID_STRING_VALUE);
		if(!startNewGame.equals(CommonDataManager.INVALID_STRING_VALUE)){
			return startNewGame.equals(CommonDataManager.START_NEW_GAME_TRUE);
		}
		
		return false;
	}


	private void rememberThatUserJustStartedNewGame() {
    	SharedPreferences levelProgress = getSharedPreferences(CommonDataManager.GAME_META_DATA_KEY, 0);
	    SharedPreferences.Editor editor = levelProgress.edit();
	    editor.putString(CommonDataManager.START_NEW_GAME_KEY, CommonDataManager.START_NEW_GAME_FALSE);
	    editor.commit();
	}


	private void deleteGameProgress() {
    	// Delete overall game progress
    	SharedPreferences gameProgress = getSharedPreferences(CommonDataManager.OVERALL_GAME_PROGRESS_KEY, 0);
	    SharedPreferences.Editor editor = gameProgress.edit();
	    editor.clear();
	    editor.commit();
	    
	    // Delete progress of each level
	    // TODO: Refactor this using the LevelLoader class. Dynamically delete each level's progress
	    for(LevelDTO level : this.levelMetaDataContainer.getLevels()){
	    	SharedPreferences levelProgress = getSharedPreferences(level.getLevelDataKey(), 0);
		    SharedPreferences.Editor levelEditor = levelProgress.edit();
		    levelEditor.clear();
		    levelEditor.commit();
	    }
	}

	private void loadGameProgress() {
		SharedPreferences prefs = getSharedPreferences(CommonDataManager.OVERALL_GAME_PROGRESS_KEY,0);
		String gameProgress = prefs.getString(CommonDataManager.TOTAL_NUMBER_OF_COINS_COLLECTED_KEY, CommonDataManager.NO_KEY);
		if(!gameProgress.equals(CommonDataManager.NO_KEY)){
			this.totalNumberOfCoinsCollected += Integer.parseInt(gameProgress);
		}
	}
	
	public void onLoadComplete() {
		if(this.mMainScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			this.mMenuScene.back();
		} else {
			/* Attach the menu. */
			this.mMainScene.setChildScene(this.mMenuScene, false, true, true);
		}
		
		if(this.spawnHowToMenu){
			this.spawnHowToMenu = false;
			this.spawnHowToMenu();
		}
		
    	if(this.coinsCollectedText != null){
    		MenuHelper.updateCoinsCollectedHUDText(this.coinsCollectedText, this.totalNumberOfCoinsCollected, CommonDataManager.TOTAL_NUMBER_OF_COINS_IN_GAME);
    	}
	}

	
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {	
		if(!this.showingHowToMenu){
			loadLevel(pMenuItem.getID());
		}
		else{
			if(pMenuItem.getID() == this.CONFIRM_HOWTO_MENU_SELECTION){
				this.showingHowToMenu = false;
				this.mMenuScene.clearChildScene();
			}
		}
		
		return true;
	}
	
    public void onResume(){
    	super.onResume();
    	int i = 1;
    	for(MenuItemContainer menuItemContainer : this.menuItemContainers){
    		this.updateLevelSelectorAnimation(i);
    		i++;
    	}
    	
    	if(this.coinsCollectedText != null){
    		MenuHelper.updateCoinsCollectedHUDText(this.coinsCollectedText, this.totalNumberOfCoinsCollected, CommonDataManager.TOTAL_NUMBER_OF_COINS_IN_GAME);
    	}
    }

	// ===========================================================
	// Methods
	// ===========================================================

	private void loadLevel(int id) {
		String howToScreenImageName = this.levelMetaDataContainer.getHowToImageName(id - 1);
		int coinsRequiredToUnlockLevel = this.levelMetaDataContainer.getNrOfCoinsRequiredToUnlockLevel(id-1);
		String toastMessage = this.levelMetaDataContainer.getToastMessage(id - 1);
		String levelMap = this.levelMetaDataContainer.getLevelMap(id-1);
		String levelDataKey = this.levelMetaDataContainer.getLevelDataKey(id-1);
		String showHowToScreen = this.levelMetaDataContainer.showHowToScreen(id - 1);
		if(coinsRequiredToUnlockLevel != CommonDataManager.INVALID_INT_VALUE){
			if(this.totalNumberOfCoinsCollected >= coinsRequiredToUnlockLevel){
				this.loadLevelAndStartGame(levelMap, levelDataKey, showHowToScreen, howToScreenImageName, toastMessage);
			}
			else{
				this.displayInsufficientCoinsToast();
			}
		}
		else{
			this.displayInsufficientCoinsToast();
		}
	}

	protected void createMenuScene() {
		this.createLevelSelectionMenu();
		this.createHowToMenu();
	}

	private void createLevelSelectionMenu() {
		this.mMenuScene = new MenuScene(this.mCamera);
		this.menuItemContainers.clear();
		
		for(int i = 1; i <= CommonDataManager.TOTAL_NUMBER_OF_LEVELS; i++){
			TiledTextureRegion levelSelectorTextureRegion = this.loadLevelSelectorTextureRegion();
			final AnimatedSpriteMenuItem menuItem = new AnimatedSpriteMenuItem(i, levelSelectorTextureRegion);
			menuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			this.mMenuScene.addMenuItem(menuItem);
			Text lockedSelectorText = this.getLockedSelectorText(i);
			Text unlockedSelectorText = this.getUnlockedSelectorText(i);
			this.menuItemContainers.add(new MenuItemContainer(menuItem, lockedSelectorText, unlockedSelectorText));
			
			this.mMenuScene.getTopLayer().addEntity(lockedSelectorText);
			this.mMenuScene.getTopLayer().addEntity(unlockedSelectorText);
			
			updateLevelSelectorAnimation(i);
		}
		
		this.mMenuScene.setMenuAnimator(new PositionalMenuAnimator(60,100,30,3));
		
		this.mMenuScene.buildAnimations();

		this.mMenuScene.setBackgroundEnabled(false);

		this.mMenuScene.setOnMenuItemClickListener(this);
		
		this.alignMenuItemTextsWithMenuItems();
	}

	private void createHowToMenu() {
		this.howToMenuScene = new MenuScene(this.mCamera);
		this.howToMenuScene.addMenuItem(new SpriteMenuItem(this.INVALID_MENU_SELECTION, this.howToImageTextureRegion));
		this.howToMenuScene.addMenuItem(new SpriteMenuItem(this.CONFIRM_HOWTO_MENU_SELECTION, this.howToMenuStartLevelTextureRegion));
		this.howToMenuScene.buildAnimations();

		this.howToMenuScene.setBackgroundEnabled(false);

		this.howToMenuScene.setOnMenuItemClickListener(this);
	}


	private void alignMenuItemTextsWithMenuItems() {
		for(MenuItemContainer container : this.menuItemContainers){
			float lockedTextPosX = container.getMenuItem().getX() + container.getMenuItem().getWidth() / 2 - container.getLockedLevelText().getWidth() / 2;
			float lockedTextPosY = container.getMenuItem().getY() + container.getMenuItem().getHeight() / 2 - container.getLockedLevelText().getHeight() / 2;
			float unlockedTextPosX = container.getMenuItem().getX() + container.getMenuItem().getWidth() / 2 - container.getUnlockedLevelText().getWidth() / 2;
			float unlockedTextPosY = container.getMenuItem().getY() + container.getMenuItem().getHeight() / 2 - container.getUnlockedLevelText().getHeight() / 2;
			container.getLockedLevelText().setPosition(lockedTextPosX, lockedTextPosY);
			container.getUnlockedLevelText().setPosition(unlockedTextPosX, unlockedTextPosY);
		}
	}


	private Text getUnlockedSelectorText(int i) {
		Text unlockedText = new Text(0, 0, this.unlockedLevelStrokeFont, "" + i, HorizontalAlign.CENTER);
		return unlockedText;
	}


	private Text getLockedSelectorText(int i) {
		int coinsRequiredToUnlockLevel = this.levelMetaDataContainer.getNrOfCoinsRequiredToUnlockLevel(i-1);
		Text lockedText = new Text(0, 0, this.lockedLevelStrokeFont, "x" + coinsRequiredToUnlockLevel, HorizontalAlign.CENTER);
		return lockedText;
	}


	/**
	 * Updates the level selector animation of the given selector.
	 * @param i The index of the selector for which the animation should be updated. NOTE: This index starts at 1, not 0!
	 */
	private void updateLevelSelectorAnimation(int i) {
		int coinsRequiredToUnlockLevel = this.levelMetaDataContainer.getNrOfCoinsRequiredToUnlockLevel(i-1);
		MenuItemContainer itemContainer = this.menuItemContainers.get(i-1);
		AnimatedSpriteMenuItem menuItem = itemContainer.getMenuItem();
		
		String levelCompletedStatus = retrieveLevelCompletedStatus(i);
		
		if(levelCompletedStatus.equals(CommonDataManager.LEVEL_COMPLETED_TRUE)){
			menuItem.animate(new long[]{200, 200}, 2, 3, true);
			itemContainer.getLockedLevelText().setVisible(false);
			itemContainer.getUnlockedLevelText().setVisible(false);
		}
		else{
			menuItem.animate(new long[]{200, 200}, 0, 1, true);
			if (coinsRequiredToUnlockLevel != CommonDataManager.INVALID_INT_VALUE) {
				if(this.totalNumberOfCoinsCollected >= coinsRequiredToUnlockLevel){
					itemContainer.getLockedLevelText().setVisible(false);
					itemContainer.getUnlockedLevelText().setVisible(true);
				}
				else{
					itemContainer.getLockedLevelText().setVisible(true);
					itemContainer.getUnlockedLevelText().setVisible(false);
				}
			}
			else{
				itemContainer.getLockedLevelText().setVisible(true);
				itemContainer.getUnlockedLevelText().setVisible(false);
			}
		}
	}


	private String retrieveLevelCompletedStatus(int i) {
		String levelDataKey = this.levelMetaDataContainer.getLevelDataKey(i-1);
		SharedPreferences prefs = getSharedPreferences(levelDataKey,0);
		String levelCompletedStatus = prefs.getString(CommonDataManager.LEVEL_COMPLETED_KEY, CommonDataManager.NO_KEY);
		return levelCompletedStatus;
	}

	private TiledTextureRegion loadLevelSelectorTextureRegion() {
		return TextureRegionFactory.createTiledFromAsset(this.levelSelectorTexture, this, "gfx/levelselector.png", 0, 0, 2, 2);	
	}


	private void loadLevelAndStartGame(String level, String levelDataKey, String showHowToMenuValue, String howToScreenImageName, String toastMessage) {
		Intent gameIntent = new Intent(LevelSelectionGameActivity.this, MyGameActivity.class);
		gameIntent.putExtra(CommonDataManager.SHOW_HOW_TO_MENU_KEY, showHowToMenuValue);
		gameIntent.putExtra(CommonDataManager.HOW_TO_IMAGE_KEY, howToScreenImageName);
		gameIntent.putExtra(CommonDataManager.TOAST_MESSAGE_KEY, toastMessage);
		gameIntent.putExtra(CommonDataManager.SELECTED_LEVEL_KEY, level);
		gameIntent.putExtra(CommonDataManager.TOTAL_NUMBER_OF_COINS_COLLECTED_KEY, "" + this.totalNumberOfCoinsCollected);
		gameIntent.putExtra(CommonDataManager.LEVEL_STATE_DATA_KEY, levelDataKey);
		this.startActivityForResult(gameIntent, PLAY_LEVEL);
	}
	
	private void spawnHowToMenu(){
		this.showingHowToMenu = true;
		if(this.mMenuScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			this.howToMenuScene.back();
		} else {
			/* Attach the menu. */
			this.mMenuScene.setChildScene(this.howToMenuScene, false, true, true);
		} 
	}
	
	protected void displayInsufficientCoinsToast() {
		Toast.makeText(this, "You don't have enough coins to play this level.", Toast.LENGTH_SHORT).show();
	}
}
