package messages;

public class joinGameWaitingMessage implements message{


	private static final long serialVersionUID = 8079753029270682707L;
	String message = "The game will start shortly.  Waiting for other players to join...";
	
	public joinGameWaitingMessage() {
		
	}
	
	public String getMessage() {
		return message;
	}

}
