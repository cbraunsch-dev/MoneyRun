package chris.braunschweiler.touchngo.exceptions;

public class LevelNotFoundException extends Exception{
	private String message;
	
	public LevelNotFoundException(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
}
