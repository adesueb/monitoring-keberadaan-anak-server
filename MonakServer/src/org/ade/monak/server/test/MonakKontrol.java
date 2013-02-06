package org.ade.monak.server.test;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
				BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				if(args[0].equals(DAFTAR)){
					String pesan = "";
					while(! (pesan= buff.readLine()).equals(END)){
						System.out.println(pesan);
					}
					System.out.println(pesan);
				}else if(args[0].equals(STOP)){
					
				}
				buff.close();
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
	private static final String DAFTAR 		= "daftar";
	private static final String STOP		= "stop";

	private static final String END			= "end";
	
}
