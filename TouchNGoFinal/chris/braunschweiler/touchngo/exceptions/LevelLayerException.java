package chris.braunschweiler.touchngo.exceptions;

/**
 * A level layer exception that notifies the recipient (catcher) of a problem that has occurred with the
 * level layers.
 * @author chrisbraunschweiler
 *
 */
public class LevelLayerException extends Exception {
	
	/**
	 * Constructs a new LevelLayerException.
	 * @param message The message of the exception.
	 */
	public LevelLayerException(final String message) {
		super(message);
	}
}
