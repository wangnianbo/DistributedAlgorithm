/*
 * used as time out clock
 */
package au.edu.unimelb.da.pacemanextended.game;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class Time_out extends Thread {

	private Node node;

	MessageReceiver messageReceiver;
	MessageSender messageSender;

	public Time_out(Node node, MessageReceiver messageReceiver,
			MessageSender messageSender) {
		this.node = node;
		this.messageReceiver = messageReceiver;
		this.messageSender = messageSender;
	}

	// body of thread
	public synchronized void run() {
		while (true) {
			this.node.signal = false;
			try {
				synchronized (node) {
					// wait random time
					node.wait(this.node.Election_timeout());
					// decide if reset the clock
					if (node.reset == true) {
						node.reset = false;
						continue;
					} else {
						this.node.signal = true;
						this.node.notifyAll();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}