package org.ade.monak.server;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class BackendKontrol implements Runnable{

	public BackendKontrol(){

		backendPush				= new BackendPush(PORT_PUSH);
		backendRequest			= new BackendRequest(backendPush, PORT_REQUEST);
		
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
		backendRequest.startServer();
		
	}
	
	
	public void stopServer(){
		jalan = false;
		try {
			serverRequest.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadKontrol.stop();
		backendRequest.stopServer();
	}
	
	public void run() {
		while(jalan){
			try {
				Socket socket = serverRequest.accept();
				BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String pesan = buff.readLine();
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				if(pesan.equals(STOP)){
					stopServer();
				}else if(pesan.equals(DAFTAR)){
					Map<String, Socket> maps = backendPush.getMapPush();
					for(String isiDaftar:maps.keySet()){
						dos.write((isiDaftar+"\n").getBytes());
						dos.flush();
					}
					dos.write("end\n".getBytes());
					dos.flush();
				}
				dos.close();
				buff.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
	}

	private Thread			threadKontrol;
	private BackendRequest	backendRequest;
	private BackendPush		backendPush;
	
	private static final int PORT_KONTROL	= 5555;
	private static final int PORT_PUSH		= 4444;
	private static final int PORT_REQUEST	= 2525;
	
	private ServerSocket serverRequest;
	private boolean jalan;
	
	private static final String STOP	= "stop";
	private static final String DAFTAR	= "daftar";
}	
