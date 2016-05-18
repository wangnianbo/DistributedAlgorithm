package au.edu.unimelb.da.pacemanextended.game;

import org.json.simple.parser.ParseException;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class Response_thread extends Thread {

	private String msg;
	private Node node;
	private Encoding encode;
	private Decoding decode;
	MessageReceiver messageReceiver;
	MessageSender messageSender;


	public Response_thread(Node node, MessageReceiver messageReceiver,
			MessageSender messageSender) {
		this.node = node;
		this.messageReceiver = messageReceiver;
		this.messageSender = messageSender;
	}

	public void run() {
		while (true) {
			
			try{
			msg = messageReceiver.backMessage();
			System.out.println("Receive: "+msg);
			decode = new Decoding(msg);
			String[] decodMsg = new String[decode.decode().length];
			for (int i = 0; i < decode.decode().length; i++) {
				decodMsg[i] = decode.decode()[i];
			}

			switch (decodMsg[0]) {
				
			case "Request Vote":
				synchronized (node) {
					node.reset = true;
					node.phase = 1;
					node.term++;
					node.state = 2;
					msg = "Vote";
					encode = new Encoding(msg);
					msg = encode.encode();
					node.notifyAll();
				}
				messageSender.sendOtherMessage(messageSender.getPlayerID(),msg);
				System.out.println("Send: "+msg);
				msg ="";
				break;

			case "Vote":
				synchronized (node){
				if(node.state == 1)
					node.voteCount++;			
				if(node.voteCount == messageReceiver.getServerSocketList().size()) //agree number
					{
					node.state = 0;
					node.phase = 2;
					// heartbeat
					msg = "Heartbeat";
					encode = new Encoding(msg);
					msg = encode.encode();
					messageSender.sendOtherMessage(messageSender.getPlayerID(),msg);
					System.out.println("Send: "+msg);
					msg ="";
					
					Heartbeat t = new Heartbeat( node,  messageReceiver,
							 messageSender);
					t.start();
					}
				}
				break;

			case "Message":
				synchronized(node){
				if(node.state == 0&&decodMsg[3].equals("false")){
					synchronized(node){
					node.insert(decodMsg[1],decodMsg[2]);
					}
				}
				}
				if(decodMsg[3].equals("true")){
					// give back the value
					synchronized(node){
						node.backMsg = decodMsg[1];
						System.out.println("result: "+decodMsg[1]);
					}
				}
				break;

			case "Heartbeat":
				synchronized (node) {
					node.reset = true;
					node.phase = 2;
					node.notifyAll();
				}
				break;
				

			default:
				break;
			}

		}
			catch(ParseException e){
			
		}
		}
	}
	public static void main(String[] args){
//		String msg = "{\"KeyCode\":32,"PlayerID":"player3"}";
//		Decoding decode = new Decoding(msg);
//		String[] decodMsg = new String[decode.decode().length];
	}
}
