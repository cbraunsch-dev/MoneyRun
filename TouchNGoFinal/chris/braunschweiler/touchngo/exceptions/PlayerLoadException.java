package chris.braunschweiler.touchngo.exceptions;

public class PlayerLoadException extends Exception{
	private String message;
	
	public PlayerLoadException(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
}
