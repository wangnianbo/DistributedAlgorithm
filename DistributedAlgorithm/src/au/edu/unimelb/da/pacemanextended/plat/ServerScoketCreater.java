package au.edu.unimelb.da.pacemanextended.plat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import javax.net.ServerSocketFactory;

public class ServerScoketCreater extends Thread{
	
	private static final Logger logger = Logger.getLogger(ServerScoketCreater.class.getName());
	
	private int port;
	private int playerNo;
	private SeverSocketList senderSocket;
	
	

	public ServerScoketCreater(int port, int playerNo, SeverSocketList severSocketList) {
		this.port = port;
		this.playerNo = playerNo;
		this.senderSocket = severSocketList;
	}


	public void run()  {
		ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
		ServerSocket serverSocket = null;
		Socket acceptSocket = null;
		try {
			serverSocket = serverSocketFactory.createServerSocket(port);
			for (int i = 0; i < playerNo; i++) {
				acceptSocket = serverSocket.accept();
				senderSocket.addServerSocketList(acceptSocket);
			}
			

		} catch (IOException ex) {
			
			ex.printStackTrace();
		}
		senderSocket.finish();
	}

}
