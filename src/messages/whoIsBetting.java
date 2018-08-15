package messages;

public class whoIsBetting implements message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3296035723605635539L;
	
	String betterName;
	int betAmount;
	
	public whoIsBetting(String betterName){
		this.betterName = betterName;
	}
	
	public String getBetterName() {
		return betterName;
	}
	
	public int getBetAmount() {
		return betAmount;
	}

}
