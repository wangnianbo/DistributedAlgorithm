package au.edu.unimelb.da.pacemanextended.game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Encoding {
	private String msg;

	public Encoding(String msg) {
		this.msg = msg;
	}

	public String encode() {
		String[] words = msg.split(" ");
		JSONObject object = new JSONObject();
		JSONArray list = new JSONArray();
		switch (words[0]) {
		
		case "Message":
			object.put("type", "Message");
			object.put("content", words[1]+" "+words[2]);
			object.put("timestamp", words[3]);
			object.put("leadersignal", words[4]);
			break;
		
		case "Request":
			object.put("type", "Request Vote");
			break;
		
		case "Vote":
			object.put("type", "Vote");
			break;
		
		case "Heartbeat":
			object.put("type", "Heartbeat");
			break;
		
		

		default:
			break;
		}

		return object.toString();
	}

}