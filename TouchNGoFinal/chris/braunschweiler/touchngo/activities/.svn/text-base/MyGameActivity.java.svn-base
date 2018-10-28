package chris.braunschweiler.touchngo.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.StrokeFont;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;

import chris.braunschweiler.touchngo.common.CommonDataManager;
import chris.braunschweiler.touchngo.common.MenuHelper;
import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;
import chris.braunschweiler.touchngo.controller.ILevelHandler;
import chris.braunschweiler.touchngo.controller.ILevelHandlerListener;
import chris.braunschweiler.touchngo.controller.LevelHandler;
import chris.braunschweiler.touchngo.dto.MoneyBagDTO;
import chris.braunschweiler.touchngo.dto.NpcDTO;
import chris.braunschweiler.touchngo.dto.PlayableCharacterDTO;
import chris.braunschweiler.touchngo.entities.Goal;
import chris.braunschweiler.touchngo.entities.ICollectable;
import chris.braunschweiler.touchngo.entities.ICollidable;
import chris.braunschweiler.touchngo.entities.INonPlayableCharacter;
import chris.braunschweiler.touchngo.entities.INumberSprite;
import chris.braunschweiler.touchngo.entities.IPlayableCharacter;
import chris.braunschweiler.touchngo.entities.IPlayableCharacterListener;
import chris.braunschweiler.touchngo.entities.IPlayer;
import chris.braunschweiler.touchngo.entities.ICollidedListener;
import chris.braunschweiler.touchngo.entities.IEntity;
import chris.braunschweiler.touchngo.entities.IPlayerListener;
import chris.braunschweiler.touchngo.entities.MoneyBag;
import chris.braunschweiler.touchngo.entities.NumberSprite;
import chris.braunschweiler.touchngo.entities.PlayableCharacterSprite;
import chris.braunschweiler.touchngo.entities.PlayerSprite;
import chris.braunschweiler.touchngo.entities.Switch;
import chris.braunschweiler.touchngo.entities.ICollidable.CollidableType;
import chris.braunschweiler.touchngo.exceptions.DisplayFormatException;
import chris.braunschweiler.touchngo.exceptions.GameplayException;
import chris.braunschweiler.touchngo.exceptions.LevelLayerException;
import chris.braunschweiler.touchngo.exceptions.LevelLoadException;
import chris.braunschweiler.touchngo.exceptions.LevelNotFoundException;
import chris.braunschweiler.touchngo.exceptions.MapLoadException;
import chris.braunschweiler.touchngo.exceptions.PlayerLoadException;
import chris.braunschweiler.touchngo.exceptions.PlayerMovementException;
import chris.braunschweiler.touchngo.io.LevelDTO;
import chris.braunschweiler.touchngo.io.LevelMetaDataContainerImpl;
import chris.braunschweiler.touchngo.utils.TileUtilities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * The main game activity for the game. It displays the current level being played.
 * @author chrisbraunschweiler
 *
 */
public class MyGameActivity extends BaseGameActivity implements IOnSceneTouchListener, ILevelHandlerListener, IOnMenuItemClickListener {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	private static final int NR_COINS_COLLECTED_HUD_X = 95;
	private static final int NR_COINS_COLLECTED_HUD_Y = 20;
	private static final int HUD_SEPARATOR_X = 210;
	private static final int HUD_SEPARATOR_Y = 25;
	private static final int HUD_SEPARATOR_WIDTH = 40;
	private static final int HUD_SEPARATOR_HEIGHT = 60;
	private static final int TOTAL_NR_COINS_HUD_X = 240;
	private static final int TOTAL_NR_COINS_HUD_Y = 30;
	private static final int HEADER_FONT_SIZE = 48;
	private static final int SUB_HEADER_FONT_SIZE = 36;
	private static final int SMALL_TEXT_FONT_SIZE = 36;
	
	//Variables for retrieving and storing game progress
	private static final String NR_OF_MONEYBAGS_ALREADY_COLLECTED = "gameProgress";
	private static final String NO_MONEY_BAGS_COLLECTED = "No progress";
	
	//Variables used by the pause menu
	private static final int PAUSE_MENU_X = 120;
	private static final int PAUSE_MENU_Y = 350;
	private static final int PAUSE_MENU_SPACING = 100;
	private static final int CONTINUE_LEVEL_SELECTION = 0;
	private static final int RESTART_LEVEL_SELECTION = 1;
	private static final int QUIT_GAME_SELECTION = 2;
	private static final int START_GAME_AFTER_HOW_TO_SCREEN_SELECTION = 3;
	private static final int INVALID_MENU_SELECTION = 4;
	
	protected Scene mainScene;
	private String levelProgressKey;
	private Camera gameCamera;
	private TMXTiledMap levelMap;
	private Texture swimmingPlayerIndicatorTexture;
	private TextureRegion swimmingPlayerIndicatorTextureRegion;
	private Texture playableCharacterTexture;
	private Texture lockedCharacterTexture;
	private Texture selectedPlayerTexture;
	private Texture nonPlayableCharacterTexture;
	private Texture npcStateIndicatorTexture;
	private Texture waterEffectsTexture;
	private Texture moneyBagTexture;
	private Texture levelEntityMarkerTexture;
	private Texture userInputFeedbackTexture;
	private ArrayList<TiledTextureRegion> moneyBagTextureRegions = new ArrayList<TiledTextureRegion>();
	private ILevelHandler levelHandler = new LevelHandler(this);
	private MyGameActivityHelper helper = new MyGameActivityHelper();
	private AnimatedSprite levelEntityMarkerSprite;	//Marks/highlights important entities in the level (such as the bank etc)
	private AnimatedSprite userInputFeedbackSprite;	//TODO: This has been ommitted due to performance issues on the magic. Try again in next release or something. Gives the user feedback about his input
	private StrokeFont smallTextFont;
	
	// Menu Related stuff
	protected MenuScene pauseMenuScene;
	protected MenuScene gameOverMenuScene;
	protected MenuScene exitLevelMenuScene;
	private Texture menuTexture;
	protected TextureRegion menuResetTextureRegion;
	protected TextureRegion menuQuitTextureRegion;
	protected TextureRegion menuOkTextureRegion;
	protected TextureRegion menuContinueTextureRegion;
	private Texture headerFontTexture;
	private Texture smallFontTexture;
	private StrokeFont menuHeaderFont;
	private Text gamePausedText;
	private Text gameOverText;
	private Text exitLevelText;
	private ChangeableText coinsCollectedText;
	private int totalNrOfCoinsInLevel;
	private int nrOfCoinsAlreadyCollected;
	private MenuScene howToMenuScene;
	private Texture howToMenuTexture;
	private TextureRegion howToMenuStartLevelTextureRegion;
	private TextureRegion howToImageTextureRegion;
	private boolean showingHowToMenu = false;
	private boolean showingPauseMenu = false;
	private boolean showingExitLevelMenu = false;
	private boolean showingGameOverMenu = false;
	
	/**
	 * Called when the activity has finished loading.
	 */
	public void onLoadComplete() {
		String showHowToScreen = getIntent().getExtras().getString(CommonDataManager.SHOW_HOW_TO_MENU_KEY);
		if(showHowToScreen.equals(CommonDataManager.SHOW_HOW_TO_MENU_TRUE) && !this.levelAlreadyVisited()){
			spawnHowToMenu();
		}
	}

	/**
	 * Called when the engine is loaded.
	 * @return The loaded engine.
	 */
	public Engine onLoadEngine() {
		this.gameCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.gameCamera));
	}

	/**
	 * Called when the resources are being loaded.
	 */
	public void onLoadResources() {
		this.loadPlayerTextures();
		this.loadMoneyBagTextures();
		this.loadNPCTextures();
		this.loadHudStatisticTextures();
		this.loadWaterEffectsTexture();
		this.loadMiscellaneousTextures();
		this.loadMenuTextures();
		this.loadFontTextures();
	}

	private void loadFontTextures() {
		this.headerFontTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		this.smallFontTexture = new Texture(128, 128, TextureOptions.DEFAULT);
		
		this.menuHeaderFont = new StrokeFont(this.headerFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), HEADER_FONT_SIZE, true, Color.BLACK, 2, Color.WHITE);
		this.smallTextFont = new StrokeFont(this.smallFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), this.SMALL_TEXT_FONT_SIZE, true, Color.YELLOW, 2, Color.YELLOW);
		
		this.mEngine.getTextureManager().loadTexture(this.headerFontTexture);
		this.mEngine.getTextureManager().loadTexture(this.smallFontTexture);
		this.mEngine.getFontManager().loadFont(this.menuHeaderFont);
		this.mEngine.getFontManager().loadFont(this.smallTextFont);
	}

	private void loadMenuTextures() {
		this.menuTexture = new Texture(256, 512, TextureOptions.DEFAULT);
		this.howToMenuTexture = new Texture(512, 512, TextureOptions.DEFAULT);
		this.menuResetTextureRegion = TextureRegionFactory.createFromAsset(this.menuTexture, this, "gfx/btnretry.png", 0, 0);
		this.menuQuitTextureRegion = TextureRegionFactory.createFromAsset(this.menuTexture, this, "gfx/btnquit.png", 0, 100);
		this.menuOkTextureRegion = TextureRegionFactory.createFromAsset(this.menuTexture, this, "gfx/btnok.png", 0, 200);
		this.menuContinueTextureRegion = TextureRegionFactory.createFromAsset(this.menuTexture, this, "gfx/btnresume.png", 0, 300);
		this.howToImageTextureRegion = TextureRegionFactory.createFromAsset(this.howToMenuTexture, this, getIntent().getExtras().getString(CommonDataManager.HOW_TO_IMAGE_KEY), 0, 100);
		this.howToMenuStartLevelTextureRegion = TextureRegionFactory.createFromAsset(this.howToMenuTexture, this, "gfx/btnok.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.menuTexture);
		this.mEngine.getTextureManager().loadTexture(this.howToMenuTexture);
	}

	private void loadMiscellaneousTextures() {
		this.swimmingPlayerIndicatorTexture = new Texture(128,128, TextureOptions.DEFAULT);
		this.swimmingPlayerIndicatorTextureRegion = TextureRegionFactory.createFromAsset(this.swimmingPlayerIndicatorTexture, this, "gfx/waterdrop.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.swimmingPlayerIndicatorTexture);
	}

	/**
	 * Called when the scene is being loaded.
	 * @return The loaded scene.
	 */
	public Scene onLoadScene() {
		//this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mEngine.registerUpdateHandler(new IUpdateHandler(){

			public void onUpdate(float pSecondsElapsed) {
				levelHandler.updateLevelHandler();
			}

			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
		});

		this.mainScene = loadScene();
		this.createHowToMenu();
		this.createPauseMenuScene();
		this.createGameOverMenuScene();
		this.createExitLevelMenuScene();
		return this.mainScene;
	}
	
	private void createHowToMenu() {
		this.howToMenuScene = new MenuScene(this.gameCamera);
		this.howToMenuScene.addMenuItem(new SpriteMenuItem(this.INVALID_MENU_SELECTION, this.howToImageTextureRegion));
		this.howToMenuScene.addMenuItem(new SpriteMenuItem(this.START_GAME_AFTER_HOW_TO_SCREEN_SELECTION, this.howToMenuStartLevelTextureRegion));
		this.howToMenuScene.buildAnimations();

		this.howToMenuScene.setBackgroundEnabled(false);

		this.howToMenuScene.setOnMenuItemClickListener(this);
	}

	private void createPauseMenuScene() {
		this.pauseMenuScene = new MenuScene(this.gameCamera);

		final SpriteMenuItem continueMenuItem = new SpriteMenuItem(CONTINUE_LEVEL_SELECTION, this.menuContinueTextureRegion);
		continueMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.pauseMenuScene.addMenuItem(continueMenuItem);
		
		final SpriteMenuItem resetMenuItem = new SpriteMenuItem(RESTART_LEVEL_SELECTION, this.menuResetTextureRegion);
		resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.pauseMenuScene.addMenuItem(resetMenuItem);
		
		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(QUIT_GAME_SELECTION, this.menuQuitTextureRegion);
		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.pauseMenuScene.addMenuItem(quitMenuItem);
		
		//this.pauseMenuScene.setMenuAnimator(new VerticalMenuAnimator(this.PAUSE_MENU_X, this.PAUSE_MENU_Y, this.PAUSE_MENU_SPACING));
		this.pauseMenuScene.buildAnimations();

		this.pauseMenuScene.setBackgroundEnabled(false);

		this.pauseMenuScene.setOnMenuItemClickListener(this);
	}

	private void createGameOverMenuScene() {
		this.gameOverMenuScene = new MenuScene(this.gameCamera);

		final SpriteMenuItem resetMenuItem = new SpriteMenuItem(RESTART_LEVEL_SELECTION, this.menuResetTextureRegion);
		resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.gameOverMenuScene.addMenuItem(resetMenuItem);
		
		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(QUIT_GAME_SELECTION, this.menuQuitTextureRegion);
		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.gameOverMenuScene.addMenuItem(quitMenuItem);
		
		this.gameOverMenuScene.buildAnimations();

		this.gameOverMenuScene.setBackgroundEnabled(false);

		this.gameOverMenuScene.setOnMenuItemClickListener(this);
	}
	
	private void createExitLevelMenuScene() {
		this.exitLevelMenuScene = new MenuScene(this.gameCamera);
		
		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(QUIT_GAME_SELECTION, this.menuOkTextureRegion);
		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.exitLevelMenuScene.addMenuItem(quitMenuItem);
		
		this.exitLevelMenuScene.buildAnimations();

		this.exitLevelMenuScene.setBackgroundEnabled(false);

		this.exitLevelMenuScene.setOnMenuItemClickListener(this);
	}

	private Scene loadScene() {
		final Scene scene = new Scene(2);
		
		try {
			loadLevel();
			this.retrieveTotalNrOfCoinsCollected();
			List<MoneyBagDTO> bagsWhichAlreadyCollected = this.loadSavedLevelProgress();
			this.loadTMXMap();
			this.levelHandler.generateNpcReadableMap();
			this.levelHandler.addLevelLayersToScene(scene);
			this.levelHandler.loadLevelCollidables();
			this.addPlayersToScene(scene);
			this.addMoneyBagsToScene(scene, bagsWhichAlreadyCollected);
			this.addNPCsToScene(scene);
			//this.addMiscellaneousAnimatedSpritesToScene(scene);
			
			this.levelHandler.setLevelEntityMarkerSprite(this.levelEntityMarkerSprite);
			
			this.totalNrOfCoinsInLevel = this.levelHandler.getTotalNumberOfCoinsInLevel(bagsWhichAlreadyCollected);
			//this.loadHUD(scene, bagsWhichAlreadyCollected, totalNrOfCoinsInLevel);
			
			this.addDestinationRectangleToScene(scene);
			
			this.addTextsToScene(scene);
			this.loadAndDisplayNumberOfCoinsAlreadyCollectedInHud(bagsWhichAlreadyCollected);
			
			scene.setOnSceneTouchListener(this);
		} catch (LevelLayerException e) {
			Debug.e(e);
			e.printStackTrace();
		}
		catch(LevelLoadException e){
			Debug.e(e);
			e.printStackTrace();
		} catch (DisplayFormatException e) {
			// TODO Auto-generated catch block
			Debug.e(e);
			e.printStackTrace();
		}
		
		return scene;
	}
	
	private void addTextsToScene(Scene scene) {
		this.gamePausedText = new Text(85, 180, this.menuHeaderFont, "Game Paused", HorizontalAlign.CENTER);
		this.gamePausedText.setVisible(false);
		this.gameOverText = new Text(110, 200, this.menuHeaderFont, "Game Over", HorizontalAlign.CENTER);
		this.gameOverText.setVisible(false);
		this.exitLevelText = new Text(60, 200, this.menuHeaderFont, "Leaving Level...", HorizontalAlign.CENTER);
		this.exitLevelText.setVisible(false);
		
		String totalCoinsAvailableString;
		if(this.totalNrOfCoinsInLevel < 10){
			totalCoinsAvailableString = "0" + this.totalNrOfCoinsInLevel;
		}
		else{
			totalCoinsAvailableString = "" + this.totalNrOfCoinsInLevel;
		}
		this.coinsCollectedText = new ChangeableText(this.NR_COINS_COLLECTED_HUD_X, this.NR_COINS_COLLECTED_HUD_Y, this.menuHeaderFont, "Coins: 00/" + totalCoinsAvailableString, "Coins: 00/00".length());
		
		scene.getTopLayer().addEntity(this.gamePausedText);
		scene.getTopLayer().addEntity(this.gameOverText);
		scene.getTopLayer().addEntity(this.exitLevelText);
		scene.getTopLayer().addEntity(this.coinsCollectedText);
	}

	/**
	 * Adds miscellaneous animated sprites to the scene. Such sprites include highlighting animations of important
	 * level items, as well as animations indicating a correct/incorrect player move.
	 * @param scene
	 */
	private void addMiscellaneousAnimatedSpritesToScene(Scene scene) {
		TiledTextureRegion levelEntityMarkerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.levelEntityMarkerTexture, this, "gfx/guard1.png", 0, 0, 2, 2);
		this.levelEntityMarkerSprite = new AnimatedSprite(0, 0, CommonDataManager.TILE_SIZE, CommonDataManager.TILE_SIZE, levelEntityMarkerTextureRegion);
		TiledTextureRegion userFeedbackTextureRegion = TextureRegionFactory.createTiledFromAsset(this.userInputFeedbackTexture, this, "gfx/userfeedback.png", 0, 0, 5, 3);
		this.userInputFeedbackSprite = new AnimatedSprite(0, 0, CommonDataManager.TILE_SIZE, CommonDataManager.TILE_SIZE, userFeedbackTextureRegion);
		
		//scene.getTopLayer().addEntity(this.levelEntityMarkerSprite);
		scene.getTopLayer().addEntity(this.userInputFeedbackSprite);
	}

	/* Creates the menu items of the pause menu */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,CONTINUE_LEVEL_SELECTION,0,"Continue");
		menu.add(0,RESTART_LEVEL_SELECTION,0,"Restart level");
	    menu.add(0,QUIT_GAME_SELECTION,0,"Quit");
	    return true;
	}

	/* Handles item selections of the pause menu */
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case RESTART_LEVEL_SELECTION:
		        this.restartActivity();
		        return true;
		    case QUIT_GAME_SELECTION:
		        finish();
		        return true;
	    }
	    return false;
	}
	
	private void restartActivity(){
		this.showingPauseMenu = false;
		this.showingExitLevelMenu = false;
		this.showingHowToMenu = false;
		this.showingGameOverMenu = false;
		this.levelHandler = new LevelHandler(this);
		this.createPauseMenuScene();
		this.createGameOverMenuScene();
		this.createExitLevelMenuScene();
		this.mainScene = this.loadScene();
		this.getEngine().setScene(this.mainScene);
	}

	private void loadAndDisplayNumberOfCoinsAlreadyCollectedInHud(List<MoneyBagDTO> bagsWhichAlreadyCollected) throws DisplayFormatException {
		this.nrOfCoinsAlreadyCollected = 0;
		for(MoneyBagDTO bag : bagsWhichAlreadyCollected){
			nrOfCoinsAlreadyCollected += bag.getNumberOfCoins();
		}
		
		MenuHelper.updateCoinsCollectedHUDText(this.coinsCollectedText, nrOfCoinsAlreadyCollected, this.totalNrOfCoinsInLevel);
	}

	/**
	 * Loads the player's progress for this level. The progress of a level contains the coordinates of
	 * the money bags which have already been collected.
	 * @return Returns the money bags in this level which have already been collected.
	 */
	private List<MoneyBagDTO> loadSavedLevelProgress() {
		List<MoneyBagDTO> bagsWhichHaveAlreadyBeenCollected = new ArrayList<MoneyBagDTO>();
	    this.levelProgressKey = null;
	    if(this.getIntent().getExtras()!=null){
	    	this.levelProgressKey = this.getIntent().getExtras().getString(CommonDataManager.LEVEL_STATE_DATA_KEY);
	    }
	    if(this.levelProgressKey != null){
			SharedPreferences prefs = getSharedPreferences(levelProgressKey,0);
			SharedPreferences.Editor editor = prefs.edit();
			String gameProgress = prefs.getString(NR_OF_MONEYBAGS_ALREADY_COLLECTED, NO_MONEY_BAGS_COLLECTED);
			if(!gameProgress.equals(NO_MONEY_BAGS_COLLECTED)){
				int numberOfBagsCollected = Integer.parseInt(gameProgress);
				for(int i = 0; i < numberOfBagsCollected; i++){
					String noBagData = "No Bag";
					String bagDataKey = "bag" + i;
					String collectedBagData = prefs.getString(bagDataKey, noBagData);
					if(!collectedBagData.equals(noBagData)){
						StringTokenizer st = new StringTokenizer(collectedBagData, ",");
						String collectedBagXString = st.nextToken();
						int collectedBagX = Integer.parseInt(collectedBagXString);
						String collectedBagYString = st.nextToken();
						int collectedBagY = Integer.parseInt(collectedBagYString);
						String collectedBagNrOfCoinsString = st.nextToken();
						int collectedBagNrOfCoins = Integer.parseInt(collectedBagNrOfCoinsString);
						
						bagsWhichHaveAlreadyBeenCollected.add(new MoneyBagDTO(collectedBagX, collectedBagY, collectedBagNrOfCoins));
					}
				}
			}
	    }
	    
	    return bagsWhichHaveAlreadyBeenCollected;
	}

	private void retrieveTotalNrOfCoinsCollected() {
		int totalNrOfCoinsCollected = Integer.parseInt(getIntent().getExtras().getString(CommonDataManager.TOTAL_NUMBER_OF_COINS_COLLECTED_KEY));
		this.levelHandler.setTotalNrOfCoinsCollected(totalNrOfCoinsCollected);
	}

	public void setLevelHandler(ILevelHandler levelHandler){
		this.levelHandler = levelHandler;
	}
	
	public void setActivityHelper(MyGameActivityHelper helper){
		this.helper = helper;
	}
	
	public void loadLevel() {
		String gameProgress = null;
        if(this.helper.gameProgressExists()){
        	try{
        		gameProgress = getIntent().getExtras().getString(CommonDataManager.SELECTED_LEVEL_KEY);
            	this.levelHandler.loadLevel(gameProgress);
        	}
        	catch(LevelNotFoundException e){
        		Debug.e(e.getMessage());
        	}
        }
        else{
    		loadNextLevel();
        }
	}

	private void loadNextLevel() {
		this.levelHandler.loadLevel();
	}
	
	private void loadPlayerTextures(){
		this.playableCharacterTexture = new Texture(512, 512, TextureOptions.DEFAULT);
		this.lockedCharacterTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		this.selectedPlayerTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		this.mEngine.getTextureManager().loadTexture(this.playableCharacterTexture);
		this.mEngine.getTextureManager().loadTexture(this.lockedCharacterTexture);
		this.mEngine.getTextureManager().loadTexture(this.selectedPlayerTexture);
	}
	
	private void loadWaterEffectsTexture(){
		this.waterEffectsTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		this.mEngine.getTextureManager().loadTexture(this.waterEffectsTexture);
	}
	
	private void loadMoneyBagTextures(){
		this.moneyBagTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		Texture moneyBagTexture2 = new Texture(256, 256, TextureOptions.DEFAULT);
		
		this.moneyBagTextureRegions.add(TextureRegionFactory.createTiledFromAsset(this.moneyBagTexture, this, "gfx/moneybag.png", 0, 0, 2, 2));
		this.moneyBagTextureRegions.add(TextureRegionFactory.createTiledFromAsset(moneyBagTexture2, this, "gfx/moneybag.png", 0, 0, 2, 2));
		this.moneyBagTextureRegions.add(TextureRegionFactory.createTiledFromAsset(moneyBagTexture2, this, "gfx/moneybag.png", 0, 0, 2, 2));
		this.moneyBagTextureRegions.add(TextureRegionFactory.createTiledFromAsset(moneyBagTexture2, this, "gfx/moneybag.png", 0, 0, 2, 2));
		
		this.mEngine.getTextureManager().loadTexture(this.moneyBagTexture);
		this.mEngine.getTextureManager().loadTexture(moneyBagTexture2);
	}
	
	private void loadNPCTextures(){
		this.nonPlayableCharacterTexture = new Texture(512, 512, TextureOptions.DEFAULT);
		this.npcStateIndicatorTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		this.mEngine.getTextureManager().loadTexture(this.nonPlayableCharacterTexture);
		this.mEngine.getTextureManager().loadTexture(this.npcStateIndicatorTexture);
	}
	
	private void loadHudStatisticTextures() {
	}
	
	private void addDestinationRectangleToScene(Scene scene) {
		final Rectangle currentTileRectangle = new Rectangle(0, 0, this.levelMap.getTileWidth(), this.levelMap.getTileHeight());
		currentTileRectangle.setColor(1, 0, 0, 0.25f);
		scene.getTopLayer().addEntity(currentTileRectangle);
		
		scene.registerUpdateHandler(new IUpdateHandler() {
			
			public void reset() { }

			
			public void onUpdate(final float pSecondsElapsed) {
				currentTileRectangle.setPosition(levelHandler.getCurrentPlayerX(), levelHandler.getCurrentPlayerY());
			}
		});
	}
	
	private void addMoneyBagsToScene(Scene scene, List<MoneyBagDTO> bagsWhichAlreadyCollected){
		try{
			List<MoneyBag> levelMoneyBags = this.levelHandler.loadMoneyBags(this.moneyBagTextureRegions, bagsWhichAlreadyCollected);
			for(MoneyBag moneyBag : levelMoneyBags){
				scene.getTopLayer().addEntity(moneyBag);
			}
		}
		catch (MapLoadException e) {
			Debug.e(e);
		} catch (LevelLayerException e) {
			Debug.e(e);
			e.printStackTrace();
		} catch (LevelLoadException e) {
			Debug.e(e);
			e.printStackTrace();
		}
	}

	private void addPlayersToScene(Scene scene){
		try{
			List<IPlayableCharacter> levelPlayers = new ArrayList<IPlayableCharacter>();
			int numPlayersInLevel = this.levelHandler.getNumberOfPlayableCharactersInLevel();
			for(int playerIndex = 0; playerIndex<numPlayersInLevel; playerIndex++){
				PlayableCharacterDTO playerDTO = this.levelHandler.getPlayableCharacterData(playerIndex);
				AnimatedSprite waterEffectsSprite = this.loadWaterEffectAnimatedSprite();
				AnimatedSprite lockedPlayerSprite = this.loadLockedPlayerAnimatedSprite();
				AnimatedSprite selectedPlayerSprite = this.loadSelectedPlayerAnimatedSprite();
				Sprite swimmingIndicatorSprite = new Sprite(playerDTO.getXPos(), playerDTO.getYPos(), CommonDataManager.TILE_SIZE, CommonDataManager.TILE_SIZE, this.swimmingPlayerIndicatorTextureRegion);
				TiledTextureRegion playerTexture = this.LoadPlayerSpriteTexture(playerDTO.getPlayerAbility());
				IPlayableCharacter player = levelHandler.loadPlayableCharacter(playerDTO, playerTexture, swimmingIndicatorSprite, waterEffectsSprite, lockedPlayerSprite, this.smallTextFont, selectedPlayerSprite); 
				scene.getTopLayer().addEntity(player);
				if(playerDTO.getPlayerAbility() == PlayerAbility.SWIM){
					scene.getTopLayer().addEntity(swimmingIndicatorSprite);
					scene.getTopLayer().addEntity(waterEffectsSprite);
				}
				
				scene.getTopLayer().addEntity(selectedPlayerSprite);			
				if(player.isLocked()){
					Text lockedPlayerNrCoinsText = player.getNrOfCoinsRequiredToUnlockPlayerText();
					scene.getTopLayer().addEntity(lockedPlayerSprite);
					scene.getTopLayer().addEntity(lockedPlayerNrCoinsText);
				}
			}
			
			this.levelHandler.highlightSelectedPlayer();
		}
		catch(MapLoadException e){
			Debug.e(e);
		}
		catch (LevelLayerException e) {
			Debug.e(e);
			e.printStackTrace();
		} catch (LevelLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private AnimatedSprite loadSelectedPlayerAnimatedSprite() {
		TiledTextureRegion selectedPlayerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.selectedPlayerTexture, this, "gfx/selectedplayer.png", 0, 0, 2, 2);
		AnimatedSprite selectedPlayerSprite = new AnimatedSprite(0, 0, CommonDataManager.TILE_SIZE, CommonDataManager.TILE_SIZE, selectedPlayerTextureRegion);
		return selectedPlayerSprite;
	}

	private AnimatedSprite loadLockedPlayerAnimatedSprite() {
		TiledTextureRegion lockedPlayerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.lockedCharacterTexture, this, "gfx/lockedplayer.png", 0, 0, 2, 2);
		AnimatedSprite lockedPlayerSprite = new AnimatedSprite(0, 0, CommonDataManager.TILE_SIZE, CommonDataManager.TILE_SIZE, lockedPlayerTextureRegion);
		return lockedPlayerSprite;
	}

	private AnimatedSprite loadWaterEffectAnimatedSprite() {
		TiledTextureRegion waterTextureRegion = TextureRegionFactory.createTiledFromAsset(this.waterEffectsTexture, this, "gfx/swiminwatereffect.png", 0, 0, 2, 2);
		AnimatedSprite waterEffectSprite = new AnimatedSprite(0, 0, CommonDataManager.TILE_SIZE, CommonDataManager.TILE_SIZE, waterTextureRegion);
		return waterEffectSprite;
	}
	
	private AnimatedSprite loadNpcStateIndicatorSprite(){
		TiledTextureRegion stateIndicatorTextureRegion = TextureRegionFactory.createTiledFromAsset(this.npcStateIndicatorTexture, this, "gfx/stateindicator.png", 0, 0, 2, 3);
		AnimatedSprite stateIndicatorSprite = new AnimatedSprite(0, 0, CommonDataManager.TILE_SIZE, CommonDataManager.TILE_SIZE, stateIndicatorTextureRegion);
		return stateIndicatorSprite;
	}

	private TiledTextureRegion LoadPlayerSpriteTexture(PlayerAbility playerAbility) {
		TiledTextureRegion playerTextureRegion;
		if(playerAbility == PlayerAbility.FLY){
			playerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.playableCharacterTexture, this, "gfx/standardplayer.png", 0, 0, 4, 5); ///This line won't work cuz it's using the same texture and coordinates as the standard player
		}
		else{
			playerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.playableCharacterTexture, this, "gfx/standardplayer.png", 0, 0, 4, 5);
		}
		
		return playerTextureRegion;
	}

	private void addNPCsToScene(Scene scene) {
		try{
			List<INonPlayableCharacter> levelPlayers = new ArrayList<INonPlayableCharacter>();
			int numPlayersInLevel = this.levelHandler.getNumberOfNonPlayableCharactersInLevel();
			for(int playerIndex = 0; playerIndex<numPlayersInLevel; playerIndex++){
				NpcDTO playerDTO = this.levelHandler.getNonPlayableCharacterData(playerIndex);
				AnimatedSprite waterEffectsSprite = this.loadWaterEffectAnimatedSprite();
				AnimatedSprite stateIndicatorSprite = this.loadNpcStateIndicatorSprite();
				TiledTextureRegion playerTexture = this.LoadNonPlayerSpriteTexture(playerDTO.getPlayerAbility());
				Sprite swimmingIndicatorSprite = new Sprite(playerDTO.getXPos(), playerDTO.getYPos(), CommonDataManager.TILE_SIZE, CommonDataManager.TILE_SIZE, this.swimmingPlayerIndicatorTextureRegion);
				INonPlayableCharacter player = levelHandler.loadNonPlayableCharacter(playerDTO, playerTexture, swimmingIndicatorSprite, waterEffectsSprite, stateIndicatorSprite); 
				scene.getTopLayer().addEntity(player);
				if(playerDTO.getPlayerAbility() == PlayerAbility.SWIM){
					scene.getTopLayer().addEntity(swimmingIndicatorSprite);
					scene.getTopLayer().addEntity(waterEffectsSprite);
				}
				scene.getTopLayer().addEntity(stateIndicatorSprite);
			}
		}
		catch(MapLoadException e){
			Debug.e(e);
		}
		catch (LevelLayerException e) {
			Debug.e(e);
			e.printStackTrace();
		}
	}

	private TiledTextureRegion LoadNonPlayerSpriteTexture(
			PlayerAbility playerAbility) {
		TiledTextureRegion playerTextureRegion;
		if(playerAbility == PlayerAbility.FLY){
			playerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.nonPlayableCharacterTexture, this, "gfx/standardenemy.png", 0, 0, 4, 5); //This line will not work cuz it's using the same texture and coordinates as the standard npc.
		}
		else{
			playerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.nonPlayableCharacterTexture, this, "gfx/standardenemy.png", 0, 0, 4, 5);
		}
		
		return playerTextureRegion;
	}

	private void loadTMXMap(){
		try {
			final TMXLoader tmxLoader = new TMXLoader(this, this.mEngine.getTextureManager(), TextureOptions.BILINEAR, new ITMXTilePropertiesListener() {
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {				

				}
			});
			this.levelMap = tmxLoader.loadFromAsset(this, levelHandler.getCurrentLevel());
			this.levelHandler.setTMXLevelMap(levelMap);
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}
	}

	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		float mTouchX;
		float mTouchY;
		if(pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN)
        {	
			// Note: When using a regular Camera (not a BoundCamera), retrieve the Touch Event coordinates using
			// pSceneTouchEvent.getX() or pSceneEvent.getY(). Don't use pScenEvent.getMotionEvent().getX() unless you're
			// using a bound camera.
            mTouchX = pSceneTouchEvent.getX();
            mTouchY = pSceneTouchEvent.getY();
            int touchCol = (int) mTouchX / CommonDataManager.TILE_SIZE;
            int touchRow = (int) mTouchY / CommonDataManager.TILE_SIZE;
            int spriteX = touchCol * CommonDataManager.TILE_SIZE;
            int spriteY = touchRow * CommonDataManager.TILE_SIZE;
            //this.userInputFeedbackSprite.setPosition(spriteX, spriteY);
            
            try{
            	this.levelHandler.handleTouchEvent(mTouchX, mTouchY);
            	//this.userInputFeedbackSprite.animate(new long[] { 100, 100, 100, 100, 100}, 5, 9, false);
            }
            catch(PlayerMovementException e){
            	//this.userInputFeedbackSprite.animate(new long[] { 200, 200, 200, 200, 100}, 10, 14, false);
            	Debug.e(e);
            	e.printStackTrace();
            }
            catch(LevelLayerException e){
            	Debug.e(e);
            	e.printStackTrace();
            }
            catch(GameplayException e){
            	Debug.e(e);
            	e.printStackTrace();
            }
        }
        
        return true;
	}
	
	/**
	 * Helper class for the activity so that the activity is easier to test.
	 * @author chrisbraunschweiler
	 *
	 */
	public class MyGameActivityHelper{
		
		/**
		 * Checks whether game progress exists.
		 * @return True if game progress exists, false otherwise.
		 */
		public boolean gameProgressExists() {
	        if(getIntent().getExtras()!=null){
	        	return true;
	        }
	        
	        return false;
		}
	}

	public TMXTiledMap getLevelMap() {
		return this.levelMap;
	}

	/**
	 * Leaves the current level and returns to the MenuSelectionScreenActivity. The number of coins collected in this level
	 * is then passed to the LevelSelectionMenuActivity.
	 */
	public void onLeaveLevel(int numberOfCoinsCollected, List<MoneyBagDTO> collectedMoneyBags) {
		this.saveLevelProgress(collectedMoneyBags, numberOfCoinsCollected);
		Intent dataIntent = new Intent(MyGameActivity.this, LevelSelectionGameActivity.class);
		dataIntent.putExtra(CommonDataManager.NR_OF_COINS_COLLECTED_IN_LEVEL_KEY, "" + numberOfCoinsCollected);
		this.setResult(RESULT_OK, dataIntent);
		this.spawnExitLevelMenu();
		//this.finish();
	}

	private void saveLevelProgress(List<MoneyBagDTO> collectedMoneyBags, int numberOfCoinsCollected) {
		if(this.levelProgressKey != null && collectedMoneyBags != null){
			SharedPreferences levelProgress = getSharedPreferences(this.levelProgressKey, 0);
		    SharedPreferences.Editor editor = levelProgress.edit();
		    editor.putString(NR_OF_MONEYBAGS_ALREADY_COLLECTED, "" + collectedMoneyBags.size());
		    for(int i = 0; i < collectedMoneyBags.size(); i++){
				String bagDataKey = "bag" + i;
				editor.putString(bagDataKey, collectedMoneyBags.get(i).getXPos() + "," + collectedMoneyBags.get(i).getYPos() + "," + collectedMoneyBags.get(i).getNumberOfCoins());
			}
		    
		    if(numberOfCoinsCollected + this.nrOfCoinsAlreadyCollected >= this.totalNrOfCoinsInLevel){
		    	editor.putString(CommonDataManager.LEVEL_COMPLETED_KEY, CommonDataManager.LEVEL_COMPLETED_TRUE);
		    }
		    else{
		    	editor.putString(CommonDataManager.LEVEL_COMPLETED_KEY, CommonDataManager.LEVEL_COMPLETED_FALSE);
		    }
		    
		    editor.putString(CommonDataManager.LEVEL_ALREADY_VISITED_KEY, CommonDataManager.LEVEL_ALREADY_VISITED_TRUE);
		    
		    editor.commit();
		}
	}

	public void onMoneyBagDeposited(int totalNumberOfCoinsCollectedInLevel) {
		MenuHelper.updateCoinsCollectedHUDText(this.coinsCollectedText, totalNumberOfCoinsCollectedInLevel, this.totalNrOfCoinsInLevel);
	}

	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case CONTINUE_LEVEL_SELECTION:
				killPauseMenu();
				return true;
			case RESTART_LEVEL_SELECTION:
				this.restartActivity();
				return true;
			case QUIT_GAME_SELECTION:
				/* End Activity. */
				this.finish();
				return true;
			case START_GAME_AFTER_HOW_TO_SCREEN_SELECTION:
				//Toast.makeText(this, getIntent().getExtras().getString(CommonDataManager.TOAST_MESSAGE_KEY), Toast.LENGTH_LONG).show();
				this.showingHowToMenu = false;
				this.mainScene.clearChildScene();
				return true;
			default:
				return false;
		}
	}

	private void killPauseMenu() {
		this.showingPauseMenu = false;
		this.gamePausedText.setVisible(false);
		this.mainScene.clearChildScene();
	}
	
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if(!this.showingPauseMenu){
				if(this.mEngine.isRunning()) {
					this.spawnPauseMenu();
				} 
			}
			else{
				this.killPauseMenu();
			}
			
			return true;
		} else {			
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}
	
	private void spawnPauseMenu() {
		if(!this.showingHowToMenu && !this.showingExitLevelMenu && !this.showingGameOverMenu){
			this.showingPauseMenu = true;
			this.gamePausedText.setVisible(true);
			if(this.mainScene.hasChildScene()) {
				/* Remove the menu and reset it. */
				this.pauseMenuScene.back();
			} else {
				/* Attach the menu. */
				this.mainScene.setChildScene(this.pauseMenuScene, false, true, true);
			} 
		}
	}
	
	private void spawnHowToMenu(){
		this.showingHowToMenu = true;
		if(this.mainScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			this.howToMenuScene.back();
		} else {
			/* Attach the menu. */
			this.mainScene.setChildScene(this.howToMenuScene, false, true, true);
		} 
	}

	// Spawns game menu
	private void spawnGameOverMenu() {
		this.showingGameOverMenu = true;
		this.gameOverText.setVisible(true);
		if(this.mainScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			this.gameOverMenuScene.back();
		} else {
			/* Attach the menu. */
			this.mainScene.setChildScene(this.gameOverMenuScene, false, true, true);
		}
	}
	
	private void spawnExitLevelMenu(){
		this.showingExitLevelMenu = true;
		this.exitLevelText.setVisible(true);
		if(this.mainScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			this.exitLevelMenuScene.back();
		} else {
			/* Attach the menu. */
			this.mainScene.setChildScene(this.exitLevelMenuScene, false, true, true);
		}
	}
	
	private boolean levelAlreadyVisited() {
		String levelDataKey = this.levelProgressKey;
		SharedPreferences prefs = getSharedPreferences(levelDataKey,0);
		String levelVisitedValue = prefs.getString(CommonDataManager.LEVEL_ALREADY_VISITED_KEY, CommonDataManager.NO_KEY);
		return levelVisitedValue.equals(CommonDataManager.LEVEL_ALREADY_VISITED_TRUE);
	}

	public void onAllPlayersDied() {
		this.spawnGameOverMenu();
	}
}
