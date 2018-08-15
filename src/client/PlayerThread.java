package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import gameplay.dealer;
import gameplay.player;
import messages.*;
import server.ServerThread;

public class PlayerThread extends Thread implements Serializable{
	
	//private PrintWriter pw;     //output
	//private BufferedReader br;  //input 
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	player me;
	private boolean hasJoinedGame = false;
	int numPlayersNeeded = 0;
	
	
	public PlayerThread(String hostname, int port) throws IOException, IllegalArgumentException  {
	
			System.out.println("Trying to connect to " + hostname + ":" + port);
			Socket s = new Socket(hostname, port);
			System.out.println("Connected to " + hostname + ":" + port);
			//br = new BufferedReader( new InputStreamReader(s.getInputStream()));
			//pw = new PrintWriter(s.getOutputStream());
		
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			
			this.start();
			Scanner scan = new Scanner(System.in);
			mainMenu();
			
			while(true) { //user input into console, send data out

				//System.out.println(br.readLine());

			}

	}
	
	public boolean getJoinedStatus() {
		return hasJoinedGame;
	}
	
	public void run() {
		
		try {
			while(true) {
				message m = (message)ois.readObject();
				
				if(m instanceof GameOptionMessage) {
					GameOptionMessage gom = GameOptionMessage.class.cast(m);
					if (gom.getGameNameAcceptanceStatus()) { //game name was accepted
						if (gom.getIsNewGame()) { //Starting a Game
							//create a message of type CreateValidGameMessage from GameOptionMenu object
							//can "stupidly" get any username as cretor is guranteed no other existing usernames in game he is creating
							String name = getUserName();
							message newGame = new CreateValidGameMessage(gom.getNumPlayers(), gom.getGameName(), name);
							createValidGameInServer(newGame);
						} else { //Joining A Game 
							//now need to check if user name is valid for the given game
							if (gom.getFirstUsernameAttempt()) { //First time joining user is attempting to enter username
								String name = getUserName();
								gom.setJoiningUserHasEnteredName(true);
								gom.setJoiningUserName(name);
								joinGameCheckUsername(gom);
							} else { //User has already tried entering username 
								if(gom.getUserNameAccepted()) { //username was accepted
									System.out.println("Username was accepted"); //for testing, need to add user to the specified game next
									//public player(String name, String gameName, dealer myDealer, ServerThread mst){
									message addUser = new addUserToGameMessage(gom.getJoiningUserName(), gom.getGameName());
									addUserToExistingGameInServer(addUser);
									
								} else { //username was not accepted
									System.out.println("Invalid choice.  This username has already been chosen by another player in this game");
									String name = getUserName();
									gom.setJoiningUserName(name);
									joinGameCheckUsername(gom);
								}
							}
						}
					}else { //game name was not accepted
						if (gom.getIsNewGame()) { //Starting a new game
							System.out.println("Invalid Choice.  This game name has already been chosen by another user");
							newGameGameName();
						} else { //Joining a game
							System.out.println("Invalid choice.  There are no ongoing games with this name");
							joinGameCheckGameName();
						}
					}
				} else if(m instanceof joinGameWaitingMessage) {
					joinGameWaitingMessage jgwm = joinGameWaitingMessage.class.cast(m);
					System.out.println(jgwm.getMessage());
				} else if (m instanceof GameCreatorWaitMessage) {
					GameCreatorWaitMessage gcwm = GameCreatorWaitMessage.class.cast(m);
					if (gcwm.getPlayerThatJustJoinedIsCreator()) {
						if (gcwm.getNumPlayersWaitingFor() > 1) {
							
							System.out.println("Waiting for "+gcwm.getNumPlayersWaitingFor()+" other players to join...");
						} else if (gcwm.getNumPlayersWaitingFor() == 1) {
							
							System.out.println("Waiting for "+gcwm.getNumPlayersWaitingFor()+" other player to join...");
						}
					} else {
						System.out.println(gcwm.getPlayerThatJustJoined()+" just joined the game");
						if (gcwm.getNumPlayersWaitingFor() > 1) {
							System.out.println("Waiting for "+gcwm.getNumPlayersWaitingFor()+" other players to join...");
						} else if (gcwm.getNumPlayersWaitingFor() == 1) {
						
							System.out.println("Waiting for "+gcwm.getNumPlayersWaitingFor()+" other player to join...");
						}
					}
				} else if (m instanceof GameReadyMessage) {
					GameReadyMessage grm = GameReadyMessage.class.cast(m);
					System.out.println(grm.getMessage());
				} else if(m instanceof dealerShuffleMessage) {
					dealerShuffleMessage dsm = dealerShuffleMessage.class.cast(m);
					System.out.println(dsm.getMessage());
				} else if(m instanceof myBetMessage) {
					myBetMessage mbm = myBetMessage.class.cast(m);
					placeBet(mbm);
					
				} else if(m instanceof whoIsBetting) {
					whoIsBetting nmbm = whoIsBetting.class.cast(m);
					System.out.println("It is " + nmbm.getBetterName()+"'s turn to bet.");
				} else if(m instanceof theirBetAmountMessage) {
					theirBetAmountMessage tbam = theirBetAmountMessage.class.cast(m);
					System.out.println(tbam.getBetter()+ " bet" + tbam.getBetAmount() + "chips.");
				} else if(m instanceof StatusMessage) {
					StatusMessage sm = StatusMessage.class.cast(m);
					displayStatusMessage(sm);
				}
			} 
		}catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	public void displayStatusMessage(StatusMessage sm){
		System.out.println("-----------------------------------------");
		System.out.println("Dealer");
		Vector<String> dealerCards = sm.getDealerCards();
		System.out.println("Cards:  | ? |  | "+dealerCards.get(1)+" |");
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		if (sm.getNumPlayers() ==1) {
			
		}
		
		if (sm.getNumPlayers() ==2) {
			
		}
		
		if (sm.getNumPlayers() == 3) {
			
		}
	}
	
	public void placeBet(myBetMessage mbm) {
		boolean validBet = false;
		int betInt = 0;
		
		while (validBet == false) {
			System.out.println(mbm.getBetterName()+ " it is your turn to bet.  Your chip total is: "+mbm.getCurrentNumChips());
			Scanner scan = new Scanner(System.in);
			String bet = scan.nextLine();
			
			
			try {
				betInt = Integer.parseInt(bet);
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number.");
			} 
			
			if((betInt<=0)||(betInt>mbm.getCurrentNumChips())) {
				System.out.println("Please enter a valid bet.");
			} else {
				validBet = true;
			}
		}
		
		System.out.println("You bet "+betInt+" chips.");
		mbm.setCurrentBet(betInt);
		message mybetmessage = (message) mbm;
		
		try {
			oos.writeObject(mybetmessage);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	


	public void mainMenu() {
		System.out.println("Please choose from the options below...");
		System.out.println("1) Start Game");
		System.out.println("2) Join Game");
		Scanner scan = new Scanner(System.in);
		String menuChoice = scan.nextLine();
		
		int menuChoiceInt = Integer.parseInt(menuChoice);
		
		if (menuChoiceInt == 1) {
			newGameNumPlayers();
		} else if (menuChoiceInt == 2) {
			joinGameCheckGameName();
		} else {
			System.out.println("Please enter a valid option...");
			mainMenu();
		}
	}
	
	private void newGameNumPlayers() {
		boolean validNumPlayers = false;
		Scanner scan = new Scanner(System.in);
		String numPlayers;
		
		while (validNumPlayers == false) {
			System.out.println("Please enter the number of players in your game (1-3)");
			numPlayers = scan.nextLine();
			
			int numPlayersInt = Integer.parseInt(numPlayers);
			
			if ((numPlayersInt > 3) || (numPlayersInt < 1)) {
				System.out.println("Please enter a valid number of players");
				
			} else {
				validNumPlayers = true;
				numPlayersNeeded(numPlayersInt);
				newGameGameName();
			}
		}
	}
	
	private void newGameGameName() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please choose a name for your game");
		String teamName = scan.nextLine();
		
		//create new message object to be serialized and sent to gameroom to check for validity
		GameOptionMessage mgom = new GameOptionMessage(teamName, true);
		mgom.setNumPlayers(numPlayersNeeded);
		message myTeamName = (message) mgom;
		
		try {
			oos.writeObject(myTeamName);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void createValidGameInServer(message m) {
		//need total number of players
		//need this players username
		//need to wait for everyone to join
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getUserName() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please choose a username");
		String username = scan.nextLine();
		return username;
	}
	
	private void joinGameCheckGameName() {  //checks to see if the game joining user attempting to join exists
		System.out.println("Please enter the name of the game you wish to join...");
		Scanner scan = new Scanner(System.in);
		String gameNameToJoin = scan.nextLine();
		message gameToJoin = new GameOptionMessage(gameNameToJoin, false);
		try {
			oos.writeObject(gameToJoin);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void joinGameCheckUsername(GameOptionMessage gom) {
		message gameToJoin_CheckUsername = (message) gom;
		try {
			oos.writeObject(gameToJoin_CheckUsername);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addUserToExistingGameInServer(message m) {
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Setter for number of players in game (create or join)
	private void numPlayersNeeded(int numPlayersNeeded) {
		this.numPlayersNeeded = numPlayersNeeded;
	}
	
	public static void connect () {
		String portNumberString = null;
		String ipAddressString = null;
		
		System.out.println("Please Enter the port...");
		Scanner scanner = new Scanner(System.in);
		portNumberString = scanner.nextLine();
		
		System.out.println("Please Enter the ipaddress...");
		ipAddressString = scanner.nextLine();	
			
			try {
				
				int portNumberInt = Integer.parseInt(portNumberString);
				PlayerThread pt = new PlayerThread (ipAddressString, portNumberInt);
				
			} catch (IOException ioe) {
				System.out.println("ioeexceptions"+ ioe.getMessage());
				connect();
			} catch (IllegalArgumentException iae) {
				System.out.println("illegalargumentexceptions" + iae.getMessage());
				connect();
			} 
}

	
	
	public static void main(String [] args) {
		System.out.println("Welcome to Black Jack");
		connect();
	}
}
