package org.ade.monak.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/*
 * format daftar push : id_koneksi
 */
public class BackendPush implements Runnable{

	public BackendPush(int port){
		try {
			this.serverPush 		= new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer(){
		jalan = true;
		threadPush = new Thread(this);
		threadPush.start();
	}
	
	public void stopServer(){
		jalan = false;
		for(Socket soc:mapPush.values()){
			if(soc!=null&&soc.isConnected()){
				try {
					soc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			serverPush.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Map<String, Socket> getMapPush() {
		return mapPush;
	}

	public void setMapPush(Map<String, Socket> mapPush) {
		this.mapPush = mapPush;
	}

	public void run() {
		while(jalan){
			try {
				Socket socket = serverPush.accept();
				socket.setSoTimeout(10000);
				BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String idOrtu = buff.readLine();
				mapPush.put(idOrtu, socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Thread threadPush;
	private ServerSocket serverPush;
	private boolean jalan = false;
	
	

	private Map<String, Socket> mapPush = new HashMap<String, Socket>();
	
	
}
