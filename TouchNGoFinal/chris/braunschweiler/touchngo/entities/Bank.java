package chris.braunschweiler.touchngo.entities;

import java.util.ArrayList;

import chris.braunschweiler.touchngo.entities.ICollidable.CollidableType;

/**
 * A bank is the place the player can deposit his money bag. The number of coins in the bag is then added to
 * the player's total number of coins collected in the current level.
 * @author chrisbraunschweiler
 *
 */
public class Bank implements ICollidable{
	private int column;
	private int row;
	private int globalId;
	private String objectIdentifier;
	private ArrayList<ICollidedListener> listeners;
	private boolean active;
	
	public Bank(int column, int row, int globalId){
		this.column = column;
		this.row = row;
		this.globalId = globalId;
		this.active = true;
		listeners = new ArrayList<ICollidedListener>();
	}
	
	public Bank(int column, int row, int globalId, String objectIdentifier){
		this.column = column;
		this.row = row;
		this.globalId = globalId;
		this.objectIdentifier = objectIdentifier;
		this.active = true;
		listeners = new ArrayList<ICollidedListener>();
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
		return CollidableType.BANK;
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

	public void collideWith(IPlayableCharacter player) {
		if(active){
			for(ICollidedListener listener:listeners){
				listener.collisionBetweenPlayerAndBank(this, player);
			}
		}
	}
}
