package au.edu.unimelb.da.pacemanextended.game;

import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class Insert_thread extends Thread{

	private GamePlat gamePlat;
	private Node node;
	
	public Insert_thread(GamePlat gamePlat){
		
		this.gamePlat = gamePlat;
		this.node = gamePlat.node;
		
	}
	
	public void run(){
		while(true){
			
			if(node.state == 0&&!node.msg.equals("")&&node.phase == 2){
				node.insert(node.msg, String.valueOf(System.currentTimeMillis()));
				node.msg = "";
			}
		}
	}
}
