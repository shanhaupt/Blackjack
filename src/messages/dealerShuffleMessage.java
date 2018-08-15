package messages;

public class dealerShuffleMessage implements message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4147664313315563953L;
	String message = "Dealer is shuffling cards...";
	
	public dealerShuffleMessage(){
		
	}
	
	public String getMessage() {
		return message;
	}
}
