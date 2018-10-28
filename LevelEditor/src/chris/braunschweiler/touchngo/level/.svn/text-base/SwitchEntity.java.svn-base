/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chris.braunschweiler.touchngo.level;

import java.util.ArrayList;

/**
 *
 * @author chrisbraunschweiler
 */
public class SwitchEntity extends GameEntity{

    private ArrayList<GameEntity> affectedEntities;

    public SwitchEntity(String id, double xCoord, double yCoord, double width,
            double height, boolean active){
        super(id, xCoord,yCoord,width,height,active);
        affectedEntities = new ArrayList<GameEntity>();
    }

    public SwitchEntity(String id, double xCoord, double yCoord, double width,
            double height, boolean active, ArrayList<GameEntity> affectedEntities){
        super(id, xCoord,yCoord,width,height,active);
        this.affectedEntities = affectedEntities;
    }

    /**
     * @return the affectedEntities
     */
    public ArrayList<GameEntity> getAffectedEntities() {
        return affectedEntities;
    }

    /**
     * @param affectedEntities the affectedEntities to set
     */
    public void setAffectedEntities(ArrayList<GameEntity> affectedEntities) {
        this.affectedEntities = affectedEntities;
    }
}
