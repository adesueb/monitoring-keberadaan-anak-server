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

		runnableRequest				= new BackendRequest();
		
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
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				if(pesan.equals(STOP)){
					stopServer();
				}else if(pesan.equals(DAFTAR)){
					Map<String, Socket> maps = runnableRequest.getBackendPush().getMapPush();
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
	private BackendRequest	runnableRequest;
	private static final int PORT_KONTROL	= 5555;
	private ServerSocket serverRequest;
	private boolean jalan;
	
	private static final String STOP	= "stop";
	private static final String DAFTAR	= "daftar";
	private static final String PING	= "?";
	private static final String PONG	= "Y";
}	
