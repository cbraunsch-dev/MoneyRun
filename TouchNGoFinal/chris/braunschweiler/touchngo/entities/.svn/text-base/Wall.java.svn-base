package chris.braunschweiler.touchngo.entities;

import java.util.ArrayList;

public class Wall implements IObstacle{

	private int column;
	private int row;
	private int globalId;
	private String objectIdentifier;
	private boolean active;
	
	public Wall(int column, int row, int globalId){
		this.column = column;
		this.row = row;
		this.globalId = globalId;
		this.active = true;
	}
	
	public Wall(int column, int row, int globalId, String objectIdentifier){
		this.column = column;
		this.row = row;
		this.globalId = globalId;
		this.objectIdentifier = objectIdentifier;
		this.active = true;
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

	public ObstacleType getType() {
		return ObstacleType.WALL;
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
