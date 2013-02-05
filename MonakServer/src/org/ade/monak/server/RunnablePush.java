package org.ade.monak.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class RunnablePush implements Runnable{

	public RunnablePush(){
		try {
			this.serverPush 		= new ServerSocket(PORT_PUSH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.serverKoneksiPush 	= new KoneksiPushFactory();
	}
	
	public KoneksiPushFactory getKoneksiPushFactory(){
		return this.serverKoneksiPush;
	}
	
	public void startServer(){
		jalan = true;
		threadPush = new Thread(this);
		threadPush.start();
	}
	
	public void stopServer(){
		jalan = false;
		try {
			serverPush.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadPush.stop();
	}
	
	public void run() {
		while(jalan){
			try {
				Socket socket = serverPush.accept();
				BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String idOrtu = buff.readLine();
				serverKoneksiPush.getOrtuKoneks().put(idOrtu, socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Thread threadPush;
	private ServerSocket serverPush;
	private final KoneksiPushFactory serverKoneksiPush;
	
	private boolean jalan = false;
	
	private static final int PORT_PUSH	= 4444;
}
