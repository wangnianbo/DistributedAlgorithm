package au.edu.unimelb.da.pacemanextended.multicast.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Message Receiver
 * @author Bill
 *
 */
public class ReceiverThread extends Thread {

	private int playerId;
	private Socket socket;
	private MessageQueue messageQueue;
	private BufferedReader in;

	public ReceiverThread(int playerId, Socket socket, MessageQueue messageQueue) {
		this.playerId = playerId;
		this.socket = socket;
		this.messageQueue = messageQueue;
	}

	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "UTF-8"));
			while (true) {
				String message = in.readLine();
				messageQueue.put(message);
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
