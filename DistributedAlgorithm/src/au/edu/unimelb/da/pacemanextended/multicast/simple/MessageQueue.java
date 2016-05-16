package au.edu.unimelb.da.pacemanextended.multicast.simple;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
	private Queue<String> msgQueue = new LinkedList<String>();
	public synchronized void put(String message) {
		msgQueue.add(message);
		notify();
		
	}
	
	public synchronized String get(){
		if(msgQueue.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return msgQueue.poll();
	}

}
