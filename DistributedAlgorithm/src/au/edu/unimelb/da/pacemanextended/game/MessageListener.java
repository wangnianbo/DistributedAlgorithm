package au.edu.unimelb.da.pacemanextended.game;

import java.util.LinkedList;
import java.util.Queue;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;

public class MessageListener extends Thread {
	private Queue<String> msgQueue = new LinkedList<String>();
	MessageReceiver messageReceiver;

	public MessageListener(MessageReceiver messageReceiver) {
		this.messageReceiver = messageReceiver;
	}

	public boolean hasMsg() {
		return (!msgQueue.isEmpty());
	}

	public String getMsg() {
		return msgQueue.poll();
	}

	public void run() {
		while (true) {
			msgQueue.add(messageReceiver.backMessage());
		}

		///f
	}

}
