package au.edu.unimelb.da.pacemanextended.game;
import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class Time_out extends Thread {
	
    private Node node;
    
    MessageReceiver messageReceiver;
	MessageSender messageSender;
	public Time_out (Node node, MessageReceiver messageReceiver,
			MessageSender messageSender) {
		this.node = node;
		this.messageReceiver = messageReceiver;
		this.messageSender = messageSender;
	}
    
	public synchronized void run() {
		while (true) {
			this.node.signal = false;
			try {
				synchronized (node) {
					node.wait(this.node.Election_timeout());
					if(node.reset == true)
					{
						node.reset = false;
						continue;
					}			
					else
					{
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