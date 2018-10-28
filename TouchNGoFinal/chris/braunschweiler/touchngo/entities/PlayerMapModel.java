package chris.braunschweiler.touchngo.entities;

import chris.braunschweiler.touchngo.common.CommonDataManager;

/**
 * A simplified model of the level's tmx map. This allows a player to determine what kind of tile
 * he's currently on.
 * @author chrisbraunschweiler
 *
 */
public class PlayerMapModel {
	public static final int LAND = 0;
	public static final int WATER = 1;
	
	private int[][] mapData;
	
	public PlayerMapModel(int[][] mapData){
		this.mapData = mapData;
	}
	
	public int getTileProperty(int pixelPositionX, int pixelPositionY){
		int column = pixelPositionX / CommonDataManager.TILE_SIZE;
		int row = pixelPositionY / CommonDataManager.TILE_SIZE;
		
		return mapData[column][row];
	}
}
