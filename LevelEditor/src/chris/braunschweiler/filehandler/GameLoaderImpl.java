/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chris.braunschweiler.filehandler;

import chris.braunschweiler.touchngo.level.GameEntity;
import chris.braunschweiler.touchngo.level.LevelEntity;
import chris.braunschweiler.touchngo.level.SwitchEntity;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author chrisbraunschweiler
 */
public class GameLoaderImpl implements IGameLoader{
    private static final String FILE_NAME = "levels.txt";

    private static final String LEVEL_OPEN_TAG = "<level>";
    private static final String LEVEL_CLOSE_TAG = "</level>";

    private static final String HORIZONTAL_WALL_OPEN_TAG = "<horizontal_wall>";
    private static final String HORIZONTAL_WALL_CLOSE_TAG = "</horizontal_wall>";
    private static final String VERTICAL_WALL_OPEN_TAG = "<vertical_wall>";
    private static final String VERTICAL_WALL_CLOSE_TAG = "</vertical_wall>";
    private static final String BALL_OPEN_TAG = "<ball>";
    private static final String BALL_CLOSE_TAG = "</ball>";
    private static final String GOAL_OPEN_TAG = "<goal>";
    private static final String GOAL_CLOSE_TAG = "</goal>";
    private static final String SWITCH_OPEN_TAG = "<switch>";
    private static final String SWITCH_CLOSE_TAG = "</switch>";

    private static final String ID_OPEN_TAG = "<id>";
    private static final String ID_CLOSE_TAG = "</id>";
    private static final String ACTIVE_OPEN_TAG = "<active>";
    private static final String ACTIVE_CLOSE_TAG = "</active>";
    private static final String X_COORD_OPEN_TAG = "<xcoord>";
    private static final String X_COORD_CLOSE_TAG = "</xcoord>";
    private static final String Y_COORD_OPEN_TAG = "<ycoord>";
    private static final String Y_COORD_CLOSE_TAG = "</ycoord>";
    private static final String AFFECTED_OBJECTS_OPEN_TAG = "<affectedObjects>";
    private static final String AFFECTED_OBJECTS_CLOSE_TAG = "</affectedObjects>";

    private String filename;
    private BufferedReader br;
    private PrintWriter pw;

    public GameLoaderImpl(){
        filename = FILE_NAME;
        //br = new BufferedReader(new FileReader(filename));
    }

    public void saveGame(List<LevelEntity> gameLevels)throws Exception {
        pw = new PrintWriter(new FileOutputStream(filename));
        for(LevelEntity gameLevel:gameLevels){
            writeLevelHeader(gameLevel);
            for(GameEntity horizontalWall:gameLevel.getHorizontalWalls()){
                writeHorizontalWall(horizontalWall);
            }
            for(GameEntity verticalWall:gameLevel.getVerticalWalls()){
                writeVerticalWall(verticalWall);
            }
            for(GameEntity ball:gameLevel.getBalls()){
                writeBall(ball);
            }
            for(GameEntity goal:gameLevel.getGoals()){
                writeGoal(goal);
            }
            for(GameEntity theSwitch:gameLevel.getSwitches()){
                writeSwitch(theSwitch);
            }
            writeLevelEnd();
        }
        pw.flush();
        pw.close();
    }

    public void loadGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //Private helpers
    private void writeLevelHeader(LevelEntity level)throws Exception{
        if(pw!=null){
            pw.println(LEVEL_OPEN_TAG);
            pw.println(ID_OPEN_TAG+level.getLevelId()+ID_CLOSE_TAG);
        }
        else{
            throw new Exception("Unable to write to file.");
        }
    }

    private void writeHorizontalWall(GameEntity gameEntity)throws Exception{
        if(pw!=null){
            pw.println(HORIZONTAL_WALL_OPEN_TAG);
            pw.println(ID_OPEN_TAG+gameEntity.getId()+ID_CLOSE_TAG);
            pw.println(ACTIVE_OPEN_TAG+gameEntity.isActive()+ACTIVE_CLOSE_TAG);
            pw.println(X_COORD_OPEN_TAG+(int)gameEntity.getxCoord()+X_COORD_CLOSE_TAG);
            pw.println(Y_COORD_OPEN_TAG+(int)gameEntity.getyCoord()+Y_COORD_CLOSE_TAG);
            pw.println(HORIZONTAL_WALL_CLOSE_TAG);
        }
        else{
            throw new Exception("Unable to write to file.");
        }
    }

    private void writeVerticalWall(GameEntity gameEntity)throws Exception{
        if(pw!=null){
            pw.println(VERTICAL_WALL_OPEN_TAG);
            pw.println(ID_OPEN_TAG+gameEntity.getId()+ID_CLOSE_TAG);
            pw.println(ACTIVE_OPEN_TAG+gameEntity.isActive()+ACTIVE_CLOSE_TAG);
            pw.println(X_COORD_OPEN_TAG+(int)gameEntity.getxCoord()+X_COORD_CLOSE_TAG);
            pw.println(Y_COORD_OPEN_TAG+(int)gameEntity.getyCoord()+Y_COORD_CLOSE_TAG);
            pw.println(VERTICAL_WALL_CLOSE_TAG);
        }
        else{
            throw new Exception("Unable to write to file.");
        }
    }

    private void writeBall(GameEntity gameEntity)throws Exception{
        if(pw!=null){
            pw.println(BALL_OPEN_TAG);
            pw.println(ID_OPEN_TAG+gameEntity.getId()+ID_CLOSE_TAG);
            pw.println(ACTIVE_OPEN_TAG+gameEntity.isActive()+ACTIVE_CLOSE_TAG);
            pw.println(X_COORD_OPEN_TAG+(int)gameEntity.getxCoord()+X_COORD_CLOSE_TAG);
            pw.println(Y_COORD_OPEN_TAG+(int)gameEntity.getyCoord()+Y_COORD_CLOSE_TAG);
            pw.println(BALL_CLOSE_TAG);
        }
        else{
            throw new Exception("Unable to write to file.");
        }
    }

    private void writeGoal(GameEntity gameEntity)throws Exception{
        if(pw!=null){
            pw.println(GOAL_OPEN_TAG);
            pw.println(ID_OPEN_TAG+gameEntity.getId()+ID_CLOSE_TAG);
            pw.println(ACTIVE_OPEN_TAG+gameEntity.isActive()+ACTIVE_CLOSE_TAG);
            pw.println(X_COORD_OPEN_TAG+(int)gameEntity.getxCoord()+X_COORD_CLOSE_TAG);
            pw.println(Y_COORD_OPEN_TAG+(int)gameEntity.getyCoord()+Y_COORD_CLOSE_TAG);
            pw.println(GOAL_CLOSE_TAG);
        }
        else{
            throw new Exception("Unable to write to file.");
        }
    }

    private void writeSwitch(GameEntity gameEntity)throws Exception{
        if(pw!=null){
            pw.println(SWITCH_OPEN_TAG);
            pw.println(ID_OPEN_TAG+gameEntity.getId()+ID_CLOSE_TAG);
            pw.println(ACTIVE_OPEN_TAG+gameEntity.isActive()+ACTIVE_CLOSE_TAG);
            pw.println(X_COORD_OPEN_TAG+(int)gameEntity.getxCoord()+X_COORD_CLOSE_TAG);
            pw.println(Y_COORD_OPEN_TAG+(int)gameEntity.getyCoord()+Y_COORD_CLOSE_TAG);
            pw.println(AFFECTED_OBJECTS_OPEN_TAG);
            for(GameEntity affectedEntity:((SwitchEntity)gameEntity).getAffectedEntities()){
                pw.println(affectedEntity.getId());
            }
            pw.println(AFFECTED_OBJECTS_CLOSE_TAG);
            pw.println(SWITCH_CLOSE_TAG);
        }
        else{
            throw new Exception("Unable to write to file.");
        }
    }

    private void writeLevelEnd()throws Exception{
        if(pw!=null){
           pw.println(LEVEL_CLOSE_TAG);
        }
        else{
            throw new Exception("Unable to write to file.");
        }
    }
}
