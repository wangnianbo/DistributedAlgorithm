package au.edu.unimelb.da.pacemanextended.plat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import au.edu.unimelb.da.pacemanextended.multicast.MessageCenter;

public class TestMulicastRoundMessageProssor extends Thread{
	MessageCenter messageCenter;
	int testCounter=0;
	String fileName;
	public TestMulicastRoundMessageProssor(MessageCenter messageCenter, int testCounter, String fileName) {
		this.messageCenter = messageCenter;
		this.testCounter = testCounter;
		this.fileName = fileName;
	}
	
	public void run(){
		String messageString ="";
		StringBuffer result = new StringBuffer();
		JSONParser jsonParser = new org.json.simple.parser.JSONParser();
		while (true) {
			
			messageString = messageCenter.backMessage();
			if("Done".equals(messageString)){
				break;
			}
			try {
				JSONObject msgJsn = (JSONObject) jsonParser.parse(messageString);
				if(msgJsn.containsKey("Receiver")){
					String record = msgJsn.get("Sender")+"\t"+msgJsn.get("Receiver")+"\t"
				+(new Date().getTime() -(long) msgJsn.get("Time"))+"\n";
					System.out.println(record);
					result.append(record);
				}else {
					msgJsn.put("Receiver", messageCenter.messageSender.getPlayerID());
					String receiver = (String) msgJsn.get("Sender");
					messageCenter.putMessage(receiver, msgJsn.toJSONString());
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File file = new File(fileName);
		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] resultInBytes = result.toString().getBytes();

			fop.write(resultInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
