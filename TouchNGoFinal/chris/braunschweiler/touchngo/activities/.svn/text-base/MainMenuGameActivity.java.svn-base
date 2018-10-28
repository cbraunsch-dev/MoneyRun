package chris.braunschweiler.touchngo.activities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.MoveModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import chris.braunschweiler.touchngo.common.CommonDataManager;
import chris.braunschweiler.touchngo.gui.PositionalMenuAnimator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;

public class MainMenuGameActivity extends BaseGameActivity implements IOnMenuItemClickListener{
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	private static final int MENU_X = 160;
	private static final int MENU_Y = 350;
	private static final int MENU_ITEM_SPACING = 100;

	protected static final int MENU_NEW_GAME = 0;
	protected static final int MENU_RESUME_GAME = MENU_NEW_GAME + 1;
	protected static final int MENU_CREDITS = MENU_RESUME_GAME + 1;
	protected static final int CREDITS_IMG = MENU_CREDITS + 1;
	protected static final int CREDITS_OK = CREDITS_IMG + 1;

	// ===========================================================
	// Fields
	// ===========================================================

	protected Camera mCamera;

	protected Scene mMainScene;

	private Texture mTexture;

	protected MenuScene mMenuScene;
	protected MenuScene creditsMenuScene;

	private Texture backgroundTexture;
	private Texture mMenuTexture;
	private Texture creditsMenuTexture;
	private TextureRegion backgroundTextureRegion;
	protected TextureRegion newGameTextureRegion;
	protected TextureRegion resumeGameTextureRegion;
	protected TextureRegion creditsTextureRegion;
	protected TextureRegion creditsInfoTextureRegion;
	protected TextureRegion creditsOkTextureRegion;
	private SpriteMenuItem newGameMenuItem;
	private SpriteMenuItem resumeGameMenuItem;
	private SpriteMenuItem creditsMenuItem;

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
		this.backgroundTexture = new Texture(512, 1024, TextureOptions.DEFAULT);
		this.backgroundTextureRegion = TextureRegionFactory.createFromAsset(this.backgroundTexture, this, "gfx/startscreen.png", 0, 0);
		this.mMenuTexture = new Texture(256, 512, TextureOptions.DEFAULT);
		this.creditsMenuTexture = new Texture(512, 512, TextureOptions.DEFAULT);
		this.newGameTextureRegion = TextureRegionFactory.createFromAsset(this.mMenuTexture, this, "gfx/btnnew.png", 0, 0);
		this.resumeGameTextureRegion = TextureRegionFactory.createFromAsset(this.mMenuTexture, this, "gfx/btnresume.png", 0, 100);
		this.creditsTextureRegion = TextureRegionFactory.createFromAsset(this.mMenuTexture, this, "gfx/btncredits.png", 0, 200);
		this.creditsInfoTextureRegion = TextureRegionFactory.createFromAsset(this.creditsMenuTexture, this, "gfx/credits.png", 0, 0);
		this.creditsOkTextureRegion = TextureRegionFactory.createFromAsset(this.creditsMenuTexture, this, "gfx/btnok.png", 0, 300);
		this.mEngine.getTextureManager().loadTexture(this.mMenuTexture);
		this.mEngine.getTextureManager().loadTexture(this.backgroundTexture);
		this.mEngine.getTextureManager().loadTexture(this.creditsMenuTexture);
	}

	
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.createCreditsMenuScene();
		this.createMenuScene();

		/* Just a simple scene with an animated face flying around. */
		this.mMainScene = new Scene(1);
		//this.mMainScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		this.mMainScene.setBackground(new SpriteBackground(new Sprite(0, 0, 480, 800, this.backgroundTextureRegion)));
		
		return this.mMainScene;
	}

	
	private void createCreditsMenuScene() {
		this.creditsMenuScene = new MenuScene(this.mCamera);
		this.creditsMenuScene.addMenuItem(new SpriteMenuItem(CREDITS_IMG, this.creditsInfoTextureRegion));
		this.creditsMenuScene.addMenuItem(new SpriteMenuItem(CREDITS_OK, this.creditsOkTextureRegion));
		this.creditsMenuScene.setMenuAnimator(new SlideMenuAnimator());
		this.creditsMenuScene.buildAnimations();

		this.creditsMenuScene.setBackgroundEnabled(false);

		this.creditsMenuScene.setOnMenuItemClickListener(this);
	}


	public void onLoadComplete() {
		if(this.mMainScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			this.mMenuScene.back();
		} else {
			/* Attach the menu. */
			this.mMainScene.setChildScene(this.mMenuScene, false, true, true);
		}
		
		this.updateMenu();
	}
	
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case MENU_NEW_GAME:
				this.startGame(true);
				return true;
			case MENU_RESUME_GAME:
				this.startGame(false);
				return true;
			case MENU_CREDITS:
				pMenuScene.setChildSceneModal(this.creditsMenuScene);
				return true;
			case CREDITS_OK:
				this.creditsMenuScene.back();
				return true;
			default:
				return false;
		}
	}
	
    public void onResume(){
    	super.onResume();
    	
    	this.updateMenu();
    }

	private void updateMenu() {
		if(this.resumeGameMenuItem != null){
			if(this.gameProgressExists()){
				this.resumeGameMenuItem.setVisible(true);
			}
			else{
				this.resumeGameMenuItem.setVisible(false);
			}
		}
	}


	// ===========================================================
	// Methods
	// ===========================================================

	protected void createMenuScene() {
		this.mMenuScene = new MenuScene(this.mCamera);

		this.newGameMenuItem = new SpriteMenuItem(MENU_NEW_GAME, this.newGameTextureRegion);
		newGameMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mMenuScene.addMenuItem(newGameMenuItem);
		
		this.resumeGameMenuItem = new SpriteMenuItem(MENU_RESUME_GAME, this.resumeGameTextureRegion);
		resumeGameMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mMenuScene.addMenuItem(resumeGameMenuItem);
		
		this.creditsMenuItem = new SpriteMenuItem(MENU_CREDITS, this.creditsTextureRegion);
		this.creditsMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mMenuScene.addMenuItem(creditsMenuItem);
		
		//this.mMenuScene.setMenuAnimator(new VerticalMenuAnimator(this.MENU_X, this.MENU_Y, this.MENU_ITEM_SPACING));
		this.mMenuScene.buildAnimations();

		this.mMenuScene.setBackgroundEnabled(false);

		this.mMenuScene.setOnMenuItemClickListener(this);
	}

	private boolean gameProgressExists() {
		SharedPreferences prefs = getSharedPreferences(CommonDataManager.OVERALL_GAME_PROGRESS_KEY,0);
		String gameProgress = prefs.getString(CommonDataManager.TOTAL_NUMBER_OF_COINS_COLLECTED_KEY, CommonDataManager.NO_KEY);
		if(!gameProgress.equals(CommonDataManager.NO_KEY)){
			return true;
		}
		
		return false;
	}
	
	private void startGame(boolean newGame){
		// Load the level selection activity
		Intent levelSelectionIntent = new Intent(MainMenuGameActivity.this, LevelSelectionGameActivity.class);
		if(newGame){
			this.startNewGame();
		}
		startActivity(levelSelectionIntent);
	}


	private void startNewGame() {
		SharedPreferences levelProgress = getSharedPreferences(CommonDataManager.GAME_META_DATA_KEY, 0);
	    SharedPreferences.Editor editor = levelProgress.edit();
	    editor.putString(CommonDataManager.START_NEW_GAME_KEY, CommonDataManager.START_NEW_GAME_TRUE);
	    editor.commit();
	}
}
