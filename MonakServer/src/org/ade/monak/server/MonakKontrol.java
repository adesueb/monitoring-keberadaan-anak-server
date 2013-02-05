package org.ade.monak.server;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MonakKontrol {

	public static void main(String [] args){
		if(args.length>0){
			try {
				Socket socket = new Socket("localhost", PORT_KONTROL);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.write((args[0]+"\n").getBytes());
				dos.flush();
				dos.close();
				socket.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
	}
	

	private static final int PORT_KONTROL	= 5555;
}
