/*
 * encode the message from string to json
 */
package au.edu.unimelb.da.pacemanextended.game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Encoding {
	private String msg;

	public Encoding(String msg) {
		this.msg = msg;
	}

	public String encode() {

		// get word from string which seperated by white space
		//System.out.println(msg);
		String[] words = msg.split(" ");
		JSONObject object = new JSONObject();
		JSONArray list = new JSONArray();
		switch (words[0]) {

		case "Message":
			object.put("type", "Message");
			object.put("content", words[1] + " " + words[2] + " " + words[3]);
			object.put("timestamp", words[4]);
			object.put("leadersignal", words[5]);
			break;

		case "Request":
			object.put("type", "Request Vote");
			break;

		case "Vote":
			object.put("type", "Vote");
			break;

		case "Heartbeat":
			object.put("type", "Heartbeat");
			object.put("source", words[1]);
			object.put("id", words[2]);
			break;

		default:
			break;
		}

		return object.toString();
	}

}