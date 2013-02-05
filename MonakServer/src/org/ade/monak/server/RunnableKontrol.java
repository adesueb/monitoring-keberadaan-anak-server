package org.ade.monak.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class RunnableKontrol implements Runnable{

	public RunnableKontrol(){

		runnableRequest				= new RunnableRequest();
		
		try {
			this.serverRequest 		= new ServerSocket(PORT_KONTROL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void startServer(){
		jalan = true;
		threadKontrol = new Thread(this);
		threadKontrol.start();
		runnableRequest.startServer();
		
	}
	
	
	public void stopServer(){
		jalan = false;
		try {
			serverRequest.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadKontrol.stop();
		runnableRequest.stopServer();
	}
	
	public void run() {
		while(jalan){
			try {
				Socket socket = serverRequest.accept();
				BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String pesan = buff.readLine();
				buff.close();
				socket.close();
				if(pesan == "mati"){
					stopServer();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
	}

	private Thread			threadKontrol;
	private RunnableRequest	runnableRequest;
	private static final int PORT_KONTROL	= 5555;
	private ServerSocket serverRequest;
	private boolean jalan;
}	
