package testing;

import java.util.Vector;

import cards.*;

public class Deck_Testing {
	
	public static void main (String [] args)  {
		//Testing the Deck of Cards 
		Deck myDeck = new Deck();
		myDeck.newDeck();
		Vector<Card> myCards = myDeck.getDeck();
		for (int i=0; i<myCards.size(); i++) {
			System.out.println("Card: " + myCards.elementAt(i).getValue()+" "+myCards.elementAt(i).getSuit());
		}
		myDeck.shuffleDeck();
		System.out.println("----------------------------------------");
		myCards = myDeck.getDeck();
		for (int i=0; i<myCards.size(); i++) {
			System.out.println("Card: " + myCards.elementAt(i).getValue()+" "+myCards.elementAt(i).getSuit()+"  "+myCards.elementAt(i).getWeight());
		}
		
		//check to make sure deck has 52 cards
		if (myCards.size() != 52) {
			System.out.println("There are not 52 cards in the deck!!");
		}
		
		
	}
}
