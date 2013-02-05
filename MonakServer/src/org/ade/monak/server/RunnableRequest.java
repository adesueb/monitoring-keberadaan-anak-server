package org.ade.monak.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RunnableRequest implements Runnable{

	public RunnableRequest(){

		runnablePush				= new RunnablePush();
		
		try {
			this.serverRequest 		= new ServerSocket(PORT_REQUEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void startServer(){
		jalan = true;
		threadRequest = new Thread(this);
		threadRequest.start();
		runnablePush.startServer();
		
	}
	
	public KoneksiPushFactory getKoneksiPushFactory(){
		return runnablePush.getKoneksiPushFactory();
	}

	
	public void stopServer(){
		jalan = false;
		try {
			serverRequest.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadRequest.stop();
		runnablePush.stopServer();
	}
	
	public void run() {
		while(jalan){
			try {
				Socket socket = serverRequest.accept();

				new Thread(new ServerPenerimaRequest
						(socket, runnablePush.getKoneksiPushFactory()))
							.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
	}

	private Thread			threadRequest;
	private RunnablePush	runnablePush;
	private static final int PORT_REQUEST	= 2525;
	private ServerSocket serverRequest;
	private boolean jalan;
}	
