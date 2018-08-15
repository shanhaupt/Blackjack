package cards;

import java.util.Random;
import java.util.Vector;

public class Deck {
	
	private Vector<Card> deck = new Vector<Card>();
	
	public Deck () {
	
	}
	
	public void newDeck(){
		
		deck.clear();
		
		for (int j=1; j<=4; j++) {
			for (int i=1; i<=13; i++) {
						
				String value;
				int weight;
				if (i==1) {
					value = "Ace";
					weight = 111;
				} else if(i==11) {
					value = "Jack";
					weight = 10;
				} else if(i==12) {
					value="Queen";
					weight = 10;
				} else if(i==13) {
					value="King";
					weight= 10;
				} else {
					value = new Integer(i).toString();
					weight = i;
				}
				
				
				
				if(j==1) {
					String suit = "diamonds";
					Card myCard = new Card(value, suit, weight);
					deck.add(myCard);
				}
				if(j==2) {
					String suit = "clubs";
					Card myCard = new Card(value, suit, weight);
					deck.add(myCard);
				}
				if(j==3) {
					String suit = "hearts";
					Card myCard = new Card(value, suit, weight);
					deck.addElement(myCard);
				}
				if(j==4) {
					String suit = "spades";
					Card myCard = new Card(value, suit, weight);
					deck.addElement(myCard);
				}
			}
		}
	}
	
	public void shuffleDeck() {
		if(deck.size() != 52) {
			System.out.println("Playing Deck does not have 52 cards!!");
		}
		
		Vector<Card> shuffledDeck = new Vector<Card>(); 
		
		while(deck.size() > 0) {
			Random rand = new Random(); 
			int value = rand.nextInt(deck.size());
			//System.out.println(value); FOR TESTING ONLY
			shuffledDeck.add(deck.elementAt(value));
			deck.remove(value);	
		}
		
		
		if ((deck.size() == 0)&&(shuffledDeck.size() == 52)) {
			deck = shuffledDeck;
		}else {
			System.out.println("The Deck did not shuffle properly!");
			return;
		}	
	}
	
	public Vector<Card> getDeck(){
		return deck;
	}
}
