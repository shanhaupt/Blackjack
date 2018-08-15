package messages;

public class addUserToGameMessage implements message{
	
	private static final long serialVersionUID = -5730967025790518416L;
	String userName;
	String gameName;
	
	public addUserToGameMessage(String userName, String gameName){
		this.userName = userName;
		this.gameName = gameName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getGameName() {
		return gameName;
	}
}
