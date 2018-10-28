package chris.braunschweiler.touchngo.controller;

import java.util.List;

import chris.braunschweiler.touchngo.dto.MoneyBagDTO;

/**
 * Any class that implements this interface can be notified by the level handler of any necessary
 * events.
 * @author chrisbraunschweiler
 *
 */
public interface ILevelHandlerListener {
	
	/**
	 * Called when the player is leaving the level.
	 * @param The number of coins the user collected in the level.
	 */
	void onLeaveLevel(int numberOfCoinsCollected, List<MoneyBagDTO> bagsAlreadyCollected);
	
	/**
	 * Called when the player deposited a money bag.
	 * @param totalNumberOfCoinsCollectedInLevel The number of coins that have been collected so far. This
	 * includes the coins that were just deposited in the bank.
	 */
	void onMoneyBagDeposited(int totalNumberOfCoinsCollectedInLevel);
	
	/**
	 * Called when all players have died.
	 */
	void onAllPlayersDied();
}
