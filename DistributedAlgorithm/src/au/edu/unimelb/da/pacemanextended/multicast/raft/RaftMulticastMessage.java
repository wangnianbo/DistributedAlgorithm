package au.edu.unimelb.da.pacemanextended.multicast.raft;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import au.edu.unimelb.da.pacemanextended.game.Node;
import au.edu.unimelb.da.pacemanextended.game.Response_thread;
import au.edu.unimelb.da.pacemanextended.game.Send_thread;
import au.edu.unimelb.da.pacemanextended.game.Time_out;
import au.edu.unimelb.da.pacemanextended.multicast.MessageReceiver;
import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;
import au.edu.unimelb.da.pacemanextended.multicast.simple.SimpleMulticastMessage;
import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class RaftMulticastMessage extends SimpleMulticastMessage {
	Node node;

	public RaftMulticastMessage(MessageReceiver messageReceiver,
			MessageSender messageSender) {
		super(messageReceiver, messageSender);
		node = new Node();
		Response_thread response = new Response_thread(node, messageReceiver, messageSender);
		Send_thread send = new Send_thread(node, messageReceiver, messageSender);
		Time_out time = new Time_out(node, messageReceiver, messageSender);

		response.start();
		send.start();
		time.start();
	}

	@Override
	public String backMessage() {
		while (true) {
			String rsult = "";

			synchronized (node) {

				if (!node.backMsg.equals("")) {
					rsult = node.backMsg;
					node.backMsg = "";
				}

				
			}
			if(!"".equals(rsult)){
				String[] words = rsult.split(" ");
				JSONObject jsonObject1 = new JSONObject();
			
				jsonObject1.put("PlayerID", words[1]);
				jsonObject1.put("KeyCode", Integer.parseInt(words[0]));
				
				String jsonMsgString =jsonObject1.toJSONString();
				return jsonMsgString;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean putMessage(String playerID, String jsonMsg) {
		return super.messageSender.putMessage(playerID, jsonMsg);
	}

	@Override
	public boolean putMessage(String jsonMsg) {
		synchronized (node) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = null;
			try {
				jsonObject = (JSONObject) jsonParser.parse(jsonMsg);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String keycode = Long.toString((long) jsonObject.get("KeyCode"));
			
			
			String localPlayerID = (String) jsonObject.get("PlayerID");
			

			node.msg = keycode + " " + localPlayerID;
			System.out.println(node.msg);
		}
		return true;
	}

}
