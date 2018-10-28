package chris.braunschweiler.touchngo.entities;

import java.util.ArrayList;

/**
 * The goal with which the player's critter needs to collide in order to beat the current level.
 * @author chrisbraunschweiler
 *
 */
public class Goal implements ICollidable{

	private int column;
	private int row;
	private int globalId;
	private String objectIdentifier;
	private ArrayList<ICollidedListener> listeners;
	private boolean active;
	private boolean occupied;
	
	public Goal(int column, int row, int globalId){
		this.column = column;
		this.row = row;
		this.globalId = globalId;
		this.active = true;
		listeners = new ArrayList<ICollidedListener>();
		this.occupied = false;
	}
	
	public Goal(int column, int row, int globalId, String objectIdentifier){
		this.column = column;
		this.row = row;
		this.globalId = globalId;
		this.objectIdentifier = objectIdentifier;
		this.active = true;
		listeners = new ArrayList<ICollidedListener>();
		this.occupied = false;
	}
	
	public void collideWith(IPlayableCharacter player) {
		if(active){
			if(!occupied){
				for(ICollidedListener listener:listeners){
					listener.collisionBetweenPlayerAndGoal(this, player);
				}
			}
			
			this.occupied = true;
		}
	}

	public int getCol() {
		return this.column;
	}

	public int getGlobalId() {
		return this.globalId;
	}

	public int getRow() {
		return this.row;
	}

	public void registerCollidedListener(ICollidedListener listener) {
		this.listeners.add(listener);
	}

	public CollidableType getType() {
		return CollidableType.GOAL;
	}

	public String getObjectIdentifer() {
		return this.objectIdentifier;
	}

	public void toggleActiveFlag() {
		this.active = !this.active;
	}

	public boolean isActive() {
		return this.active;
	}
}
