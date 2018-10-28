package chris.braunschweiler.touchngo.level;

import java.util.ArrayList;

import chris.braunschweiler.touchngo.gameentities.Ball;
import chris.braunschweiler.touchngo.gameentities.GameEntity;
import chris.braunschweiler.touchngo.gameentities.Goal;
import chris.braunschweiler.touchngo.gameentities.HorizontalWall;
import chris.braunschweiler.touchngo.gameentities.Switch;
import chris.braunschweiler.touchngo.gameentities.VerticalWall;

/**
 * Represents a game level.
 * A game level contains a list of game entities. The game level updates the individual game entities
 * and also handles the collision detection. Input events are also delegated to the level entity so
 * that it can decide whether to move the currently selected ball or whether to switch to the newly
 * touched ball.
 * @author chrisbraunschweiler
 *
 */
public class LevelEntity{
	
	private String levelId;
	private ArrayList<HorizontalWall> horizontalWalls;
	private ArrayList<VerticalWall> verticalWalls;
	private ArrayList<Goal> goals;
	private ArrayList<Ball> balls;
	private ArrayList<Switch> switches;
	
	private boolean levelFinished;
	
	public LevelEntity(){
		levelId = "";
		horizontalWalls = new ArrayList<HorizontalWall>();
		verticalWalls = new ArrayList<VerticalWall>();
		goals = new ArrayList<Goal>();
		balls = new ArrayList<Ball>();
		switches = new ArrayList<Switch>();
		levelFinished = false;
	}
	
	public void updateLevel(){
		//Update positions of game entities according to their velocities
		for(Ball ball:balls){
			ball.updateEntity();
		}
		
		handleCollisions();
		
		//Check if level is finished
		//If all goals are inactive, level is finished
		boolean allGoalsInactive = true;
		for(Goal goal:goals){
			if(goal.isActive())
				allGoalsInactive = false;
		}
		if(goals.size()>0 && allGoalsInactive)
			levelFinished = true;
	}
	
	/**
	 * Handle touch event. Set the active ball(s)' target coordinates to the ones passed in the parameter.
	 * @param xCoord The new target x coordinate of the active ball(s).
	 * @param yCoord The new target y coordinate of the active ball(s).
	 */
	public void handleTouchEvent(int xCoord, int yCoord){
		boolean clickedBall = false;
		for(Ball ball:balls){
			if(clickedBall(ball,xCoord,yCoord)){
				clickedBall = true;
				//Toggle clicked ball's active flag
				ball.setActive(!ball.isActive());
			}
		}
		if(!clickedBall){
			for(Ball ball:balls){
				if(ball.isActive()){
					ball.setNewTargetPosition(xCoord, yCoord);
				}
			}
		}
	}
	
	private void handleCollisions(){
		//Check if balls are colliding with anything
		for(Ball ball:balls){
			//Check if balls are colliding with horizontal walls
			for(HorizontalWall wall:horizontalWalls){
				if(!ball.hasJustCollided()&&areColliding(ball,wall)){
					ball.collide(wall);
				}
			}
			for(VerticalWall wall:verticalWalls){
				if(!ball.hasJustCollided()&&areColliding(ball,wall)){
					ball.collide(wall);
				}
			}
			//Check if balls have collided with goals
			for(Goal goal:goals){
				if(!ball.hasJustCollided()&&areColliding(ball,goal)){
					ball.collide(goal);
				}
			}
			//Check if balls have collided with switches
			for(Switch mySwitch:switches){
				if(!ball.hasJustCollided()&&areColliding(ball,mySwitch)){
					ball.collide(mySwitch);
				}
			}
			
			//Check if balls are colliding with other balls
			ArrayList<Ball> otherBalls = new ArrayList<Ball>();
			for(Ball theBall: balls){
				if(!theBall.equals(ball)){
					otherBalls.add(theBall);
				}
			}
			for(Ball otherBall:otherBalls){
				if(!ball.hasJustCollided()&&areColliding(ball,otherBall)){
					ball.collide(otherBall);
				}
			}
		}
	}
	
	/**
	 * Returns true if the user clicked on one of the balls, false otherwise.
	 * @param xCoord The x coordinate of where the user clicked.
	 * @param yCoord The y coordinate of where the user clicked.
	 * @param The ball which is being checked for clickage.
	 * @return True if the user clicked on the ball, false otherwise.
	 */
	private boolean clickedBall(GameEntity entity, int xCoord, int yCoord){
		//Since ball is drawn a little off its actual position, the collision with the ball
		//has to be done a bit off of its actual position. These calculations correspond with the
		//ones found in the GameView's drawGame() method where the ball is drawn.
		int ballX = (int)(entity.getxCoord()-entity.getWidth()/2);
		int ballY = (int)(entity.getyCoord()-entity.getHeight()/2);
		int ballX2 = (int)(ballX+entity.getWidth());
		int ballY2 = (int)(ballY+entity.getHeight());
		if(xCoord>=ballX&&xCoord<=ballX2&&
				yCoord>=ballY&&yCoord<=ballY2)
			return true;
		return false;
	}
	
	/**
	 * Checks whether the passed game entites are colliding with each other.
	 * @param The first entity.
	 * @param The second entity.
	 * @return True if the two entities are colliding, false otherwise.
	 */
	private boolean areColliding(GameEntity firstEntity, GameEntity secondEntity){
		int firstEntityLeft = firstEntity.getCollisionBox().getBounds().left;
		int firstEntityTop = firstEntity.getCollisionBox().getBounds().top;
		int firstEntityRight = firstEntity.getCollisionBox().getBounds().right;
		int firstEntityBottom = firstEntity.getCollisionBox().getBounds().bottom;
		int secondEntityLeft = secondEntity.getCollisionBox().getBounds().left;
		int secondEntityTop = secondEntity.getCollisionBox().getBounds().top;
		int secondEntityRight = secondEntity.getCollisionBox().getBounds().right;
		int secondEntityBottom = secondEntity.getCollisionBox().getBounds().bottom;
		if(firstEntity.getCollisionBox().getBounds().intersects(secondEntityLeft, secondEntityTop, 
				secondEntityRight, secondEntityBottom)){
			return true;
		}
		if(secondEntity.getCollisionBox().getBounds().intersects(firstEntityLeft, firstEntityTop, 
				firstEntityRight, firstEntityBottom)){
			return true;
		}
		return false;
	}

	public boolean isLevelFinished() {
		return levelFinished;
	}

	public void setHorizontalWalls(ArrayList<HorizontalWall> horizontalWalls) {
		this.horizontalWalls = horizontalWalls;
	}

	public ArrayList<HorizontalWall> getHorizontalWalls() {
		return horizontalWalls;
	}

	public void setVerticalWalls(ArrayList<VerticalWall> verticalWalls) {
		this.verticalWalls = verticalWalls;
	}

	public ArrayList<VerticalWall> getVerticalWalls() {
		return verticalWalls;
	}

	public void setGoals(ArrayList<Goal> goals) {
		this.goals = goals;
	}

	public ArrayList<Goal> getGoals() {
		return goals;
	}

	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}

	public ArrayList<Ball> getBalls() {
		return balls;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setSwitches(ArrayList<Switch> switches) {
		this.switches = switches;
	}

	public ArrayList<Switch> getSwitches() {
		return switches;
	}
}
