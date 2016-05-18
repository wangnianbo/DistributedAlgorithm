package au.edu.unimelb.da.pacemanextended.game;
import au.edu.unimelb.da.pacemanextended.plat.GamePlat;

public class Time_out extends Thread {
	
    private Node node;
    
    public Time_out(GamePlat gamePlat){
    	this.node = gamePlat.node;
    }
    
	public synchronized void run() {
		while (true) {
			this.node.signal = false;
			try {
				synchronized (node) {
					node.wait(this.node.Election_timeout());
					if(node.reset == true)
					{
						node.reset = false;
						continue;
					}			
					else
					{
						this.node.signal = true;
						this.node.notifyAll();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}