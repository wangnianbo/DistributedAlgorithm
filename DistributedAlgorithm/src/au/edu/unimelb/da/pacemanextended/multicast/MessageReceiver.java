package au.edu.unimelb.da.pacemanextended.multicast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import au.edu.unimelb.da.pacemanextended.Pacman;

public abstract class MessageReceiver{
	
	//string is playerID like player1, player2
	private String playerID;
	
	//string is playerID like player1, player2
	private List<ServerSocket> serverSocketList; 
	
	public abstract String backMessage();
	
	public abstract boolean isNext();

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public List<ServerSocket> getServerSocketList() {
		return serverSocketList;
	}

	public void setServerSocketList(List<ServerSocket> serverSocketList) {
		this.serverSocketList = serverSocketList;
	}
	
	
		

}
