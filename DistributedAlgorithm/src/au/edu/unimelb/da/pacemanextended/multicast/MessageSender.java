package au.edu.unimelb.da.pacemanextended.multicast;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public abstract class MessageSender{
	
	//string is playerID like player1, player2
	private String playerID;
	
	//string is playerID like player1, player2
	private Map<String, Socket> sendSocketMap = new HashMap<String, Socket>(); 
	
	public abstract boolean putMessage(String jsonMsg);

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public Map<String, Socket> getSendSocketMap() {
		return sendSocketMap;
	}

	public void setSendSocket(String playerID, Socket socket) {
		this.sendSocketMap.put(this.playerID, socket);
	}
	
	
		
	

}
