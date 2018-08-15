package messages;

public class GameOptionMessage implements message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6007539466359017224L;
	String gameName;
	String joiningUserName;
	boolean isNewGame;
	boolean gameNameWasAccepted;
	int numPlayers;
	
	//variables for player trying to join a game
	boolean joiningUserHasEnteredName = false;
	boolean userNameAccepted = false;
	boolean firstUsernameAttempt = true;
	
	public GameOptionMessage(String gameName, boolean isNewGame){
		this.gameName = gameName;
		this.isNewGame = isNewGame;
		
	}
	
	//getters
	
	
	public String getGameName() {
		return gameName;
	}
	
	public boolean getIsNewGame() {
		return isNewGame;
	}
	
	public boolean getGameNameAcceptanceStatus() {
		return gameNameWasAccepted;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	public String getJoiningUserName() {
		return joiningUserName;
	}
	
	public boolean getUserNameAccepted() {
		return userNameAccepted;
	}
	
	public boolean getFirstUsernameAttempt() {
		return firstUsernameAttempt;
	}
	
	public boolean getJoiningUserHasEnteredName() {
		return joiningUserHasEnteredName;
	}
	

	
	//setters
	
	public void setGameNameAcceptanceStatus(boolean wasAccepted) {
		this.gameNameWasAccepted = wasAccepted;
	}
	
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public void setJoiningUserName(String username) {
		this.joiningUserName = username;
	}
	
	public void setUserNameAccepted (boolean b) {
		this.userNameAccepted = b;
	}
	
	
	public void setFirstUsernameAttempt (boolean b) {
		this.firstUsernameAttempt = b;
	}
	
	public void setJoiningUserHasEnteredName(boolean b) {
		this.joiningUserHasEnteredName = b;
	}
	
	
}
