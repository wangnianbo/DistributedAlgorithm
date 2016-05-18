package au.edu.unimelb.da.pacemanextended.multicast;


public abstract class MessageCenter {
	
	public MessageReceiver messageReceiver;
	public MessageSender messageSender;

	public MessageCenter (MessageReceiver messageReceiver, MessageSender messageSender) {
		this.messageReceiver = messageReceiver;
		this.messageSender = messageSender;
	}
	
	public abstract String backMessage();
	

	public abstract boolean putMessage(String playerID, String jsonMsg);
	
	public abstract boolean putMessage(String jsonMsg);
	
	
	
	
}
