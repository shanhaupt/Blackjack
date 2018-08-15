package gameplay;

import java.util.Vector;

import cards.*;

public class dealer {
	Deck myDeck = new Deck();
	Vector<Card> myCards = null;
	
	 public dealer(){
		myDeck.newDeck();
	}
	 
	 public void newDeck() {
		 myDeck.newDeck();
	 }
	 
	 public void shuffleDeck() {
		 myDeck.shuffleDeck();
	 }
	 
	public Card hitPlayer(){
		if(myDeck.getDeck().size() == 0) {
			myDeck.newDeck();
			myDeck.shuffleDeck();
		}
		
		
		Card playerCard = myDeck.getDeck().get(myDeck.getDeck().size()-1);
		myDeck.getDeck().remove(myDeck.getDeck().size()-1);
		return playerCard;
	}
	
	public void clearCards() {
		if(myCards != null) {
			myCards.clear();
		}
		
	}
	
	public void hitDealer() {
		if(myDeck.getDeck().size() == 0) {
			myDeck.newDeck();
			myDeck.shuffleDeck();
		}
		
		
		Card dealerCard = myDeck.getDeck().get(myDeck.getDeck().size()-1);
		myCards.add(dealerCard);
		myDeck.getDeck().remove(myDeck.getDeck().size()-1);
		
		
	}
	

	public void testing() {
		for (int i=0; i<myCards.size(); i++) {
			System.out.println("Card: " + myCards.elementAt(i).getValue()+" "+myCards.elementAt(i).getSuit()+"  "+myCards.elementAt(i).getWeight());
		}
	}
	
	public Vector<Card> getDealerCards(){
		return myCards;
	}
	
	
	
	
}
