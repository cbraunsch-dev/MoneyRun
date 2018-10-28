package chris.braunschweiler.touchngo.common;

import org.anddev.andengine.entity.text.ChangeableText;

/**
 * Helper class for Menu-related tasks.
 * @author chrisbraunschweiler
 *
 */
public class MenuHelper {
	
	public static void updateCoinsCollectedHUDText(ChangeableText text, int numberOfCoinsCollected, int totalNumberOfCoinsInLevel){
		String coinsCollectedString;
		String totalCoinsAvailableString;
		if(numberOfCoinsCollected < 10){
			coinsCollectedString = "0" + numberOfCoinsCollected;
		}
		else{
			coinsCollectedString = "" + numberOfCoinsCollected;
		}
		if(totalNumberOfCoinsInLevel < 10){
			totalCoinsAvailableString = "0" + totalNumberOfCoinsInLevel;
		}
		else{
			totalCoinsAvailableString = "" + totalNumberOfCoinsInLevel;
		}
		
		text.setText("Coins: " + coinsCollectedString + "/" + totalCoinsAvailableString);
	}
}
