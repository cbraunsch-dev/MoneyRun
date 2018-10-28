package chris.braunschweiler.touchngo.exceptions;

/**
 * Thrown if a target is unreachable.
 * @author chrisbraunschweiler
 *
 */
public class TargetUnreachableException extends Exception{
	public TargetUnreachableException(String message){
		super(message);
	}
}
