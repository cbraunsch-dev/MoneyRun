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
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Represents the start screen that appears when the game is first launched.
 * @author chrisbraunschweiler
 */
public class StartScreenGameActivity extends BaseGameActivity implements IOnMenuItemClickListener {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;

	protected static final int MENU_START_GAME = 0;

	// ===========================================================
	// Fields
	// ===========================================================

	protected Camera mCamera;

	protected Scene mMainScene;

	private Texture mTexture;

	protected MenuScene mMenuScene;

	private Texture backgroundTexture;
	private Texture mMenuTexture;
	private TextureRegion backgroundTextureRegion;
	protected TextureRegion startGameTextureRegion;

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
		this.mTexture = new Texture(64, 64, TextureOptions.DEFAULT);
		this.mEngine.getTextureManager().loadTexture(this.mTexture);

		this.backgroundTexture = new Texture(512, 1024, TextureOptions.DEFAULT);
		this.mMenuTexture = new Texture(256, 128, TextureOptions.DEFAULT);
		this.backgroundTextureRegion = TextureRegionFactory.createFromAsset(this.backgroundTexture, this, "gfx/startscreen.png", 0, 0);
		this.startGameTextureRegion = TextureRegionFactory.createFromAsset(this.mMenuTexture, this, "gfx/btnstart.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mMenuTexture);
		this.mEngine.getTextureManager().loadTexture(this.backgroundTexture);
	}

	
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.createMenuScene();

		/* Just a simple scene with an animated face flying around. */
		this.mMainScene = new Scene(1);
		//this.mMainScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		this.mMainScene.setBackground(new SpriteBackground(new Sprite(0, 0, 480, 800, this.backgroundTextureRegion)));
		
		return this.mMainScene;
	}

	
	public void onLoadComplete() {
		if(this.mMainScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			this.mMenuScene.back();
		} else {
			/* Attach the menu. */
			this.mMainScene.setChildScene(this.mMenuScene, false, true, true);
		}
	}
	
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case MENU_START_GAME:
				Intent mainMenuIntent = new Intent(StartScreenGameActivity.this, MainMenuGameActivity.class);
        		startActivity(mainMenuIntent);
				return true;
			default:
				return false;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	protected void createMenuScene() {
		this.mMenuScene = new MenuScene(this.mCamera);

		final SpriteMenuItem resetMenuItem = new SpriteMenuItem(MENU_START_GAME, this.startGameTextureRegion);
		resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mMenuScene.addMenuItem(resetMenuItem);
		
		this.mMenuScene.buildAnimations();

		this.mMenuScene.setBackgroundEnabled(false);
		
		this.mMenuScene.setOnMenuItemClickListener(this);
	}
}