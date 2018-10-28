package chris.braunschweiler.touchngo.gameentities;

import android.graphics.drawable.Drawable;

/**
 * Represents an abstract game entity.
 * A game entity is any object that is part of a game level. Game entities have positions, dimensions,
 * an image etc.
 * @author chrisbraunschweiler
 *
 */
public abstract class GameEntity{
	
	private String id;
	private boolean active;
	private double xCoord;
	private double yCoord;
	private double xVeloc;
	private double yVeloc;
	private double width;
	private double height;
	private Drawable activeImage;
	private Drawable inactiveImage;
	private Drawable collisionBox;	//used for collision detection only
	
	public GameEntity(){
		id = "";
		active = true;
		xCoord = 0;
		yCoord = 0;
		xVeloc = 0;
		yVeloc = 0;
		width = 10;
		height = 10;
		activeImage = null;
		inactiveImage = null;
		collisionBox = null;
	}
	
	public abstract void updateEntity();

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}

	public double getxCoord() {
		return xCoord;
	}

	public void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}

	public double getyCoord() {
		return yCoord;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getWidth() {
		return width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getHeight() {
		return height;
	}

	public void setxVeloc(double xVeloc) {
		this.xVeloc = xVeloc;
	}

	public double getxVeloc() {
		return xVeloc;
	}

	public void setyVeloc(double yVeloc) {
		this.yVeloc = yVeloc;
	}

	public double getyVeloc() {
		return yVeloc;
	}

	public void setActiveImage(Drawable activeImage) {
		this.activeImage = activeImage;
		if(collisionBox==null)
			setCollisionBox(activeImage);
	}

	public Drawable getActiveImage() {
		return activeImage;
	}

	public void setInactiveImage(Drawable inactiveImage) {
		this.inactiveImage = inactiveImage;
		if(collisionBox==null)
			setCollisionBox(inactiveImage);
	}

	public Drawable getInactiveImage() {
		return inactiveImage;
	}

	public void setCollisionBox(Drawable collisionBox) {
		this.collisionBox = collisionBox;
	}

	public Drawable getCollisionBox() {
		return collisionBox;
	}
}
