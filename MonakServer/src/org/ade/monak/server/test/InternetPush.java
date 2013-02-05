package org.ade.monak.server.test;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class InternetPush implements Runnable{

	public static void main(String [] args){
		InternetPush internet = new InternetPush();
		internet.startConnection();
	}
	
	public InternetPush(){
		
	}
	public void run() {
		try {
		
			socket = new Socket("localhost", 4444);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
			
			// TODO : isi dengan id Ortu...................
			dos.write(("1717"+"\n").getBytes());
			//.............................................
			dos.flush();
			System.out.print("masuk gak?");
			
			while(startConnection){
				String pesan = buff.readLine();
				System.out.println(pesan);
				if(pesan==null){
					break;		
				}
			}
			buff.close();
			dos.close();
			socket.close();
					
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			if(startConnection){
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				startConnection();
			}
		} finally{
		}
	}
	
	public void stopConnection(){
		startConnection = false;
	}
	
	public void startConnection(){
		startConnection = true;
		new Thread(this).start();	
		
	}
	
	public boolean isStart(){
		return startConnection;
	}
	
	private Socket socket;
	private boolean startConnection = false;
	

}
