package messages;

import server.ServerThread;

public class myBetMessage implements message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4937911618217574041L;
	int currentNumChips;
	int betAmount;
	transient ServerThread better;
	String betterName;
	int position;
	
	public myBetMessage(int currentNumChips, ServerThread better, String betterName, int position, int numPlayers) {
		this.currentNumChips = currentNumChips;
		this.better = better;
		this.betterName = betterName;
		this.position = position;
	}
	
	public ServerThread getBetterThread() {
		return better;
	}
	
	public String getBetterName() {
		return betterName;
	}
	
	public int getBetAmount() {
		return betAmount;
	}
	
	public int getCurrentNumChips() {
		return currentNumChips;
	}
	
	public void setCurrentBet(int bet) {
		this.betAmount = bet;
	}
	
	public int getPosition() {
		return position;
	}
	
}
