package au.edu.unimelb.da.pacemanextended.plat;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import com.sun.media.jfxmedia.events.NewFrameEvent;
import com.sun.org.apache.xerces.internal.util.EntityResolver2Wrapper;

import au.edu.unimelb.da.pacemanextended.game.Node;
import au.edu.unimelb.da.pacemanextended.game.Response_thread;
import au.edu.unimelb.da.pacemanextended.game.Send_thread;
import au.edu.unimelb.da.pacemanextended.game.Time_out;
import au.edu.unimelb.da.pacemanextended.multicast.MessageCenter;
import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;

import au.edu.unimelb.da.pacemanextended.multicast.isis.IsisMulticastMessage;
import au.edu.unimelb.da.pacemanextended.multicast.isis.IsisReceivedMessageProcessor;
import au.edu.unimelb.da.pacemanextended.multicast.raft.RaftMulticastMessage;

import au.edu.unimelb.da.pacemanextended.multicast.simple.SimpleMulticastMessage;
import au.edu.unimelb.da.pacemanextended.multicast.simple.SimpleMulticastMessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.simple.SimpleMulticastMessageSender;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class GamePlat {

	private static final Logger logger = Logger.getLogger(GamePlat.class.getName());

	public static int playerNumber = 3;

	String[] addressArray = { "localhost:40001", "localhost:40002", "localhost:40003", "localhost:40004" };// 10.12.239.20//10.13.233.69//172.20.10.8

	public MessageReceiver messageReceiver;

	public MessageSender messageSender;

	public MessageCenter messageCenter;

	public String localPlayerID;

	/*
	 * The running environment of the pac-man
	 */
	public GamePlat() {

		Properties prop = new Properties();
		Map<String, String> urlMap = new HashMap<String, String>();
		// try {
		//
		// input = new FileInputStream("./resources/config/config.properties");
		//
		// prop.load(input);
		// for (int i = 0; i < playerNumber; i++) {
		// logger.log(Level.INFO, prop.getProperty("player" + (1 + i)));
		// urlMap.put("player" + (1 + i),
		// prop.getProperty("player" + (1 + i)));
		// }
		//
		// // save properties to project root folder
		//
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// } finally {
		// if (input != null) {
		// try {
		// input.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }

		for (int i = 0; i < playerNumber; i++) {
			logger.log(Level.INFO, prop.getProperty("player" + (1 + i)));
			urlMap.put("player" + (1 + i), addressArray[i]);
		}
		localPlayerID = setNetworkConnection(urlMap);
		logger.log(Level.INFO, "Finish Establish Connection");
		// multicast Test Player1 Sending message and others receive

		// if (gamePlat.messageSender.getPlayerID().equals("player1")) {
		// int count =0;
		// while (true) {
		//
		// gamePlat.messageSender.putMessage("player1 Sending Message: "+
		// ++count);
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// } else {
		// while (true) {
		// System.out.println(gamePlat.messageSender.getPlayerID()+" getting
		// Message: " + gamePlat.messageReceiver.backMessage());
		// try {
		// Thread.sleep(1);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }

	}

	/**
	 * Estanbulish tcp connection between all players
	 * 
	 * @param urlMap
	 * @return
	 */
	private String setNetworkConnection(Map<String, String> urlMap) {
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
						ServerScoketCreater scoketCreater = new ServerScoketCreater(port, playerNumber,
								severSocketList);
						scoketCreater.start();
					}

				}

			}
		}

		severSocketList.isFinish();
		messageSender = new SimpleMulticastMessageSender(localPlayerID, senderSocketMap);
		messageReceiver = new SimpleMulticastMessageReceiver(localPlayerID, severSocketList.getServerSocketList());

		// messageCenter = new RaftMulticastMessage (messageReceiver,
		// messageSender);

		// messageCenter = new IsisMulticastMessage(messageReceiver,
		// messageSender);

		messageCenter = new SimpleMulticastMessage(messageReceiver, messageSender);

		// messageCenter = new IsisMulticastMessage(messageReceiver,
		// messageSender);
		return localPlayerID;

	}

	public static void main(String[] args) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		// multicast order
		/*
		 * playerNumber = 3; Integer numberOfTest = 100; String resultFileName
		 * ="multicastOrder_"+playerNumber+"players_"+simpleDateFormat.format(
		 * new Date())+".txt"; GamePlat gamePlat = new GamePlat();
		 * TestMulicastOrderMessageProssor testMulicastOrderMessageProssor = new
		 * TestMulicastOrderMessageProssor(gamePlat.messageCenter,
		 * numberOfTest*playerNumber, resultFileName);
		 * testMulicastOrderMessageProssor.start(); try { Thread.sleep(5000); }
		 * catch (InterruptedException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } for (int i = 0; i < numberOfTest; i++) {
		 * 
		 * gamePlat.messageCenter.putMessage(UUID.randomUUID());
		 * 
		 * }
		 */
		// round timer
		// multicast order
		playerNumber = 2;
		Integer numberOfTest = 10;
		String resultFileName = "RoundTime_" + playerNumber + "players_" + simpleDateFormat.format(new Date()) + ".txt";
		GamePlat gamePlat = new GamePlat();
		TestMulicastRoundMessageProssor testMulicastRoundMessageProssor = new TestMulicastRoundMessageProssor(
				gamePlat.messageCenter, numberOfTest * (playerNumber - 1), resultFileName);
		testMulicastRoundMessageProssor.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < numberOfTest; i++) {
			for (int j = 0; j < playerNumber; j++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Time", new Date().getTime());
				jsonObject.put("Sender", gamePlat.localPlayerID);
				String receiver = "player" + (j + 1);
				if (!gamePlat.localPlayerID.equals(receiver)) {
					gamePlat.messageCenter.putMessage(receiver, jsonObject.toJSONString());
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gamePlat.messageCenter.putMessage("Done");

		// if (gamePlat.messageSender.getPlayerID().equals("player1")) {
		// int count =0;
		// while (true) {
		//
		// gamePlat.messageCenter.putMessage("player1 Sending Message: "+
		// ++count);
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// } else {
		// while (true) {
		// System.out.println(gamePlat.messageSender.getPlayerID()+" getting
		// Message: " + gamePlat.messageCenter.backMessage());
		// try {
		// Thread.sleep(1);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }

	}

}