/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * TODO: Make sure that levels are returned in the order that they were added.
 */

package chris.braunschweiler.touchngo.controller;

import chris.braunschweiler.filehandler.GameLoaderImpl;
import chris.braunschweiler.filehandler.IGameLoader;
import chris.braunschweiler.touchngo.level.LevelEntity;
import chris.braunschweiler.touchngo.view.ViewCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author chrisbraunschweiler
 */
public class MyController implements IController{

    private ViewCallback viewCallback;
    private IGameLoader gameLoader;

    private TreeMap<String,LevelEntity> gameLevels;

    public MyController(ViewCallback viewCallback){
        this.viewCallback = viewCallback;
        gameLoader = new GameLoaderImpl();
        gameLevels = new TreeMap<String,LevelEntity>();
    }

    public Set<String> getLevelIds() {
        return gameLevels.keySet();
    }

    public LevelEntity getLevel(String levelId) {
        return gameLevels.get(levelId);
    }

    public void addLevel(String levelId, LevelEntity level) {
        gameLevels.put(levelId, level);
    }

    public void removeLevel(String levelId) {
        gameLevels.remove(levelId);
    }

    public boolean levelExists(String levelId) {
        return gameLevels.containsKey(levelId);
    }

    public void saveGame(List<LevelEntity> gameLevels) throws Exception {
        gameLoader.saveGame(gameLevels);
    }

    public List<LevelEntity> getLevels() {
        List<LevelEntity> levelList = new ArrayList<LevelEntity>();
        Set<String> levelKeys = gameLevels.keySet();
        for(String levelKey:levelKeys){
            levelList.add(gameLevels.get(levelKey));
        }
        return levelList;
    }
}
