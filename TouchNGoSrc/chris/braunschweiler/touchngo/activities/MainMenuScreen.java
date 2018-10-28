package chris.braunschweiler.touchngo.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import chris.braunschweiler.touchngo.filehandlers.GameProgressHandler;

/**
 * Represents the main screen that the user sees when the game starts up.
 * @author chrisbraunschweiler
 *
 */
public class MainMenuScreen extends Activity{
	
	private static final String GAME_PROGRESS_PREFS = "gameProgressPrefs";
	private static final String GAME_PROGRESS_KEY = "gameProgress";
	private static final String NO_GAME_PROGRESS_MADE = "#No Progress";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu); 
        
        Button startGameButton = (Button)findViewById(R.id.StartGame);
        startGameButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		Intent startGameIntent = new Intent(MainMenuScreen.this,GameActivity.class);
        		startActivity(startGameIntent);
        	}
        });
        
        Button resumeGameButton = (Button)findViewById(R.id.ResumeGame);
        resumeGameButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		//Retrieve game progress (levelId of level player is currently on) and
        		//pass it to the GameActivity.
        		SharedPreferences prefs = getSharedPreferences(GAME_PROGRESS_PREFS,0);
        		SharedPreferences.Editor editor = prefs.edit();
        		String gameProgress = prefs.getString(GAME_PROGRESS_KEY, NO_GAME_PROGRESS_MADE);
        		Intent startGameIntent = new Intent(MainMenuScreen.this, GameActivity.class);
				startGameIntent.putExtra(GAME_PROGRESS_KEY, gameProgress);
				startActivity(startGameIntent);
        	}
        });
    }
}
