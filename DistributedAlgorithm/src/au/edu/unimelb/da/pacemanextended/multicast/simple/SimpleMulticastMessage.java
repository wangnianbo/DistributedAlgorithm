package au.edu.unimelb.da.pacemanextended.multicast.simple;

import au.edu.unimelb.da.pacemanextended.multicast.MessageCenter;
import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;

public class SimpleMulticastMessage extends MessageCenter{

	public SimpleMulticastMessage(MessageReceiver messageReceiver,
			MessageSender messageSender) {
		super(messageReceiver, messageSender);
	}

	@Override
	public String backMessage() {
		return super.messageReceiver.backMessage();
	}

	@Override
	public boolean putMessage(String playerID, String jsonMsg) {
		return super.messageSender.putMessage(playerID, jsonMsg);
	}

	@Override
	public boolean putMessage(String jsonMsg) {
		return super.messageSender.putMessage(jsonMsg);
	}
	
	

}
