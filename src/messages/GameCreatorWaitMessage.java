package messages;

public class GameCreatorWaitMessage implements message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8407070832191559650L;
	int numPlayersWaitingFor;
	String playerThatJustJoined;
	boolean playerThatJustJoinedIsCreator = false;
	
	public GameCreatorWaitMessage(int numPlayersWaitingFor, String playerThatJustJoined){
		this.numPlayersWaitingFor = numPlayersWaitingFor;
		this.playerThatJustJoined = playerThatJustJoined;
	}
	
	public void setPlayerThatJustJoinedIsCreator() {
		playerThatJustJoinedIsCreator = true;
	}
	
	public int getNumPlayersWaitingFor() {
		return numPlayersWaitingFor;
	}
	
	public String getPlayerThatJustJoined() {
		return playerThatJustJoined;
	}
	
	public boolean getPlayerThatJustJoinedIsCreator() {
		return playerThatJustJoinedIsCreator;
	}
	
	
}
