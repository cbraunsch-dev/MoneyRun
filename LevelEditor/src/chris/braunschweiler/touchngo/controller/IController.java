/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chris.braunschweiler.touchngo.controller;

import chris.braunschweiler.touchngo.level.LevelEntity;
import java.util.List;
import java.util.Set;

/**
 *
 * @author chrisbraunschweiler
 * Interface that the controller offers to the view.
 */
public interface IController {

    public Set<String> getLevelIds();
    public LevelEntity getLevel(String levelId);
    public List<LevelEntity> getLevels();
    public void addLevel(String levelId, LevelEntity level);
    public void removeLevel(String levelId);
    public boolean levelExists(String levelId);
    public void saveGame(List<LevelEntity> gameLevels)throws Exception;
}
