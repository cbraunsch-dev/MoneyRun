package chris.braunschweiler.touchngo.filehandlers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import android.content.res.AssetManager;
import chris.braunschweiler.touchngo.gameentities.Ball;
import chris.braunschweiler.touchngo.gameentities.Goal;
import chris.braunschweiler.touchngo.gameentities.HorizontalWall;
import chris.braunschweiler.touchngo.gameentities.Switch;
import chris.braunschweiler.touchngo.gameentities.VerticalWall;
import chris.braunschweiler.touchngo.level.LevelEntity;

/**
 * Loads the levels from a file.
 * @author chrisbraunschweiler
 *
 */
public class LevelLoader {
	
	//Constants
	private static final String OPEN_TAG_LEVEL = "<level>";
	private static final String CLOSE_TAG_LEVEL ="</level>";
	private static final String OPEN_TAG_WALL_HORIZONTAL = "<horizontal_wall>";
	private static final String CLOSE_TAG_WALL_HORIZONTAL = "</horizontal_wall>";
	private static final String OPEN_TAG_WALL_VERTICAL = "<vertical_wall>";
	private static final String CLOSE_TAG_WALL_VERTICAL = "</vertical_wall>";
	private static final String OPEN_TAG_GOAL = "<goal>";
	private static final String CLOSE_TAG_GOAL = "</goal>";
	private static final String OPEN_TAG_BALL = "<ball>";
	private static final String CLOSE_TAG_BALL = "</ball>";
	private static final String OPEN_TAG_SWITCH = "<switch>";
	private static final String CLOSE_TAG_SWITCH = "</switch>";
	
	private static final String OPEN_TAG_ID = "<id>";
	private static final String CLOSE_TAG_ID = "</id>";
	private static final String OPEN_TAG_ACTIVE = "<active>";
	private static final String CLOSE_TAG_ACTIVE = "</active>";
	private static final String OPEN_TAG_X_COORD = "<xcoord>";
	private static final String CLOSE_TAG_X_COORD = "</xcoord>";
	private static final String OPEN_TAG_Y_COORD = "<ycoord>";
	private static final String CLOSE_TAG_Y_COORD = "</ycoord>";
	private static final String OPEN_TAG_AFFECTED_OBJECTS = "<affectedObjects>";
	private static final String CLOSE_TAG_AFFECTED_OBJECTS = "</affectedObjects>";
	
	private HardwareCompatibilityHandler hardwareHandler;
	private boolean gameFinished;
	private String currentLine;
	private AssetManager assetManager;
	private BufferedReader br;
	private boolean loadedGameProgress;
	//Temporary cache for all of the affected objects of the level's switches.
	//This cache keeps track of which switch has which affected objects. Each switch
	//is identified by its unique ID. To each switch's id corresponds a list of strings which
	//are the IDs of the switch's affected objects. After all the game entities have been loaded,
	//the entities with the given IDs are added to the respective switches.
	private HashMap<String,List<String>> affectedObjectsCache;
	
	public LevelLoader(AssetManager assetManager){
		try {
			gameFinished = false;
			currentLine = "";
			this.assetManager = assetManager;
			br = new BufferedReader(new InputStreamReader(new DataInputStream(assetManager.open("levels.txt"))));
			loadedGameProgress = false;
			affectedObjectsCache = new HashMap<String,List<String>>();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves the level loader to the specified level's
	 * position in the file.
	 * @param levelId
	 */
	public void moveAheadToLevel(String levelId){
		try {
				currentLine = br.readLine();
				while(!currentLine.equals(OPEN_TAG_ID+levelId+CLOSE_TAG_ID)){
					currentLine = br.readLine();
				}
				loadedGameProgress = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the next available level.
	 */
	public LevelEntity loadLevel(){
		LevelEntity newLevel = new LevelEntity();
		try {
			if(!loadedGameProgress)
				currentLine = br.readLine();
			boolean readLevelId = true;
			while(currentLine!=null&&!currentLine.equals(CLOSE_TAG_LEVEL)){
				if(!loadedGameProgress)
					currentLine = br.readLine();
				if(readLevelId){
					readLevelId = false;
					String idValue = getValueInsideTags(currentLine, OPEN_TAG_ID, CLOSE_TAG_ID);
					newLevel.setLevelId(idValue);
				}
				else if(currentLine.equals(OPEN_TAG_WALL_HORIZONTAL)){
					loadHorizontalWallInfo(newLevel);
				}
				else if(currentLine.equals(OPEN_TAG_WALL_VERTICAL)){
					loadVerticalWallInfo(newLevel);
				}
				else if(currentLine.equals(OPEN_TAG_GOAL)){
					loadGoalInfo(newLevel);
				}
				else if(currentLine.equals(OPEN_TAG_BALL)){
					loadBallInfo(newLevel);
				}
				else if(currentLine.equals(OPEN_TAG_SWITCH)){
					loadSwitchInfo(newLevel);
				}
				loadedGameProgress = false;
			}
			this.loadAffectedObjectsIntoSwitches(newLevel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newLevel;
	}
	
	public void resetLoader(){
		try {
			br.close();
			br = new BufferedReader(new InputStreamReader(new DataInputStream(assetManager.open("levels.txt"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the horizontal wall info into the level.
	 * @param The level which is being constructed from the file data.
	 * @throws IOException 
	 */
	private void loadHorizontalWallInfo(LevelEntity newLevel) throws IOException, NumberFormatException{
		HorizontalWall wall = new HorizontalWall();
		currentLine = br.readLine();
		String wallId = getValueInsideTags(currentLine, OPEN_TAG_ID,CLOSE_TAG_ID);
		wall.setId(wallId);
		currentLine = br.readLine();
		if(currentLine.equals("<active>true</active>")){
			wall.setActive(true);
		}
		else{
			wall.setActive(false);
		}
		currentLine = br.readLine();
		String xCoordValue = getValueInsideTags(currentLine, OPEN_TAG_X_COORD,CLOSE_TAG_X_COORD);
		wall.setxCoord(Integer.parseInt(xCoordValue));
		currentLine = br.readLine();
		String yCoordValue = getValueInsideTags(currentLine, OPEN_TAG_Y_COORD,CLOSE_TAG_Y_COORD);
		wall.setyCoord(Integer.parseInt(yCoordValue));
		newLevel.getHorizontalWalls().add(wall);
		currentLine = br.readLine();	//read closing bracket
	}
	
	/**
	 * Loads the vertical wall info into the level.
	 * @param The level which is being constructed from the file data.
	 * @throws IOException
	 */
	private void loadVerticalWallInfo(LevelEntity newLevel) throws IOException{
		VerticalWall wall = new VerticalWall();
		currentLine = br.readLine();
		String wallId = getValueInsideTags(currentLine, OPEN_TAG_ID,CLOSE_TAG_ID);
		wall.setId(wallId);
		currentLine = br.readLine();
		if(currentLine.equals("<active>true</active>")){
			wall.setActive(true);
		}
		else{
			wall.setActive(false);
		}
		currentLine = br.readLine();
		String xCoordValue = getValueInsideTags(currentLine, OPEN_TAG_X_COORD,CLOSE_TAG_X_COORD);
		wall.setxCoord(Integer.parseInt(xCoordValue));
		currentLine = br.readLine();
		String yCoordValue = getValueInsideTags(currentLine, OPEN_TAG_Y_COORD,CLOSE_TAG_Y_COORD);
		wall.setyCoord(Integer.parseInt(yCoordValue));
		newLevel.getVerticalWalls().add(wall);
		currentLine = br.readLine();	//read closing bracket
	}
	
	/**
	 * Loads the goal data into the level.
	 * @param The level which is being constructed from the file data.
	 * @throws IOException
	 */
	private void loadGoalInfo(LevelEntity newLevel) throws IOException, NumberFormatException{
		Goal goal = new Goal();
		currentLine = br.readLine();
		String goalId = getValueInsideTags(currentLine, OPEN_TAG_ID,CLOSE_TAG_ID);
		goal.setId(goalId);
		currentLine = br.readLine();
		if(currentLine.equals("<active>true</active>")){
			goal.setActive(true);
		}
		else{
			goal.setActive(false);
		}
		currentLine = br.readLine();
		String xCoordValue = getValueInsideTags(currentLine, OPEN_TAG_X_COORD,CLOSE_TAG_X_COORD);
		goal.setxCoord(Integer.parseInt(xCoordValue));
		currentLine = br.readLine();
		String yCoordValue = getValueInsideTags(currentLine, OPEN_TAG_Y_COORD,CLOSE_TAG_Y_COORD);
		goal.setyCoord(Integer.parseInt(yCoordValue));
		newLevel.getGoals().add(goal);
		currentLine = br.readLine();	//read closing bracket
	}
	
	/**
	 * Loads the ball data into the level.
	 * @param The level which is being constructed from the file data.
	 * @throws IOException
	 */
	private void loadBallInfo(LevelEntity newLevel) throws IOException, NumberFormatException{
		Ball ball = new Ball();
		currentLine = br.readLine();
		String ballId = getValueInsideTags(currentLine, OPEN_TAG_ID,CLOSE_TAG_ID);
		ball.setId(ballId);
		currentLine = br.readLine();
		if(currentLine.equals("<active>true</active>")){
			ball.setActive(true);
		}
		else{
			ball.setActive(false);
		}
		currentLine = br.readLine();
		String xCoordValue = getValueInsideTags(currentLine, OPEN_TAG_X_COORD, CLOSE_TAG_X_COORD);
		ball.setxCoord(Integer.parseInt(xCoordValue));
		currentLine = br.readLine();
		String yCoordValue = getValueInsideTags(currentLine, OPEN_TAG_Y_COORD, CLOSE_TAG_Y_COORD);
		ball.setyCoord(Integer.parseInt(yCoordValue));
		newLevel.getBalls().add(ball);
		currentLine = br.readLine();	//read closing bracket
	}
	
	/**
	 * Loads the switch data into the level.
	 * @param The level being constructed.
	 * @throws IOException thrown if an IO error occurs.
	 * @throws NumberFormatException If file is corrupt (x and y coords aren't in the proper format).
	 */
	private void loadSwitchInfo(LevelEntity newLevel)throws IOException, NumberFormatException, Exception{
		Switch newSwitch = new Switch();
		currentLine = br.readLine();
		String switchId = getValueInsideTags(currentLine, OPEN_TAG_ID, CLOSE_TAG_ID);
		newSwitch.setId(switchId);
		currentLine = br.readLine();
		if(currentLine.equals("<active>true</active>")){
			newSwitch.setActive(true);
		}
		else{
			newSwitch.setActive(false);
		}
		currentLine = br.readLine();
		String xCoordValue = getValueInsideTags(currentLine, OPEN_TAG_X_COORD, CLOSE_TAG_X_COORD);
		newSwitch.setxCoord(Integer.parseInt(xCoordValue));
		currentLine = br.readLine();
		String yCoordValue = getValueInsideTags(currentLine, OPEN_TAG_Y_COORD, CLOSE_TAG_Y_COORD);
		newSwitch.setyCoord(Integer.parseInt(yCoordValue));
		//Read in affectedObjects data
		ArrayList<String> affectedObjectsValues = new ArrayList<String>();
		currentLine = br.readLine();
		if(!currentLine.equals(OPEN_TAG_AFFECTED_OBJECTS))
			throw new Exception("Level File Corrupt: Switch object contains incorrect affectedObjects data.");
		while(!(currentLine = br.readLine()).equals(CLOSE_TAG_AFFECTED_OBJECTS)){
			affectedObjectsValues.add(currentLine);
		}
		this.affectedObjectsCache.put(newSwitch.getId(), affectedObjectsValues);
		newLevel.getSwitches().add(newSwitch);
		currentLine = br.readLine();	//read closing bracket
	}
	
	/**
	 * Returns the content inside the tags of the given line.
	 * @param The line which contains the content inside of the tags
	 * @param The opening tag.
	 * @return The content inside the tags of the given line.
	 */
	private String getValueInsideTags(String line, String openTag, String closeTag){
		//StringTokenizer st = new StringTokenizer(line,openTag);
		//String tagContent = st.nextToken();
		String[] splitStrings = line.split(openTag);
		String tagContentWithCloseTag = splitStrings[1];
		String[] splitStrings2 = tagContentWithCloseTag.split(closeTag);
		String tagContent = splitStrings2[0];
		return tagContent;
	}
	
	private void loadAffectedObjectsIntoSwitches(LevelEntity newLevel){
		for(Switch switchEntity:newLevel.getSwitches()){
			List<String> affectedObjectsValues = this.affectedObjectsCache.get(switchEntity.getId());
			for(String affectedObjectValue:affectedObjectsValues){
				ArrayList<HorizontalWall> horizontalWalls = newLevel.getHorizontalWalls();
				for(HorizontalWall wall:horizontalWalls){
					if(wall.getId().equals(affectedObjectValue)){
						//Add game entity to switch's list of affected objects
						switchEntity.getAffectedObjects().add(wall);
					}
				}
				ArrayList<VerticalWall> verticalWalls = newLevel.getVerticalWalls();
				for(VerticalWall wall:verticalWalls){
					if(wall.getId().equals(affectedObjectValue)){
						//Add game entity to switch's list of affected objects
						switchEntity.getAffectedObjects().add(wall);
					}
				}
				ArrayList<Switch> switches = newLevel.getSwitches();
				for(Switch theSwitch:switches){
					if(theSwitch.getId().equals(affectedObjectValue)){
						//Add entity to list of affected objects
						switchEntity.getAffectedObjects().add(theSwitch);
					}
				}
			}
		}
	}

	public boolean isGameFinished() {
		return gameFinished;
	}
}
