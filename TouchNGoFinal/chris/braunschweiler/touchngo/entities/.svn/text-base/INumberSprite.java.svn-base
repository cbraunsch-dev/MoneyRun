package chris.braunschweiler.touchngo.entities;

import chris.braunschweiler.touchngo.exceptions.DisplayFormatException;

/**
 * Defines an interface for a number sprite. A number sprite simply displays a numeric value using sprites.
 * The current version can only display 3 digit values. The values 000 through 999 can be displayed.
 * @author chrisbraunschweiler
 *
 */
public interface INumberSprite {
	
	/**
	 * Displays the given number using animated sprites.
	 * @param number The number to be displayed.
	 * @throws DisplayFormatException This is thrown if the number passed is bigger than 999. The current
	 * NumberSprite can only display values less than or equal to three digit numbers.
	 */
	public void displayNumber(int number) throws DisplayFormatException;
}
