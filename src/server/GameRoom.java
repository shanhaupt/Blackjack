package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import gameplay.PlayerServerThreadPairing;
import gameplay.game;
import messages.CreateValidGameMessage;
import messages.GameOptionMessage;
import messages.addUserToGameMessage;
import messages.joinGameWaitingMessage;

public class GameRoom {

	
	private Vector<ServerThread> serverThreads = new Vector<ServerThread>();
	private Vector<game> games = new Vector<game>();
	
	
	
	
	public GameRoom(int port) throws IOException, IllegalArgumentException {
			System.out.println("Trying to bind to port " + port);
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			
			

			while(true) {
				Socket s = ss.accept();
				System.out.println("Accepted connection from " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
			}
	}
	
	//Will create a new game if requested by the user through the ServerThread
	public void createGame(CreateValidGameMessage cvgm, ServerThread st) {
		game newGame = new game(cvgm.getGameName(), cvgm.getNumPlayers(), st, cvgm.getCreatorPlayerName());
		games.add(newGame);
		
	}
	
	public void addUserToGame(addUserToGameMessage autgm, ServerThread st) {
		
		for (int i=0; i<games.size(); i++) { //find the game
			if (games.get(i).getGameName().equals(autgm.getGameName())) {
				joinGameWaitingMessage jgwm = new joinGameWaitingMessage();
				st.brodcastJoinGameWaitingMessage(jgwm);
				games.get(i).createPlayer(autgm.getGameName(), autgm.getUserName(), st);
				
			}
		}
	}
	
	public boolean isValidGame(GameOptionMessage gom, ServerThread st) {
		/*
		 FOR TESTING PURPOSES ONLY
		 
		 games.add(new game("names"));
		 
		 END FOR TESTING PURPOSES ONLY
		 */
		boolean foundGame = false;
		game gameToCheck = null;
		for (int i=0; i<games.size(); i++) {
			if (games.get(i).getGameName().equals(gom.getGameName())){
				foundGame = true;
				gameToCheck = games.get(i);
				
				//FIGURE OUT HOW AND WHERE TO ADD THIS IN LATER!! - out for now for testing purposes
				/*if (games.get(i).getNumPlayersNeeded() > games.get(i).currentNumPlayers()) {
					foundGame = true;
				}*/ 
			}
		}
		
		if (foundGame == true) {
			if (gom.getIsNewGame()){ //new game name already exists
				//Set Game Name Acceptance Status of the GameMessage
				gom.setGameNameAcceptanceStatus(false);
				st.brodcastGameOptionMessage(gom);
				
			} else { //game attempting to join exists
				if(gom.getJoiningUserHasEnteredName() == false) {  //user has not yet entered userneame in console
					if(gameToCheck.currentNumPlayers() >= gameToCheck.getNumPlayersNeeded()) { //check to make sure game attempting to join isnt already running
						gom.setGameNameAcceptanceStatus(false);
					} else {
						gom.setGameNameAcceptanceStatus(true); //tell user that game is valid, then ask user for username
					}
					
					st.brodcastGameOptionMessage(gom);
				} else {  // joining user has already entered name in console 
					gom.setFirstUsernameAttempt(false);
					Vector<PlayerServerThreadPairing> myPairings = gameToCheck.getPlayerPairings();
					
					gom.setUserNameAccepted(true);
					for(int i = 0; i < myPairings.size(); i++) { //check game to see if username already exists
						if (myPairings.get(i).getPlayerObject().getName().equals(gom.getJoiningUserName())){ // username already exists in the game
							gom.setUserNameAccepted(false);
						} 
					}
					st.brodcastGameOptionMessage(gom);
				}
			}
		} else {
			
			if (gom.getIsNewGame()) { //new game name does not exist
				//Set Game Name Acceptance Status of the GameMessage
				gom.setGameNameAcceptanceStatus(true);
				st.brodcastGameOptionMessage(gom);
			} else { //game attempting to join does not exist
				gom.setGameNameAcceptanceStatus(false);
				st.brodcastGameOptionMessage(gom);
			}
		}
		return foundGame;
	}
	
	
	/*public void broadcast(String message, ServerThread st) {
		if (message != null) {
			System.out.println(message);
			for(ServerThread thread : serverThreads) {
				if (thread != st) {
					thread.sendMessage(message);
				}
			}
		}
	}*/
	
	public static void enterPort() {
	
		String portNumberString = null;
		
		try{
			System.out.println("Please Enter a port...");
			
			Scanner scanner = new Scanner(System.in);
			portNumberString = scanner.nextLine();
			int portNumberInt = Integer.parseInt(portNumberString);
			System.out.println(portNumberInt);
			
			GameRoom gr = new GameRoom(portNumberInt);
			
		} catch (IOException ioe ) {
			//System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
			System.out.println("Invalid Port Number");
			enterPort();
		} catch (IllegalArgumentException iae) {
			//System.out.println("iae in ChatRoom constructor: " + iae.getMessage());
			System.out.println("Invalid Port Number");
			enterPort();
		}
		
		System.out.println("Sucessfully started the Black Jack server on port "+ portNumberString);
	}
	
	
	
	public static void main (String[] args) {
		System.out.println("Welcome to the Black Jack Server!");
		enterPort();
		
		
	
	}
	
	
}
