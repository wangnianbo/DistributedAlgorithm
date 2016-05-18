package au.edu.unimelb.da.pacemanextended.multicast.node;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import au.edu.unimelb.da.pacemanextended.multicast.simple.SimpleMulticastMessageSender;

public class NodeMulticastMessageSender extends SimpleMulticastMessageSender {

	public NodeMulticastMessageSender(String playerID,
			Map<String, Socket> sendSocketMap) {
		super(playerID, sendSocketMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean putMessage(String jsonMsg) {
		return super.putMessage(jsonMsg);
	}

	@Override
	public boolean putMessage(String playerID, String jsonMsg) {
		// TODO Auto-generated method stub
		return super.putMessage(playerID, jsonMsg);
	}
	
}
