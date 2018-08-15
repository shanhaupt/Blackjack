package messages;

import java.util.Vector;

public class StatusMessage implements message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7305248270929499041L;
	int numPlayers;
	Vector <String> playerNames;
	
	Vector<Integer> player1Values;
	Vector<Integer> player2Values;
	Vector<Integer> player3Values;
	
	Vector<String> player1Cards;
	Vector<String> player2Cards;
	Vector<String> player3Cards;
	Vector<String> dealerCards;
	
	int player1Chips;
	int player2Chips;
	int player3Chips;
	
	int player1Bet;
	int player2Bet;
	int player3Bet;
	
	public StatusMessage(){
		
	}
	
	public void setDealerCards(Vector<String> dealerCards) {
		this.dealerCards = dealerCards;
	}
	
	public Vector<String> getDealerCards() {
		return dealerCards;
	}
	
	public void setPlayer1Bet(int player1Bet) {
		this.player1Bet = player1Bet;
	}
	
	public int getPlayer1Bet() {
		return player1Bet;
	}
	
	public void setPlayer2Bet(int player2Bet) {
		this.player2Bet = player2Bet;
	}
	
	public int getPlayer2Bet() {
		return player2Bet;
	}
	
	public void setPlayer3Bet(int player3Bet) {
		this.player3Bet = player3Bet;
	}
	
	public int getPlayer3Bet() {
		return player3Chips;
	}
	
	public void setPlayer1Chips(int player1Chips) {
		this.player1Chips = player1Chips;
	}
	
	public int getPlayer1Chips() {
		return player1Chips;
	}
	
	public void setPlayer2Chips(int player2Chips) {
		this.player2Chips = player2Chips;
	}
	
	public int getPlayer2Chips() {
		return player2Chips;
	}
	
	public void setPlayer3Chips(int player3Chips) {
		this.player3Chips = player3Chips;
	}
	
	public int getPlayer3Chips() {
		return player3Chips;
	}
	
	
	
	public void setPlayer1Cards(Vector<String> player1Cards) {
		this.player1Cards = player1Cards;
	}
	
	public Vector<String> getPlayer1Cards(){
		return player1Cards;
	}
	
	public void setPlayer2Cards(Vector<String> player2Cards) {
		this.player2Cards = player2Cards;
	}
	
	public Vector<String> getPlayer2Cards(){
		return player2Cards;
	}
	
	public void setPlayer3Cards(Vector<String> player3Cards) {
		this.player3Cards = player3Cards;
	}
	
	public Vector<String> getPlayer3Cards(){
		return player3Cards;
	}
	
	
	public void setPlayer1Values(Vector<Integer> player1Values) {
		this.player1Values = player1Values;
	}
	
	public Vector<Integer> getPlayer1Values() {
		return player1Values;
	}
	
	public void setPlayer2Values(Vector<Integer> player2Values) {
		this.player2Values = player2Values;
	}
	
	public Vector<Integer> getPlayer2Values() {
		return player2Values;
	}
	
	public void setPlayer3Values(Vector<Integer> player3Values) {
		this.player3Values = player3Values;
	}
	
	public Vector<Integer> getPlayer3Values() {
		return player3Values;
	}
	
	
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	
}
