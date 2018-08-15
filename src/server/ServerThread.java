package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import gameplay.PlayerServerThreadPairing;
import gameplay.game;
import messages.*;

public class ServerThread extends Thread {
	//when inheriting from thread, we need to override run 
	//private BufferedReader br;
	//private PrintWriter pw;
	private GameRoom gr;
	private game g;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private boolean newUser = true;
	
	public ServerThread(Socket s, GameRoom gr) {
		try {
			this.gr = gr;
			//br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			//pw = new PrintWriter(s.getOutputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	} 
	
//	public void sendMessage(String message) {
//		pw.println(message);
//		pw.flush();
//	}
	
	public void setGame(game g) {
		this.g = g;
	}
	
	public void brodcastStatusMessage(StatusMessage sm) {
		message m = (message) sm;
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void brodcastMyBetMessage(myBetMessage mbm) {
		message m = (message) mbm;
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void brodcastWhoIsBetting(whoIsBetting nmbm) {
		message m = (message) nmbm;
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void brodcastThierBetAmount(theirBetAmountMessage tbam) {
		message m = (message) tbam;
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void brodcastGameOptionMessage(GameOptionMessage gom) {
		//Upcast GameOptionMessage to parent message inorder to 
		//ensure PLayerThread can recieve the message
		message m = (message) gom;
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void brodcastJoinGameWaitingMessage(joinGameWaitingMessage jgwm) {
		message m = (message) jgwm;
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void brodcastGameCreatorWaitMessage(GameCreatorWaitMessage gcwm) {
		message m = (message) gcwm;
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void brodcastGameReadyMessage(GameReadyMessage grm) {
		message m = (message) grm;
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void brodcastDealerShuffleMessage(dealerShuffleMessage dsm) {
		message m = (message) dsm;
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		try {
			while(true) {
				message m = (message)ois.readObject();
				
				if(m instanceof GameOptionMessage) {
					GameOptionMessage gom = GameOptionMessage.class.cast(m);
					gr.isValidGame(gom, this);
					
				} else if(m instanceof CreateValidGameMessage) {
					CreateValidGameMessage gvgm = CreateValidGameMessage.class.cast(m);
					//System.out.println("THISRAN");
					gr.createGame(gvgm, this);
				} else if(m instanceof addUserToGameMessage) {
					addUserToGameMessage autgm = addUserToGameMessage.class.cast(m);
					gr.addUserToGame(autgm, this);
				} else if(m instanceof myBetMessage) {
					myBetMessage mbm = myBetMessage.class.cast(m);
					theirBetAmountMessage tbam = new theirBetAmountMessage(mbm.getBetAmount(), mbm.getBetterName());
					Vector<PlayerServerThreadPairing> temp = g.getPlayerPairings();
					
					for(int i=0; i<temp.size(); i++) {
						if (i == mbm.getPosition()) {
							g.getPlayerPairings().get(i).getPlayerObject().setCurrentBet(mbm.getBetAmount());
							//temp.get(i).getPlayerObject().setCurrentBet(mbm.getBetAmount());
						} else {
							//public void brodcastThierBetAmount(theirBetAmountMessage tbam) {
							temp.get(i).getPlayerThread().brodcastThierBetAmount(tbam);
						}
					}
					g.makeBetsHelper();
				}
			} 
		}catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	
}
		
		
		
	/*
	 * while(true) {
	
		
		String gameName = "FAKEGAMENAME";
		
		try {
			
			if (ois) {
				System.out.println("yp1");
				gameName = br.readLine();
				System.out.println(gameName);
				System.out.println("yp2");
				System.out.println(gr.isValidGame(gameName, this));
				System.out.println("yp3");
				String validity;
				if(gr.isValidGame(gameName, this) == true) {
					validity = "true";
				}else {
					validity = "false";
				}
				System.out.println(validity);
				
				pw.write(validity);
				pw.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			System.out.println("HELLO");
			gameName = br.readLine();
			System.out.println("HELLO");
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	*/
			
	


