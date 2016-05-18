package au.edu.unimelb.da.pacemanextended.game;

import org.json.simple.parser.ParseException;

import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class Response_thread extends Thread {

	private String msg;
	private Node node;
	private GamePlat gamePlat;
	private Encoding encode;
	private Decoding decode;

	public Response_thread(GamePlat gamePlat) {

		this.gamePlat = gamePlat;
		this.node = gamePlat.node;
	}

	public void run() {
		while (true) {
			
			try{
			msg = gamePlat.messageReceiver.backMessage();
			System.out.println("Receive: "+msg);
			decode = new Decoding(msg);
			String[] decodMsg = new String[decode.decode().length];
			for (int i = 0; i < decode.decode().length; i++) {
				decodMsg[i] = decode.decode()[i];
			}

			switch (decodMsg[0]) {
				
			case "Request Vote":
				synchronized (node) {
					node.reset = true;
					node.phase = 1;
					node.term++;
					node.state = 2;
					msg = "Vote";
					encode = new Encoding(msg);
					msg = encode.encode();
					node.notifyAll();
				}
				gamePlat.messageSender.sendOtherMessage(gamePlat.messageSender.getPlayerID(),msg);
				System.out.println("Send: "+msg);
				msg ="";
				break;

			case "Vote":
				synchronized (node){
				if(node.state == 1)
					node.voteCount++;			
				if(node.voteCount == 2) //agree number
					{
					node.state = 0;
					node.phase = 2;
					// heartbeat
					msg = "Heartbeat";
					encode = new Encoding(msg);
					msg = encode.encode();
					gamePlat.messageSender.sendOtherMessage(gamePlat.messageSender.getPlayerID(),msg);
					System.out.println("Send: "+msg);
					msg ="";
					
					Heartbeat t = new Heartbeat(gamePlat);
					t.start();
					}
				}
				break;

			case "Message":
				synchronized(node){
				if(node.state == 0&&decodMsg[3].equals("false")){
					synchronized(node){
					node.insert(decodMsg[1],decodMsg[2]);
					}
				}
				}
				if(decodMsg[3].equals("true")){
					// give back the value
					synchronized(node){
						gamePlat.node.backMsg = decodMsg[1];
						System.out.println("result: "+decodMsg[1]);
					}
				}
				break;

			case "Heartbeat":
				synchronized (node) {
					node.reset = true;
					node.phase = 2;
					node.notifyAll();
				}
				break;
				

			default:
				break;
			}

		}
			catch(ParseException e){
			
		}
		}
	}
}
