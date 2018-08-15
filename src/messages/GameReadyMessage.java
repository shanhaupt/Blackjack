package messages;

public class GameReadyMessage implements message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1143066659239288944L;
	
	String message = "Let the game commence.  Good luck to all players!";
	
	public GameReadyMessage(){
		
	}
	
	public String getMessage() {
		return message;
	}

}
