package au.edu.unimelb.da.pacemanextended.multicast;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class MessageReceiver{
	
	//string is playerID like player1, player2
	private String playerID;
	
	//string is playerID like player1, player2
	private List<Socket> serverSocketList = new ArrayList<Socket>(); 
	
	public abstract String backMessage();
	
	public abstract boolean isNext();

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public List<Socket> getServerSocketList() {
		return serverSocketList;
	}

	public void addServerSocket(Socket serverSocket) {
		this.serverSocketList.add(serverSocket);
	}
	
	
		

}
