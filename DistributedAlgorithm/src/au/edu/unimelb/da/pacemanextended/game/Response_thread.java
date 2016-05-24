/*
 * receive the message and reply, and set the status
 */
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

	// initialize the constructor
	public Response_thread(Node node, MessageReceiver messageReceiver,
			MessageSender messageSender) {
		this.node = node;
		this.messageReceiver = messageReceiver;
		this.messageSender = messageSender;
	}

	// body of thread
	public void run() {
		while (true) {

			try {
				// decode the json message
				msg = messageReceiver.backMessage();
//				System.out.println("Receive: " + msg);
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
					messageSender.sendOtherMessage(messageSender.getPlayerID(),
							msg);
					System.out.println("Send: " + msg);
					msg = "";
					break;

				case "Vote":
					synchronized (node) {
						if (node.state == 1)
							node.voteCount++;
						
						if (node.voteCount >= (messageReceiver
								.getServerSocketList().size()/2)&&node.state ==1) // agree number
						{
							node.state = 0;
							node.phase = 2;
							// send heartbeat message
							msg = "Heartbeat true "+messageSender.getPlayerID();
							encode = new Encoding(msg);
							msg = encode.encode();
							messageSender.sendOtherMessage(
									messageSender.getPlayerID(), msg);
							System.out.println("Send: " + msg);
							msg = "";

							// start heartbeat thread
							Heartbeat t = new Heartbeat(node, messageReceiver,
									messageSender);
							t.start();
						}
					}
					break;

				case "Message":
					synchronized (node) {
						if (node.state == 0 && decodMsg[3].equals("false")) {
							synchronized (node) {
								node.insert(decodMsg[1], decodMsg[2]);
							}
						}
					}
					if (decodMsg[3].equals("true")) {
						// give back the value
						synchronized (node) {
							node.backMsg = decodMsg[1];
							System.out.println("result: " + decodMsg[1]);
						}
					}
					break;

				case "Heartbeat":
					synchronized (node) {
						if(decodMsg[1].equals("false")){
							
						}
						if(decodMsg[1].equals("true")){
							node.reset = true;
							node.phase = 2;
							node.notifyAll();
							msg = "Heartbeat false "+messageSender.getPlayerID();
							encode = new Encoding(msg);
							msg = encode.encode();
							messageSender.putMessage(
									decodMsg[2], msg);
//							System.out.println("Send: " + msg);
						}
					}
					break;

				default:
					break;
				}

			} catch (ParseException e) {

			}
		}
	}
}
