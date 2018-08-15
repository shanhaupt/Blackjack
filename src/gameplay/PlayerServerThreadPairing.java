package gameplay;

import server.ServerThread;

public class PlayerServerThreadPairing {
	
	ServerThread playerThread;
	player playerObject;
	
	public PlayerServerThreadPairing(ServerThread st, player po){
		this.playerThread = st;
		this.playerObject = po;
	}
	
	public ServerThread getPlayerThread() {
		return playerThread;
	}
	
	public player getPlayerObject() {
		return playerObject;
	}
	
	
}
