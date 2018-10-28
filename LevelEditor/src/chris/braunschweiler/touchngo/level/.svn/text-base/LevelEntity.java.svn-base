/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chris.braunschweiler.touchngo.level;

import java.util.ArrayList;

/**
 *
 * @author chrisbraunschweiler
 */
public class LevelEntity {
    
    private String levelId;
    private ArrayList<GameEntity> horizontalWalls;
    private ArrayList<GameEntity> verticalWalls;
    private ArrayList<GameEntity> goals;
    private ArrayList<GameEntity> switches;
    private ArrayList<GameEntity> balls;

    public LevelEntity(String levelId){
        this.levelId = levelId;
        horizontalWalls = new ArrayList<GameEntity>();
        verticalWalls = new ArrayList<GameEntity>();
        goals = new ArrayList<GameEntity>();
        switches = new ArrayList<GameEntity>();
        balls = new ArrayList<GameEntity>();
    }

    public LevelEntity(String levelId, ArrayList<GameEntity> horizontalWalls,
            ArrayList<GameEntity> verticalWalls, ArrayList<GameEntity> goals,
            ArrayList<GameEntity> switches, ArrayList<GameEntity> balls){
        this.levelId = levelId;
        this.horizontalWalls = horizontalWalls;
        this.verticalWalls = verticalWalls;
        this.goals = goals;
        this.switches = switches;
        this.balls = balls;
    }

    /**
     * @return the levelId
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the horizontalWalls
     */
    public ArrayList<GameEntity> getHorizontalWalls() {
        return horizontalWalls;
    }

    /**
     * @param horizontalWalls the horizontalWalls to set
     */
    public void setHorizontalWalls(ArrayList<GameEntity> horizontalWalls) {
        this.horizontalWalls = horizontalWalls;
    }

    /**
     * @return the verticalWalls
     */
    public ArrayList<GameEntity> getVerticalWalls() {
        return verticalWalls;
    }

    /**
     * @param verticalWalls the verticalWalls to set
     */
    public void setVerticalWalls(ArrayList<GameEntity> verticalWalls) {
        this.verticalWalls = verticalWalls;
    }

    /**
     * @return the goals
     */
    public ArrayList<GameEntity> getGoals() {
        return goals;
    }

    /**
     * @param goals the goals to set
     */
    public void setGoals(ArrayList<GameEntity> goals) {
        this.goals = goals;
    }

    /**
     * @return the switches
     */
    public ArrayList<GameEntity> getSwitches() {
        return switches;
    }

    /**
     * @param switches the switches to set
     */
    public void setSwitches(ArrayList<GameEntity> switches) {
        this.switches = switches;
    }

    /**
     * @return the balls
     */
    public ArrayList<GameEntity> getBalls() {
        return balls;
    }

    /**
     * @param balls the balls to set
     */
    public void setBalls(ArrayList<GameEntity> balls) {
        this.balls = balls;
    }

    public ArrayList<GameEntity> getAllEntities(){
        ArrayList<GameEntity> allEntities = new ArrayList<GameEntity>();
        for(GameEntity horizontalWall:this.horizontalWalls){
            allEntities.add(horizontalWall);
        }
        for(GameEntity verticalWall:this.verticalWalls){
            allEntities.add(verticalWall);
        }
        for(GameEntity ball:this.balls){
            allEntities.add(ball);
        }
        for(GameEntity aSwitch:this.switches){
            allEntities.add(aSwitch);
        }
        for(GameEntity goal:this.goals){
            allEntities.add(goal);
        }
        return allEntities;
    }
}
