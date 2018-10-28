package chris.braunschweiler.touchngo.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * A Switch entity. Switches can trigger changes in state in other game entities (ie: activating/inactivating other entities).
 * @author chrisbraunschweiler
 *
 */
public class Switch implements ICollidable{
	
	private int column;
	private int row;
	private int globalId;
	private String objectIdentifier;	//The identifier used to find the object in case another Switch has triggered a state change in this object
	private ArrayList<ICollidedListener> listeners;
	private boolean active;
	private String idOfAffectedObjects;
	private boolean alreadyPressed;
	
	public Switch(int column, int row, int globalId, String idOfAffectedObjects){
		this.column = column;
		this.row = row;
		this.globalId = globalId;
		this.active = true;
		this.listeners = new ArrayList<ICollidedListener>();
		this.idOfAffectedObjects = idOfAffectedObjects;
		this.alreadyPressed = false;
	}
	
	public Switch(int column, int row, int globalId, String idOfAffectedObjects, String objectIdentifier){
		this(column, row, globalId, idOfAffectedObjects);
		this.objectIdentifier = objectIdentifier;
	}
	
	public void collideWith(IPlayableCharacter player) {
		if(active){
			if(!alreadyPressed){
				for(ICollidedListener listener:listeners){
					listener.collided(this, player);
				}
				
				this.alreadyPressed = true;
			}
		}
	}

	public CollidableType getType() {
		return CollidableType.SWITCH;
	}

	public void registerCollidedListener(ICollidedListener listener) {
		this.listeners.add(listener);
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

	public String getObjectIdentifer() {
		return this.objectIdentifier;
	}

	public void toggleActiveFlag() {
		this.active = !this.active;
	}

	public void setIdOfAffectedObjects(String idOfAffectedObjects) {
		this.idOfAffectedObjects = idOfAffectedObjects;
	}

	public String getIdOfAffectedObjects() {
		return idOfAffectedObjects;
	}

	public boolean isActive() {
		return this.active;
	}
}
