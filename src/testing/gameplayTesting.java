package testing;

import gameplay.*;

public class gameplayTesting {
	
	
	gameplayTesting(){
		
	}
	
	public static void main(String [] args) {
		dealer dealer1 = new dealer();
		player player1 = new player("Shan", dealer1);
		
		
		for(int i=0; i<5; i++) {
			player1.hit();
		}
		
		player1.testing();
		if (player1.checkBust(player1.cardTotal()) == true) {
			System.out.println("The player has bust!");
		} else {
			System.out.println("The player has not bust!");
		}
		
	}
	
	
	
}
