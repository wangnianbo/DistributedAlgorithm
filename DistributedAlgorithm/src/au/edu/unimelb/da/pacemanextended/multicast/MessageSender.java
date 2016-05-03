package au.edu.unimelb.da.pacemanextended.multicast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Properties;

public abstract class MessageSender{
	
	//string is playerID like player1, player2
	private String playerID;
	
	//string is playerID like player1, player2
	private Map<String, Socket> sendSocketList; 
	
	public abstract boolean putMessage(String jsonMsg);

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public Map<String, Socket> getSendSocketList() {
		return sendSocketList;
	}

	public void setSendSocketList(Map<String, Socket> sendSocketList) {
		this.sendSocketList = sendSocketList;
	}
	
	
		
	

}
