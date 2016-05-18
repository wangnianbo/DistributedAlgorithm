package au.edu.unimelb.da.pacemanextended.game;

import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class Heartbeat extends Thread {
	
	private Node node;
	private GamePlat gamePlat;
	private String msg;
	private Encoding encode;
	
	public Heartbeat (GamePlat gamePlat){
		this.gamePlat = gamePlat;
		this.node = gamePlat.node;
	}
	
	public void run(){
		while(true){
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg = "Heartbeat";
			encode = new Encoding(msg);
			msg = encode.encode();
			gamePlat.messageSender.sendOtherMessage(gamePlat.messageSender.getPlayerID(),msg);
			System.out.println("Send: "+msg);
			msg="";
			
			// during one heart beat send messages to all nodes
			String[] c = new String[10];
			synchronized(this.node){
				for(int i =0; i<10; i++)
				{
					c[i]=node.a[i];
					node.a[i]="";
					node.b[i]=0;
				}
			
			for(int i=0; i<10; i++){
				if(!c[i].equals("")){
					msg = "Message "+ c[i] + " "+11111+" true";
				
					// package into jason
					encode = new Encoding(msg);
					msg = encode.encode();
					gamePlat.messageSender.putMessage(msg);
					System.out.println("Send: "+msg);
					msg="";
					}
			}
			}
		}
	}

}
