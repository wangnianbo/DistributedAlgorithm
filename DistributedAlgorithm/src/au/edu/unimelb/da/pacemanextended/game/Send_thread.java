/*
 * send the message in each phase
 */
package au.edu.unimelb.da.pacemanextended.game;

import java.util.Random;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class Send_thread extends Thread {

	private String msg = "";
	private Node node;
	private Encoding encode;
	MessageReceiver messageReceiver;
	MessageSender messageSender;

	public Send_thread(Node node, MessageReceiver messageReceiver,
			MessageSender messageSender) {
		this.node = node;
		this.messageReceiver = messageReceiver;
		this.messageSender = messageSender;
	}

	// body of thread
	public void run() {
		while (true) {
			try {
				boolean jump = false;

				if (node.phase == 0) {
					// phase 1 election
					synchronized (node) {
						while (!(node.state == 2 && node.signal == true)) {
							if (node.phase == 2) {
								jump = true;
								break;
							}
							node.wait();
						}
						if (jump == true)
							continue;
						node.term++;
						node.voteCount++;
						node.state = 1;
						node.phase = 1;
						msg = "Request Vote";
						encode = new Encoding(msg);
						msg = encode.encode();
						messageSender.sendOtherMessage(
								messageSender.getPlayerID(), msg); // request
																	// vote
						// System.out.println(node.term+" "+node.phase+" "+node.state);
						System.out.println("Send: " + msg);
						msg = "";
					}
				}

				// phase 2 log replication
				label: if (node.phase == 2) {

					long timestamp = node.Election_timeout(); // local host has
																// same
																// timestamp
																// System.currentTimeMillis()
					}	
				} 
				for(int i=0; i<10000; i++);
				//System.out.println(node.state+" "+node.phase);
label:			if (node.phase == 2){
					
				//long timestamp = node.Election_timeout(); // local host has same timestamp System.currentTimeMillis()
					// phase 2 log replication
					switch (node.state) {

					// leader
					case 0:
						synchronized (this.node) {
							if (!node.msg.equals("")) {
								msg = "Message " + node.msg + " "
										+ System.currentTimeMillis() + " false";

								// package into jason
								encode = new Encoding(msg);
								msg = encode.encode();
								messageSender.putMessage(msg);
								System.out.println("Send: " + msg);
								node.msg = "";
							}
						}

						break;

					// follower
					case 2:
						synchronized (this.node) {
							if (!node.msg.equals("")) {
								msg = "Message " + node.msg + " "
										+ System.currentTimeMillis() + " false";

								// package into jason
								encode = new Encoding(msg);
								msg = encode.encode();
								messageSender.putMessage(msg);
								System.out.println("Send: " + msg);
								node.msg = "";
							}
						}
						break;

					default:
						break;
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
