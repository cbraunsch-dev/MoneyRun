package chris.braunschweiler.touchngo.gameentities;

//TODO: Make it so users can't select new target for ball while it's still moving towards its current target.
public class Ball extends GameEntity{
	
	//Constants that define how big the click area is. The click area is the spot the ball has to
	//reach before it comes to a stop (unless it hits an obstacle on the way).
	private static final int CLICK_AREA_WIDTH = 50;
	private static final int CLICK_AREA_HEIGHT = 50;
	
	private static final int BALL_SPEED = 5;	//Maximum (and default) speed of the ball.
	
	private double currentBallSpeed;	//Current speed of the ball. Used to Calculate the xVeloc and yVeloc.
	private double previousXVeloc;	//The previous x veloc of the ball
	private double previousYVeloc;	//The previous y veloc of the ball
	private double targetXCoord;	//Where ball has to go (where user touched on screen)
	private double targetYCoord;
	private boolean justCollided;	//Used to help with collision detection with walls
	private boolean finished;	//True once the ball reaches a goal. Once the ball has reached a goal, he can no longer be moved.
	
	
	public Ball(){
		currentBallSpeed = 0;
		previousXVeloc = 0;
		previousYVeloc = 0;
		justCollided = false;
		finished = false;
		setWidth(60);
		setHeight(60);
	}
	
	public void setXVeloc(double xVeloc){
		previousXVeloc = this.getxVeloc();
		super.setxVeloc(xVeloc);
	}
	
	public void setYVeloc(double yVeloc){
		previousYVeloc = this.getyVeloc();
		super.setyVeloc(yVeloc);
	}

	public void setTargetXCoord(double targetXCoord) {
		this.targetXCoord = targetXCoord;
	}

	public double getTargetXCoord() {
		return targetXCoord;
	}

	public void setTargetYCoord(double targetYCoord) {
		this.targetYCoord = targetYCoord;
	}

	public double getTargetYCoord() {
		return targetYCoord;
	}

	public void setJustCollided(boolean justCollided) {
		this.justCollided = justCollided;
	}

	public boolean hasJustCollided() {
		return justCollided;
	}

	@Override
	/**
	 * Updates the ball's position, and velocity.
	 */
	public void updateEntity() {
		if(justCollided){
			//Reduce currentBallSpeed so that ball slows down gradually
			if(currentBallSpeed==0)	//ball has stopped
			{
				justCollided = false;
			}
			else{
				if(currentBallSpeed>0)
					currentBallSpeed-=0.5;
				else if(currentBallSpeed<0)
					currentBallSpeed+=0.5;
			}
			setVelocityAccordingToTarget();
		}
		else if(getxCoord()>=getTargetXCoord()-(CLICK_AREA_WIDTH/2)&&
				getxCoord()<=getTargetXCoord()+(CLICK_AREA_WIDTH/2)&&
				getyCoord()>=getTargetYCoord()-(CLICK_AREA_HEIGHT/2)&&
				getyCoord()<=getTargetYCoord()+(CLICK_AREA_HEIGHT/2)){
			setxVeloc(0);
			setyVeloc(0);
		}
		setxCoord(getxCoord()+getxVeloc());
		setyCoord(getyCoord()+getyVeloc());
	}
	
	/**
	 * Sets the ball's target position to the values passed in the parameters.
	 * The ball will then move towards this target.
	 * @param The target X coordinate of the ball.
	 * @param The target Y coordinate of the ball.
	 */
	public void setNewTargetPosition(int targetXCoord, int targetYCoord){
		if(!finished && getxVeloc()==0 && getyVeloc()==0 && !justCollided){	//If ball isn't already moving or just collided
			currentBallSpeed = BALL_SPEED;
			this.targetXCoord = targetXCoord;
			this.targetYCoord = targetYCoord;
			
			setVelocityAccordingToTarget();
		}
	}
	
	/**
	 * Informs the ball that it has collided with something.
	 */
	public void collide(GameEntity gameEntity){
		//If the ball is active but not moving at the time of collision, it's most likely stuck somewhere.
		if(this.isActive()&&this.getxVeloc()==0&&this.getyVeloc()==0){
			//Move the ball towards where it initially came from by using its previous x and y velocity
			this.setxCoord(this.getxCoord()-previousXVeloc);
			this.setyCoord(this.getyCoord()-previousYVeloc);
		}
		else{	//If the ball is colliding normally (ie: not stuck)
			if(gameEntity instanceof HorizontalWall ||
					gameEntity instanceof VerticalWall){
				if(gameEntity.isActive()){
					setJustCollided(true);
					currentBallSpeed *=-1;
				}
			}
			if(gameEntity instanceof Ball){
				setJustCollided(true);
				currentBallSpeed *=-1;
			}
			if(gameEntity instanceof Goal){
				if(gameEntity.isActive()){
					currentBallSpeed = 0;
					gameEntity.setActive(false);
					finished = true;
				}
			}
			if(gameEntity instanceof Switch){
				if(gameEntity.isActive()){
					//Activate/deactivate switch's affected objects
					currentBallSpeed = 0;
					Switch mySwitch = (Switch)gameEntity;
					for(GameEntity affectedObject:mySwitch.getAffectedObjects()){
						affectedObject.setActive(!affectedObject.isActive());
					}
					gameEntity.setActive(false);
				}
			}
		}
	}
	
	/**
	 * Sets the velocity of the ball according to where it needs to go (to its target position).
	 */
	private void setVelocityAccordingToTarget(){
		double deltaX = getTargetXCoord()-getxCoord();
		double deltaY = getTargetYCoord()-getyCoord();
		double angle = Math.toDegrees(Math.atan(deltaY/deltaX));
		if(angle<360){
			angle+=360;
		}
		if(deltaX<0){
			angle-=180;
		}
		setxVeloc(Math.cos(Math.toRadians(angle))*currentBallSpeed);
		setyVeloc(Math.sin(Math.toRadians(angle))*currentBallSpeed);
	}
}
