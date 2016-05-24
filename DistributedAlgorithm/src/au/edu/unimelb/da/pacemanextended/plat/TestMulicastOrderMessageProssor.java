package au.edu.unimelb.da.pacemanextended.plat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import au.edu.unimelb.da.pacemanextended.multicast.MessageCenter;

public class TestMulicastOrderMessageProssor extends Thread{
	MessageCenter messageCenter;
	int testCounter=0;
	String fileName;
	public TestMulicastOrderMessageProssor(MessageCenter messageCenter, int testCounter, String fileName) {
		this.messageCenter = messageCenter;
		this.testCounter = testCounter;
		this.fileName = fileName;
	}
	
	public void run(){
		String messageString ="";
		StringBuffer result = new StringBuffer();
		while (testCounter !=0) {
			System.out.println("Counter: "+testCounter);
			messageString = messageCenter.backMessage();
			result.append(messageString).append("\n");
			testCounter--;
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
