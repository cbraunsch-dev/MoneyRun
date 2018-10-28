package chris.braunschweiler.touchngo.gui;

import org.anddev.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.anddev.andengine.entity.text.Text;

/**
 * Container object for menu items.
 * @author chrisbraunschweiler
 *
 */
public class MenuItemContainer {
	private AnimatedSpriteMenuItem menuItem;
	private Text lockedLevelText;
	private Text unlockedLevelText;
	
	public MenuItemContainer(AnimatedSpriteMenuItem menuItem, Text lockedLevelText, Text unlockedLevelText){
		this.menuItem = menuItem;
		this.lockedLevelText = lockedLevelText;
		this.unlockedLevelText = unlockedLevelText;
	}
	
	public AnimatedSpriteMenuItem getMenuItem(){
		return this.menuItem;
	}
	
	public Text getLockedLevelText(){
		return this.lockedLevelText;
	}
	
	public Text getUnlockedLevelText(){
		return this.unlockedLevelText;
	}
}
