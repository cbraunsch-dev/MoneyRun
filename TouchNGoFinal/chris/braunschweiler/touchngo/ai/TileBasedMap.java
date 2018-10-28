package chris.braunschweiler.touchngo.ai;

/**
 * The description for the data we're pathfinding over. This provides the contract
 * between the data being searched (i.e. the in game map) and the path finding
 * generic tools
 * 
 * @author Kevin Glass
 */
public interface TileBasedMap {
	/**
	 * Get the width of the tile map. The slightly odd name is used
	 * to distiguish this method from commonly used names in game maps.
	 * 
	 * @return The number of tiles across the map
	 */
	public int getWidthInTiles();

	/**
	 * Get the height of the tile map. The slightly odd name is used
	 * to distiguish this method from commonly used names in game maps.
	 * 
	 * @return The number of tiles down the map
	 */
	public int getHeightInTiles();
	
	/**
	 * Notification that the path finder visited a given tile. This is 
	 * used for debugging new heuristics.
	 * 
	 * @param x The x coordinate of the tile that was visited
	 * @param y The y coordinate of the tile that was visited
	 */
	public void pathFinderVisited(int x, int y);
	
	/**
	 * Check if the given location is blocked, i.e. blocks movement of 
	 * the supplied mover.
	 * 
	 * @param mover The mover that is potentially moving through the specified
	 * tile.
	 * @param x The x coordinate of the tile to check
	 * @param y The y coordinate of the tile to check
	 * @return True if the location is blocked
	 */
	public boolean blocked(Mover mover, int x, int y);
	
	/**
	 * Get the cost of moving through the given tile. This can be used to 
	 * make certain areas more desirable. A simple and valid implementation
	 * of this method would be to return 1 in all cases.
	 * 
	 * @param mover The mover that is trying to move across the tile
	 * @param sx The x coordinate of the tile we're moving from
	 * @param sy The y coordinate of the tile we're moving from
	 * @param tx The x coordinate of the tile we're moving to
	 * @param ty The y coordinate of the tile we're moving to
	 * @return The relative cost of moving across the given tile
	 */
	public float getCost(Mover mover, int sx, int sy, int tx, int ty);
	
	/**
	 * Blocks the tile at the given x and y coordinates with the blockage passed.
	 * @param x The x coordinate of the tile that should be blocked.
	 * @param y The y coordinate of the tile that should be blocked.
	 * @param blockage The object that should block the given tile.
	 */
	public void blockTile(int x, int y, int blockage);
	
	/**
	 * Clears all the guard-path blockages from the map. When guards plan a path, they block the tiles
	 * through which they plan to go so that other guards don't pick the same path. After all the guards have planned
	 * their paths, the map needs to be cleared of the blockages the guards created so that the blockages don't keep
	 * adding up over time.
	 */
	public void clearMapOfGuardPaths();
	
	/**
	 * Returns the property of the tile at the given absolute pixel position (as opposed to row/col position).
	 * @param pixelPositionX The absolute pixel position (not the column but the absolute x position in pixels).
	 * @param pixelPositionY The absolute pixel position (not the row but the absolute y position in pixels).
	 * @return The property of the tile at the given position.
	 */
	public int getTileProperty(int pixelPositionX, int pixelPositionY);
}
