package messages;

public class CreateValidGameMessage implements message{
	//need number of players
	//need name of the game 
	//who created this game? - maybe implement later!!
	

	private static final long serialVersionUID = -5491125604533700090L;
	int numPlayers;
	String gameName;
	String creatorPlayerName;
	
	public CreateValidGameMessage(int numPlayers, String gameName, String creatorPlayerName){
		this.numPlayers = numPlayers;
		this.gameName = gameName;
		this.creatorPlayerName = creatorPlayerName;
	}
	
	public String getGameName() {
		return gameName;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	public String getCreatorPlayerName() {
		return creatorPlayerName;
	}
}
