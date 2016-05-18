package au.edu.unimelb.da.pacemanextended.game;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class Insert_thread extends Thread{

	private Node node;
	
	MessageReceiver messageReceiver;
	MessageSender messageSender;

	public Insert_thread(Node node, MessageReceiver messageReceiver,
			MessageSender messageSender) {
		this.node = node;
		this.messageReceiver = messageReceiver;
		this.messageSender = messageSender;
	}
	
	public void run(){
		while(true){
			
			if(node.state == 0&&!node.msg.equals("")&&node.phase == 2){
				node.insert(node.msg, String.valueOf(System.currentTimeMillis()));
				node.msg = "";
			}
		}
	}
}
