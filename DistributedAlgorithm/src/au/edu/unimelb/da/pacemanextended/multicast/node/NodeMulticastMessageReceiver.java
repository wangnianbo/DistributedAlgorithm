package au.edu.unimelb.da.pacemanextended.multicast.node;

import java.net.Socket;
import java.util.List;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.simple.MessageQueue;
import au.edu.unimelb.da.pacemanextended.multicast.simple.ReceiverThread;

public class NodeMulticastMessageReceiver extends MessageReceiver{

	public NodeMulticastMessageReceiver(String playerID,
			List<Socket> serverSocketList) {
		super(playerID, serverSocketList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String backMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
