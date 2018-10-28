package chris.braunschweiler.touchngo.filehandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Saves and retrieves the player's game progress. It simply writes the name of the current
 * level into a file. When the game progress needs to be retrieved, the name of the current
 * level is retrieved from the file.
 * @author chrisbraunschweiler
 *
 */
public class GameProgressHandler {
	
	private String currentLine;
	private BufferedReader br;
	private BufferedWriter bw;
	
	private static final String PROGRESS_FILE_NAME = "/sdcard/touchngoGameProgress.txt";
	
	public GameProgressHandler(){
		try {
			File progressFile = new File(PROGRESS_FILE_NAME);
			currentLine = "";
			br = new BufferedReader(new FileReader(PROGRESS_FILE_NAME));
			bw = new BufferedWriter(new FileWriter(PROGRESS_FILE_NAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeGameProgress(String levelId){
		try {
			if(bw!=null){
				bw.write(levelId+"\n");
				bw.flush();
				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String readGameProgress() throws IOException{
		if(br!=null){
			String gameProgress = br.readLine();
			br.close();
			return gameProgress;
		}
		return null;
	}
}
