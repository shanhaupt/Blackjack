package gameplay;

import java.util.Vector;

import cards.Card;
import cards.Deck;
import messages.GameCreatorWaitMessage;
import messages.GameReadyMessage;
import messages.StatusMessage;
import messages.dealerShuffleMessage;
import messages.myBetMessage;
import messages.whoIsBetting;
import messages.message;
import server.ServerThread;

public class game {
	
	
	/*private Vector<ServerThread> playersThreads;
	private ServerThread creatorServerThread;
	private Vector<player> playerObjects;*/
	
	
	private player creatorPlayer;
	private dealer myDealer;
	
	private String gameName;
	private Vector<PlayerServerThreadPairing> myPlayerPairings = new Vector<PlayerServerThreadPairing>();
	private int currentNumPlayers = 0;
	private int numPlayersNeeded;
	
	
	public game(String gameName, int numPlayersNeeded, ServerThread creatorServerThread, String creatorName){
		this.gameName = gameName;
		this.numPlayersNeeded = numPlayersNeeded;
		this.myDealer = new dealer();
		
		//create the player object for the "creator" of the specified game
		this.creatorPlayer = new player(creatorName, gameName, myDealer, creatorServerThread);
		creatorPlayer.setAsCreator();
		PlayerServerThreadPairing myPairing = new PlayerServerThreadPairing(creatorServerThread, creatorPlayer);
		myPlayerPairings.add(myPairing);
		currentNumPlayers+=1;
		checkReadyStatus(myPairing);
		System.out.println("Added "+creatorPlayer.getName() + "to the game " + this.gameName + "that needs " + this.numPlayersNeeded+" players.");
	}
	
	private void newRound() {
		boolean gameInProgress = true;
		for(int i=0; i<myPlayerPairings.size(); i++) {
			if(myPlayerPairings.get(i).getPlayerObject().getNumChips() <= 0) {
				gameInProgress = false;
			}
		}
		
		if(gameInProgress == true) {
			for(int i=0; i<myPlayerPairings.size(); i++) {
				myPlayerPairings.get(i).getPlayerObject().setCurrentBet(0);
				myPlayerPairings.get(i).getPlayerObject().clearCards();
			}
			myDealer.clearCards();
			myDealer.newDeck();
			dealerShuffleCards();
			makeBetsHelper();
			
		} else {
			
		}
	}
	
	public void test() {
		for (int i=0; i<myPlayerPairings.size(); i++) {
			System.out.println(myPlayerPairings.get(i).getPlayerObject().getCurrentBet());
		}
	}
	
	public void dealCards() {
		for (int i=0; i<myPlayerPairings.size(); i++) {
			myPlayerPairings.get(i).getPlayerObject().hit();
			myPlayerPairings.get(i).getPlayerObject().hit();
		}
		//myDealer.hitDealer();
		//myDealer.hitDealer();
		//writeStatus();
	}
	
	public void writeStatus(){
		
		StatusMessage sm = new StatusMessage();
		
		Vector<Card> temp = myDealer.getDealerCards();
		Vector<String> dealerCards = null;
		Vector<String> playerNames = null;
		
		for (int i = 0; i<=temp.size(); i++) {
			dealerCards.add(temp.get(i).getValue()+" of " + temp.get(i).getSuit());
		}
		
		sm.setDealerCards(dealerCards);
		
		for (int i=0; i<myPlayerPairings.size(); i++) {
			if (i ==0) {
				Vector<Integer> player1Values = myPlayerPairings.get(i).getPlayerObject().cardTotal();
				
				Vector <Card> player1CardObjextsTemp = myPlayerPairings.get(i).getPlayerObject().getCards();
				Vector <String> player1Cards = null;
				for (int j = 0; j<=player1CardObjextsTemp.size(); j++) {
					player1Cards.add(player1CardObjextsTemp.get(j).getValue()+" of " + player1CardObjextsTemp.get(j).getSuit());
				}
				
				
				playerNames.add(myPlayerPairings.get(i).getPlayerObject().getName());
				sm.setNumPlayers(1);
				sm.setPlayer1Values(player1Values);
				sm.setPlayer1Cards(player1Cards);
				sm.setPlayer1Chips(myPlayerPairings.get(i).getPlayerObject().getNumChips());
				sm.setPlayer1Bet(myPlayerPairings.get(i).getPlayerObject().getCurrentBet());
				
			} else if(i ==1) {
				Vector<Integer> player2Values = myPlayerPairings.get(i).getPlayerObject().cardTotal();
				
				Vector <Card> player2CardObjextsTemp = myPlayerPairings.get(i).getPlayerObject().getCards();
				Vector <String> player2Cards = null;
				for (int j = 0; j<=player2CardObjextsTemp.size(); j++) {
					player2Cards.add(player2CardObjextsTemp.get(j).getValue()+" of " + player2CardObjextsTemp.get(j).getSuit());
				}
				
				
				
				
				
				playerNames.add(myPlayerPairings.get(i).getPlayerObject().getName());
				sm.setNumPlayers(2);
				sm.setPlayer2Values(player2Values);
				sm.setPlayer2Cards(player2Cards);
				sm.setPlayer2Chips(myPlayerPairings.get(i).getPlayerObject().getNumChips());
				sm.setPlayer2Bet(myPlayerPairings.get(i).getPlayerObject().getCurrentBet());
				
			} else if (i ==2) {
				Vector<Integer> player3Values = myPlayerPairings.get(i).getPlayerObject().cardTotal();
				
				Vector <Card> player3CardObjextsTemp = myPlayerPairings.get(i).getPlayerObject().getCards();
				Vector <String> player3Cards = null;
				for (int j = 0; j<=player3CardObjextsTemp.size(); j++) {
					player3Cards.add(player3CardObjextsTemp.get(j).getValue()+" of " + player3CardObjextsTemp.get(j).getSuit());
				}
				
				
				
				playerNames.add(myPlayerPairings.get(i).getPlayerObject().getName());
				sm.setNumPlayers(3);
				sm.setPlayer3Values(player3Values);
				sm.setPlayer3Cards(player3Cards);
				sm.setPlayer3Chips(myPlayerPairings.get(i).getPlayerObject().getNumChips());
				sm.setPlayer3Bet(myPlayerPairings.get(i).getPlayerObject().getCurrentBet());
				
			}	
		}
		
		for (int i=0; i<myPlayerPairings.size(); i++) {
			myPlayerPairings.get(i).getPlayerThread().brodcastStatusMessage(sm);
		}
	}
	
	public void makeBetsHelper() {
		
		if(myPlayerPairings.size() ==1) {
			makeBetsOnePlayer();
		}
		if(myPlayerPairings.size() ==2) {
			makeBetsTwoPlayer();
		}
		if(myPlayerPairings.size() ==3) {
			makeBetsThreePlayer();
		}
		
	}
	
	private void makeBetsOnePlayer() {
		if(myPlayerPairings.get(0).getPlayerObject().getCurrentBet() == 0) {
			player temp = myPlayerPairings.get(0).getPlayerObject();
			myBetMessage mbm = new myBetMessage(temp.getNumChips(), myPlayerPairings.get(0).getPlayerThread(), temp.getName(), 0, 1);
			whoIsBetting nmbm = new whoIsBetting(temp.getName());
			
			myPlayerPairings.get(0).getPlayerThread().brodcastMyBetMessage(mbm);
			
		} else {
			dealCards();
		}
		
		
	}
	
	private void makeBetsTwoPlayer() {
		if(myPlayerPairings.get(0).getPlayerObject().getCurrentBet() == 0) {
			player temp = myPlayerPairings.get(0).getPlayerObject();
			myBetMessage mbm = new myBetMessage(temp.getNumChips(), myPlayerPairings.get(0).getPlayerThread(), temp.getName(), 0, 2);
			whoIsBetting nmbm = new whoIsBetting(temp.getName());
			
			myPlayerPairings.get(0).getPlayerThread().brodcastMyBetMessage(mbm);
			myPlayerPairings.get(1).getPlayerThread().brodcastWhoIsBetting(nmbm);
			
			
		} else if(myPlayerPairings.get(1).getPlayerObject().getCurrentBet() == 0) {
			player temp = myPlayerPairings.get(1).getPlayerObject();
			
			myBetMessage mbm = new myBetMessage(temp.getNumChips(), myPlayerPairings.get(1).getPlayerThread(), temp.getName(), 1, 2);
			whoIsBetting nmbm = new whoIsBetting(temp.getName());
			
			myPlayerPairings.get(1).getPlayerThread().brodcastMyBetMessage(mbm);
			myPlayerPairings.get(0).getPlayerThread().brodcastWhoIsBetting(nmbm);
		} else {
			dealCards();
		}
		
	}

	private void makeBetsThreePlayer() {
		
		if(myPlayerPairings.get(0).getPlayerObject().getCurrentBet() == 0) {
			player temp = myPlayerPairings.get(0).getPlayerObject();
			myBetMessage mbm = new myBetMessage(temp.getNumChips(), myPlayerPairings.get(0).getPlayerThread(), temp.getName(), 0, 3);
			whoIsBetting nmbm = new whoIsBetting(temp.getName());
			
			myPlayerPairings.get(0).getPlayerThread().brodcastMyBetMessage(mbm);
			myPlayerPairings.get(1).getPlayerThread().brodcastWhoIsBetting(nmbm);
			myPlayerPairings.get(2).getPlayerThread().brodcastWhoIsBetting(nmbm);
			
		} else if(myPlayerPairings.get(1).getPlayerObject().getCurrentBet() == 0) {
			player temp = myPlayerPairings.get(1).getPlayerObject();
			
			myBetMessage mbm = new myBetMessage(temp.getNumChips(), myPlayerPairings.get(1).getPlayerThread(), temp.getName(), 1, 3);
			whoIsBetting nmbm = new whoIsBetting(temp.getName());
			
			myPlayerPairings.get(1).getPlayerThread().brodcastMyBetMessage(mbm);
			myPlayerPairings.get(0).getPlayerThread().brodcastWhoIsBetting(nmbm);
			myPlayerPairings.get(2).getPlayerThread().brodcastWhoIsBetting(nmbm);
			
			
		} else if(myPlayerPairings.get(2).getPlayerObject().getCurrentBet() == 0) {
			player temp = myPlayerPairings.get(2).getPlayerObject();
			
			myBetMessage mbm = new myBetMessage(temp.getNumChips(), myPlayerPairings.get(2).getPlayerThread(), temp.getName(), 2, 3);
			whoIsBetting nmbm = new whoIsBetting(temp.getName());
			
			myPlayerPairings.get(2).getPlayerThread().brodcastMyBetMessage(mbm);
			myPlayerPairings.get(0).getPlayerThread().brodcastWhoIsBetting(nmbm);
			myPlayerPairings.get(1).getPlayerThread().brodcastWhoIsBetting(nmbm);
			
		} else {
			dealCards();
		}
		
	}
	
			
			

	
	private void dealerShuffleCards(){
		myDealer.shuffleDeck();
		dealerShuffleMessage dsm = new dealerShuffleMessage();
		for(int i=0; i<myPlayerPairings.size(); i++) {
			myPlayerPairings.get(i).getPlayerThread().brodcastDealerShuffleMessage(dsm);
		}
	}
	
	private void startGame(){
		for(int i=0; i<myPlayerPairings.size(); i++) {
			myPlayerPairings.get(i).getPlayerThread().setGame(this);
		}
		newRound();
	}
	
	public void createPlayer(String gameName, String playerName, ServerThread st) {
		player myPlayer = new player(playerName, gameName, myDealer, st);
		PlayerServerThreadPairing myPairing = new PlayerServerThreadPairing(st, myPlayer);
		myPlayerPairings.add(myPairing);
		currentNumPlayers+=1;
		checkReadyStatus(myPairing);
		
		//for debug purposes to make sure there is no missing logic later on in the game!
		if(currentNumPlayers > numPlayersNeeded) {
			System.out.println("THERE ARE TOO MANY PLAYERS IN THIS GAME!!");
		}
	}
	
	public void checkReadyStatus(PlayerServerThreadPairing myPairing) {
		
		GameCreatorWaitMessage gcwm = new GameCreatorWaitMessage(numPlayersNeeded-currentNumPlayers, myPairing.getPlayerObject().getName());
	
		
		
		if(myPairing.getPlayerObject().isCreator) {
			gcwm.setPlayerThatJustJoinedIsCreator();
		}
			
		creatorPlayer.getServerThread().brodcastGameCreatorWaitMessage(gcwm);
			
		if(currentNumPlayers == numPlayersNeeded) {
			GameReadyMessage grm = new GameReadyMessage();
			for (int i=0; i<myPlayerPairings.size(); i++) {
				myPlayerPairings.get(i).getPlayerThread().brodcastGameReadyMessage(grm);
			}
			startGame();
		}	
	}
	
	
	
	public String getGameName() {
		return gameName;
	}
	
	public int getNumPlayersNeeded() {
		return numPlayersNeeded;
	}
	
	public int currentNumPlayers() {
		if (currentNumPlayers != myPlayerPairings.size()) {
			System.out.println("NumPLayers Counter and myPlayerPairings size do not match!");
			return -1;
		}
		else {
			return myPlayerPairings.size();
		}
	}
	
	public Vector<PlayerServerThreadPairing> getPlayerPairings(){
		return myPlayerPairings;
	}
	
}
