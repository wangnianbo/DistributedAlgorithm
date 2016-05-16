package au.edu.unimelb.da.pacemanextended.plat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import au.edu.unimelb.da.pacemanextended.multicast.simple.SimpleMulticastMessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.simple.SimpleMulticastMessageSender;

public class GamePlat {

	private static final Logger logger = Logger.getLogger(GamePlat.class
			.getName());

	public static final int playerNumber = 4;

	public MessageReceiver messageReceiver;

	public MessageSender messageSender;

	public static void main(String[] args) {

		Properties prop = new Properties();
		InputStream input = null;
		Map urlMap = new HashMap<String, String>();

		try {

			input = new FileInputStream("./resources/config/config.properties");

			prop.load(input);
			for (int i = 0; i < playerNumber; i++) {
				logger.log(Level.INFO, prop.getProperty("player" + (1 + i)));
				urlMap.put("player" + (1 + i),
						prop.getProperty("player" + (1 + i)));
			}

			// save properties to project root folder

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		GamePlat gamePlat = new GamePlat();
		gamePlat.setNetworkConnection(urlMap);
		logger.log(Level.INFO, "Finish Establish Connection");
		// multicast Test Player1 Sending message and others receive

		if (gamePlat.messageSender.getPlayerID().equals("player1")) {
			int count =0;
			while (true) {
				 
				gamePlat.messageSender.putMessage("player1 Sending Message: "+ ++count);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else {
			while (true) {
					System.out.println(gamePlat.messageSender.getPlayerID()+" getting Message:  " + gamePlat.messageReceiver.backMessage());
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private void setNetworkConnection(Map<String, String> urlMap) {
		String localPlayerID = null;
		Map<String, Socket> senderSocketMap = new HashMap<String, Socket>();
		SeverSocketList severSocketList = new SeverSocketList();
		for (int i = 1; i <= playerNumber; i++) {
			String playerID = "player" + i;
			String url = urlMap.get(playerID);
			String host = url.split(":")[0];
			int port = Integer.valueOf(url.split(":")[1]);

			while (true) {
				Socket socket = new Socket();
				try {

					socket.connect(new InetSocketAddress(host, port), 1000);
					senderSocketMap.put(playerID, socket);
					logger.log(Level.INFO, playerID);
					break;

				} catch (IOException e) {
					if (localPlayerID == null) {
						localPlayerID = playerID;
						ServerScoketCreater scoketCreater = new ServerScoketCreater(
								port, playerNumber, severSocketList);
						scoketCreater.start();
					}

				} 

			}
		}

		severSocketList.isFinish();
		 messageSender = new SimpleMulticastMessageSender(localPlayerID,
		 senderSocketMap);
		messageReceiver = new SimpleMulticastMessageReceiver(localPlayerID,
				severSocketList.getServerSocketList());

	}

}
