package chris.braunschweiler.touchngo.utils;

import java.util.ArrayList;
import java.util.Hashtable;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;

import chris.braunschweiler.touchngo.common.CommonDataManager;

public class TileUtilities {
	
	/**
	 * Returns a list of tile properties for the given tile.
	 * @param tileMap
	 * @param tile
	 * @return
	 */
    public static ArrayList<TMXTileProperty> MakeTilePropertyArrayListForTile(TMXTiledMap tileMap, TMXTile tile){
    	ArrayList<TMXTileProperty> tilePropertyList = new ArrayList<TMXTileProperty>();
    	
    	int globalId = tile.getGlobalTileID();
        
        TMXProperties<TMXTileProperty> properties = null;
        try {
                properties = tileMap.getTMXTileProperties(globalId);
        }
        catch(final IllegalArgumentException tmxle)
        {
        }
        finally
        {
        }
       
        if( properties != null ) {
        	for(TMXTileProperty property:properties){
        		tilePropertyList.add(property);
        	}
        }
        
        return tilePropertyList;
    }
	
	public static Hashtable<String, String> MakeHashtableForTile(TMXTiledMap tileMap,
                    TMXTile tile) {
            Hashtable<String, String> tileTable = new Hashtable<String, String>();
   
           
            int globalId = tile.getGlobalTileID();
            
            TMXProperties<TMXTileProperty> properties = null;
            try {
                    properties = tileMap.getTMXTileProperties(globalId);
            }
            catch(final IllegalArgumentException tmxle)
            {
            }
            finally
            {
            }
           
            if( properties != null ) {
                   
                    final int tilePropertyCount = properties.size();
                    for(int i = 0; i < tilePropertyCount; i++) {
                            final TMXTileProperty tileProperty = properties.get(i);
                            tileTable.put(tileProperty.getName(), tileProperty.getValue());
                    }
            }
           
            return tileTable;
    }

	public static boolean listContainsTilePropertyWithName(
			ArrayList<TMXTileProperty> tileProperties, String propertyName) {
		for (TMXTileProperty property : tileProperties) {
			if (property.getName().equals(propertyName)) {
				return true;
			}
		}
		return false;
	}

	public static String getValueOfTilePropertyWithName(
			ArrayList<TMXTileProperty> tileProperties, String propertyName) {
		for (TMXTileProperty property : tileProperties) {
			if (property.getName().equals(propertyName)) {
				return property.getValue();
			}
		}
		return null;
	}

	/**
	 * Returns the tile position that corresponds to the given pixel position. 
	 * @param pixelPosition The absolute pixel position.
	 * @return The tile position that corresponds to the given pixel position. For example,
	 * if the parameter is 180 pixels, the returned value would be 2 as in pixel row 2 or column 2 since
	 * that row/column corresponds to 180 pixels (returned values are always rounded down).
	 */
	public static int GetTilePosition(int pixelPosition){
		double positionDouble = pixelPosition / CommonDataManager.TILE_SIZE;
		int tilePosition = (int) Math.round(positionDouble);
		return tilePosition;
	}
}
