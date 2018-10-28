/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chris.braunschweiler.touchngo.view;

import chris.braunschweiler.touchngo.level.GameEntity;

/**
 *
 * @author chrisbraunschweiler
 * The callback interface of the view. Used by the controller to
 * update the View.
 */
public interface ViewCallback {

    //TODO: Check for duplicate entities when adding entities. Throw an
    //exception if there's a duplicate (ie: if an entity already exists which
    //has the same ID as that of the new entity which is to be added.
    public void addHorizontalWall(GameEntity gameEntity);
    public void addVerticalWall(GameEntity gameEntity);
    public void addBall(GameEntity gameEntity);
    public void addGoal(GameEntity gameEntity);
    public void addSwitch(GameEntity gameEntity);
}
