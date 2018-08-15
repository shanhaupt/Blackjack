package cards;

public class Card {
	
	private String value;
	private String suit;
	private int weight;
	private boolean hasBeenDrawn = false;
	
	Card (String val, String suit, int weight){
		this.value = val;
		this.suit = suit;
		this.weight = weight;
	}
	
	void drawCard() {
		hasBeenDrawn = true;
	}
	
	public String getValue(){
		return value;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public int getWeight() {
		return weight;
	}
}

