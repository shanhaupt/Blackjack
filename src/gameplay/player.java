package gameplay;



import java.util.Vector;

import cards.Card;
import server.ServerThread;

public class player{
	String name;
	ServerThread myServerThread;
	
	String gameName;
	
	
	int numChips = 500;
	int currentBet = 0;
	
	Vector<Card> myCards = new Vector<Card>();
	dealer myDealer;
	 
	 
	boolean isCreator = false;
	boolean hasBust = false;
	
	
	
	public player(String name, String gameName, dealer myDealer, ServerThread mst){
		this.name = name;
		this.gameName = gameName;
		this.myDealer = myDealer;
		this.myServerThread = mst;
	}
	
	
	
	public void hit(){
		myCards.add(myDealer.hitPlayer());
		//checkBust();
	}
	
	public void clearCards() {
		if(myCards.size() != 0) {
			myCards.clear();
		}
	}
	
	public Vector<Integer> cardTotal() {
		
		Vector<Integer> cardTotals = new Vector<Integer>(); 
		
		int sumNoAces = 0;
		int numAces = 0;
		
		for (int i=0; i<myCards.size(); i++) {
			if(myCards.get(i).getWeight() == 111) {
				numAces += 1;
			} else {
				sumNoAces += myCards.get(i).getWeight();
			}
		}
		if (numAces == 0) {
			cardTotals.add(sumNoAces);
		} else if(numAces == 1) {
			cardTotals.add(sumNoAces + 1);
			cardTotals.add(sumNoAces + 11);
				
		} else if (numAces == 2) {
			cardTotals.add(sumNoAces + 2);
			cardTotals.add(sumNoAces + 12);
			cardTotals.add(sumNoAces + 22);
			
		} else if (numAces == 3) {
			cardTotals.addElement(sumNoAces + 3);
			cardTotals.addElement(sumNoAces + 13);
			cardTotals.addElement(sumNoAces + 23);
			cardTotals.addElement(sumNoAces + 33);
				
		} else if (numAces == 4) {
				
			cardTotals.addElement(sumNoAces + 4);
			cardTotals.addElement(sumNoAces + 14);
			cardTotals.addElement(sumNoAces + 24);
			cardTotals.addElement(sumNoAces + 34);
			cardTotals.addElement(sumNoAces + 44);
		}
		return cardTotals;
	}
	
	
	public boolean checkBust(Vector<Integer> possibilities) {
		
		boolean bust = true;
		
		for (int i = 0; i<possibilities.size(); i++) {
			if(possibilities.get(i) <= 21) {
				bust = false;
			}
		}
		return bust;
	}
	
	

	public String getName() {
		return name;
	}
	
	public int getCurrentBet() {
		return currentBet;
	}
	
	public void setAsCreator() {
		isCreator = true;
	}
	
	public void setCurrentBet(int bet) {
		currentBet = bet;
	}
	
	public boolean getIsCreator() {
		return isCreator;
	}
	
	public void testing() {
		for(int i=0; i<myCards.size(); i++) {
			System.out.println(myCards.get(i).getSuit() + " " + myCards.get(i).getValue());
		}
	}
	
	public ServerThread getServerThread() {
		return myServerThread;
	}
	
	public int getNumChips() {
		return numChips;
	}
	
	public Vector<Card> getCards(){
		return myCards;
	}
	
	
	
}
