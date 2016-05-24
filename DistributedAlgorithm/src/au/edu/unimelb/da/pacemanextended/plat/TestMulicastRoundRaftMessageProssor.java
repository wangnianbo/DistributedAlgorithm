package au.edu.unimelb.da.pacemanextended.plat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import au.edu.unimelb.da.pacemanextended.multicast.MessageCenter;

public class TestMulicastRoundRaftMessageProssor extends Thread{
	MessageCenter messageCenter;
	int testCounter=0;
	String fileName;
	public TestMulicastRoundRaftMessageProssor(MessageCenter messageCenter, int testCounter, String fileName) {
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
			if("Done_Done_Done".equals(messageString)){
				break;
			}
			String[] messages = messageString.split(" "); 
			if(!"null".equals(messages[2])){
				String record = messages[0]+"\t"+messages[2]+"\t"
			+(new Date().getTime() -Long.parseLong(messages[1]) )+"\n";
				System.out.println(record);
				result.append(record);
			}else {
				messageCenter.putMessage(messages[0], messages[0]+" "+messages[1]+" "+messageCenter.messageSender.getPlayerID());
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
