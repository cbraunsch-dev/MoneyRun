package chris.braunschweiler.touchngo.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import chris.braunschweiler.touchngo.callbackinterfaces.GameEngineListener;
import chris.braunschweiler.touchngo.filehandlers.LevelLoader;
import chris.braunschweiler.touchngo.gameengine.GameView;
import chris.braunschweiler.touchngo.gameengine.GameView.GameThread;
import chris.braunschweiler.touchngo.gameentities.Ball;
import chris.braunschweiler.touchngo.gameentities.HorizontalWall;
import chris.braunschweiler.touchngo.level.LevelEntity;

public class GameActivity extends Activity implements GameEngineListener{
	
	private GameView gameView;
	private GameThread gameThread;
	private LevelLoader levelLoader;
	
	//Variables for the pause menu
	private static final int RESTART_LEVEL_SELECTION = 0;
	private static final int QUIT_GAME_SELECTION = 1;
	
	//Variables for retrieving and storing game progress
	private static final String GAME_PROGRESS_PREFS = "gameProgressPrefs";
	private static final String GAME_PROGRESS_KEY = "gameProgress";
	private static final String NO_GAME_PROGRESS_MADE = "#No Progress";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Obtain any parameters from other activity
        String gameProgress = null;
        if(this.getIntent().getExtras()!=null){
        	gameProgress = this.getIntent().getExtras().getString(GAME_PROGRESS_KEY);
        }
        AssetManager assetManager = getAssets();
        levelLoader = new LevelLoader(assetManager);
        if(gameProgress!=null&&!gameProgress.equals(NO_GAME_PROGRESS_MADE)){
        	//Adjust level loader to load the level corresponding to the user's game progress
        	levelLoader.moveAheadToLevel(gameProgress);
        }
        gameView = (GameView) findViewById(R.id.gameView);
        gameThread = gameView.getThread();
        gameThread.setGameEngineListener(this);
        gameView.setOnTouchListener(gameThread);
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	gameView.killThread();
    	gameView.setViewPaused(true);
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	if(gameView.isViewPaused()){
    		finish();
    	}
    }
    
    /**
     * Call-back for the Game Engine.
     * Loads the next level and returns it to the game engine.
     */
	public LevelEntity loadNextLevel() {
		//Load new level and save its id in the game progress file
		LevelEntity newLevel = levelLoader.loadLevel();
		if(newLevel!=null){
			SharedPreferences settings = getSharedPreferences(GAME_PROGRESS_PREFS, 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putString(GAME_PROGRESS_KEY, newLevel.getLevelId());
		    editor.commit();
		}
		return newLevel;
	}
	
	/**
	 * Loads the level specified. Useful for reloading a particular level.
	 * @return
	 */
	public void moveToLevel(String levelId){
		levelLoader.resetLoader();
		levelLoader.moveAheadToLevel(levelId);
	}
	
	/* Creates the menu items */
	public boolean onCreateOptionsMenu(Menu menu) {
	    menu.add(0,RESTART_LEVEL_SELECTION,0,"Restart level");
	    menu.add(0,QUIT_GAME_SELECTION,0,"Quit");
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case RESTART_LEVEL_SELECTION:
	        gameThread.restartLevel();
	        return true;
	    case QUIT_GAME_SELECTION:
	        finish();
	        return true;
	    }
	    return false;
	}
}