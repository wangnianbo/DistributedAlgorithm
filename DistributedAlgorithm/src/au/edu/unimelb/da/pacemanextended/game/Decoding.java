package au.edu.unimelb.da.pacemanextended.game;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Decoding {
	
	private String msg;

	public Decoding(String msg) {
		this.msg = msg;
	}

	public String[] decode() throws ParseException{
		
		String[] receive = null;
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(msg);
		switch((String) object.get("type"))
		{
		
		case "Message":
			receive = new String[4];
			receive[0] = "Message";
			receive[1] = (String) object.get("content");
			receive[2] = (String) object.get("timestamp");
			receive[3] = (String) object.get("leadersignal");
			break;
		
		case "Request Vote":
			receive = new String[1];
			receive[0] = "Request Vote";
			break;
		
		case "Vote":
			receive = new String[1];
			receive[0] = "Vote";
			break;
	
		case "Heartbeat":
			receive = new String[1];
			receive[0] = "Heartbeat";
			break;
		
		default: 
			break;
		}
		
		
	
		return receive;
	}
}

