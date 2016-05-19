package au.edu.unimelb.da.pacemanextended.multicast.simple;

import java.net.Socket;
import java.util.List;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.simple.MessageQueue;
import au.edu.unimelb.da.pacemanextended.multicast.simple.ReceiverThread;

/**
 * Simple Multicast Message Receiver
 * @author Bill
 *
 */
public class SimpleMulticastMessageReceiver extends MessageReceiver{
	
	

	MessageQueue messageQueue = new MessageQueue();
	
	public SimpleMulticastMessageReceiver(String playerID,
			List<Socket> serverSocketList) {
		
		
		super(playerID, serverSocketList);
		
		for (int i = 0; i < serverSocketList.size(); i++) {
			ReceiverThread receiverThread = new ReceiverThread(i+1, serverSocketList.get(i), messageQueue);
			receiverThread.start();
		}
	}

	@Override
	public String backMessage() {
		return messageQueue.get();
	}

	
	public static void main(String[] args) {
	}

}
