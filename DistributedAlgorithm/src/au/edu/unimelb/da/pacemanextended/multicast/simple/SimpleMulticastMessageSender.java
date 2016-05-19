package au.edu.unimelb.da.pacemanextended.multicast.simple;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.edu.unimelb.da.pacemanextended.multicast.MessageSender;

public class SimpleMulticastMessageSender extends MessageSender {
	
	private static final Logger logger = Logger.getLogger(SimpleMulticastMessageSender.class.getName());

	private Map<String, OutputStreamWriter> outputStreamWriterMap = new HashMap<String, OutputStreamWriter>();

	public SimpleMulticastMessageSender(String playerID,
			Map<String, Socket> sendSocketMap) {
		super(playerID, sendSocketMap);
		Iterator<String> keys = sendSocketMap.keySet().iterator();
		while(keys.hasNext()){
			
			String key = keys.next();
			
			
			OutputStreamWriter osw =null ;
			try {
				osw = new OutputStreamWriter(sendSocketMap.get(key).getOutputStream(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.log(Level.INFO, "osw: "+osw);
			outputStreamWriterMap.put(key, osw);
		}
		
	}

	@Override
	public boolean putMessage(String jsonMsg) {
		Iterator<String> key = outputStreamWriterMap.keySet().iterator();
		while (key.hasNext()) {
			OutputStreamWriter osw = outputStreamWriterMap.get(key.next());
			try {
				osw.write(jsonMsg);
				osw.write(System.lineSeparator());
				osw.flush();
			}catch(SocketException e){
				
			}
			catch (IOException e) {
				
				e.printStackTrace();
				return false;
			}
			
		}
		return true;
	}
	
	

	@Override
	public boolean putMessage(String playerID, String jsonMsg) {
		OutputStreamWriter osw = outputStreamWriterMap.get(playerID);
		try {
			osw.write(jsonMsg);
			osw.write(System.lineSeparator());
			osw.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public boolean sendOtherMessage(String playerID, String jsonMsg) {
		Iterator<String> keyIt = outputStreamWriterMap.keySet().iterator();
		while (keyIt.hasNext()) {
			String keyString = keyIt.next();
			if(!keyString.equals(playerID)){
				OutputStreamWriter osw = outputStreamWriterMap.get(keyString);
				try {
					osw.write(jsonMsg);
					osw.write(System.lineSeparator());
					osw.flush();
				}catch(SocketException e){
					
				} 
				catch (IOException e) {
					
					e.printStackTrace();
					return false;
				}
			}
			
			
		}
		return true;
	}

}
