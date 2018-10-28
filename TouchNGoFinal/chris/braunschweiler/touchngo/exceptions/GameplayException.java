package chris.braunschweiler.touchngo.exceptions;

public class GameplayException extends Exception{
	private String message;
	
	public GameplayException(String message){
		this.message = message;
	}

	public String getMessage(){
		return this.message;
	}
}
