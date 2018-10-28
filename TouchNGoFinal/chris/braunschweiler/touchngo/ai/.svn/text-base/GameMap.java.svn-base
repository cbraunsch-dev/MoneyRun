package chris.braunschweiler.touchngo.ai;

import chris.braunschweiler.touchngo.common.CommonDataManager;
import chris.braunschweiler.touchngo.common.CommonDataManager.PlayerAbility;

/**
 * The data map from our example game. This holds the state and context of each tile
 * on the map. It also implements the interface required by the path finder. It's implementation
 * of the path finder related methods add specific handling for the types of units
 * and terrain in the example game.
 * 
 * @author Kevin Glass
 */
public class GameMap implements TileBasedMap {
	/** The map width in tiles */
	public static final int WIDTH = 6;
	/** The map height in tiles */
	public static final int HEIGHT = 10;
	
	/** Indicate grass terrain at a given location */
	public static final int FLATLAND = 0;
	/** Indicate water terrain at a given location */
	public static final int WATER = 1;
	/** Indicate trees terrain at a given location */
	public static final int WALL = 2;
	/** Indicate a plane is at a given location */
	public static final int GUARD = 3;
	
	public static final int OBSTACLE = 4; //TODO: Temporary. Will be replaced by water and wall once it works.
	
	/** The terrain settings for each tile in the map */
	private int[][] terrain = new int[WIDTH][HEIGHT];
	
	/**
	 * Servers as a backup map for the terrain map.
	 */
	private int[][] terrainBackup = new int[WIDTH][HEIGHT];
	
	
	/** The unit in each tile of the map */
	private int[][] units = new int[WIDTH][HEIGHT];
	/** Indicator if a given tile has been visited during the search */
	private boolean[][] visited = new boolean[WIDTH][HEIGHT];
	
	/**
	 * Create a new map with the given terrain data
	 */
	public GameMap(int[][] loadedTerrain) {
		this.terrain = loadedTerrain;
		copyArrayValues(this.terrain, this.terrainBackup);
	}

	private void copyArrayValues(int[][] sourceTable, int[][] targetTable) {
		for(int i = 0; i < sourceTable.length; i++){
			for(int j = 0; j < sourceTable[i].length; j++){
				targetTable[i][j] = sourceTable[i][j];
			}
		}
	}

	/**
	 * Fill an area with a given terrain type
	 * 
	 * @param x The x coordinate to start filling at
	 * @param y The y coordinate to start filling at
	 * @param width The width of the area to fill
	 * @param height The height of the area to fill
	 * @param type The terrain type to fill with
	 */
	private void fillArea(int x, int y, int width, int height, int type) {
		for (int xp=x;xp<x+width;xp++) {
			for (int yp=y;yp<y+height;yp++) {
				terrain[xp][yp] = type;
			}
		}
	}
	
	/**
	 * Clear the array marking which tiles have been visted by the path 
	 * finder.
	 */
	public void clearVisited() {
		for (int x=0;x<getWidthInTiles();x++) {
			for (int y=0;y<getHeightInTiles();y++) {
				visited[x][y] = false;
			}
		}
	}
	
	/**
	 * @see TileBasedMap#visited(int, int)
	 */
	public boolean visited(int x, int y) {
		return visited[x][y];
	}
	
	/**
	 * Get the terrain at a given location
	 * 
	 * @param x The x coordinate of the terrain tile to retrieve
	 * @param y The y coordinate of the terrain tile to retrieve
	 * @return The terrain tile at the given location
	 */
	public int getTerrain(int x, int y) {
		return terrain[x][y];
	}
	
	/**
	 * Get the unit at a given location
	 * 
	 * @param x The x coordinate of the tile to check for a unit
	 * @param y The y coordinate of the tile to check for a unit
	 * @return The ID of the unit at the given location or 0 if there is no unit 
	 */
	public int getUnit(int x, int y) {
		return units[x][y];
	}
	
	/**
	 * Set the unit at the given location
	 * 
	 * @param x The x coordinate of the location where the unit should be set
	 * @param y The y coordinate of the location where the unit should be set
	 * @param unit The ID of the unit to be placed on the map, or 0 to clear the unit at the
	 * given location
	 */
	public void setUnit(int x, int y, int unit) {
		units[x][y] = unit;
	}
	
	/**
	 * @see TileBasedMap#blocked(Mover, int, int)
	 */
	public boolean blocked(Mover mover, int x, int y) {
		if(mover.getPlayerAbility() == PlayerAbility.SWIM){
			if(getTerrain(x,y) == WALL){
				return true;
			}
		}
		else if(mover.getPlayerAbility() == PlayerAbility.NONE){
			if(getTerrain(x,y) == WATER || getTerrain(x,y) == WALL){
				return true;
			}
		}
		/*if(getTerrain(x,y) == OBSTACLE || getTerrain(x,y) == GUARD || getTerrain(x,y) == WATER || getTerrain(x,y) == WALL){
			return true;
		}*/
		return false;
	}

	/**
	 * @see TileBasedMap#getCost(Mover, int, int, int, int)
	 */
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		return 1;
	}

	/**
	 * @see TileBasedMap#getHeightInTiles()
	 */
	public int getHeightInTiles() {
		return HEIGHT;
	}

	/**
	 * @see TileBasedMap#getWidthInTiles()
	 */
	public int getWidthInTiles() {
		return WIDTH;
	}

	/**
	 * @see TileBasedMap#pathFinderVisited(int, int)
	 */
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}

	public void blockTile(int x, int y, int blockage) {
		terrain[x][y] = blockage;
	}

	public void clearMapOfGuardPaths() {
		this.copyArrayValues(this.terrainBackup, this.terrain);
	}

	public int getTileProperty(int pixelPositionX, int pixelPositionY) {
		int column = pixelPositionX / CommonDataManager.TILE_SIZE;
		int row = pixelPositionY / CommonDataManager.TILE_SIZE;
		
		return terrain[column][row];
	}
}
