package messages;

public class theirBetAmountMessage implements message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1036979275784100364L;
	
	int bet;
	String better;
	
	public theirBetAmountMessage(int bet, String better){
		this.bet = bet;
		this.better = better;
	}
	
	public int getBetAmount() {
		return bet;
	}
	
	public String getBetter() {
		return better;
	}

}
