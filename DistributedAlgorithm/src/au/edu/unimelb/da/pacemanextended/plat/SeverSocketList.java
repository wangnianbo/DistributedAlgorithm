package au.edu.unimelb.da.pacemanextended.plat;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Sever Socket Container
 * @author Bill
 *
 */
public class SeverSocketList {
	
	private List<Socket> serverSocketList = new ArrayList<Socket>();
	private boolean finish = false;

	public synchronized void isFinish()  {
		if (finish == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		
	}

	public synchronized void finish() {
		finish = true;
		notifyAll();
	}
	public void addServerSocketList(Socket socket){
		this.serverSocketList.add(socket);
	}

	public List<Socket> getServerSocketList() {
		return serverSocketList;
	}
	
	

}
