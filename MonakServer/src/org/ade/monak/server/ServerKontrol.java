package org.ade.monak.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.ade.monak.server.push.Push;
import org.ade.monak.server.push.PushService;
import org.ade.monak.server.util.FileLog;

public class ServerKontrol implements Runnable{

	public ServerKontrol(){
		
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
	}
	
	
	public void stopServer(){
		jalan = false;
		try {
			serverRequest.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
					Map<String, Push> maps = pushService.getPushFactory().getMapPush();
					for(String isiDaftar:maps.keySet()){
						dos.write((isiDaftar+"\n").getBytes());
						dos.flush();
					}
					dos.write("end\n".getBytes());
					dos.flush();
				}else if(pesan.equals(LOG)){
					String log = FileLog.readLog();
					char[] charArray = log.toCharArray();
					for(int i=0;i<charArray.length;i++){
						dos.writeChar(charArray[i]);
					}
				}else if(pesan.equals(CLEAR_LOG)){
					FileLog.clearLog();

					dos.write("sukses delete\n".getBytes());
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
	
	private PushService 	pushService;
	
	private static final int PORT_KONTROL	= 5555;
	
	private ServerSocket serverRequest;
	private boolean jalan;
	
	private static final String STOP		= "stop";
	private static final String DAFTAR	= "daftar";
	private static final String LOG		= "log";
	private static final String CLEAR_LOG	= "clear_log";
}	
