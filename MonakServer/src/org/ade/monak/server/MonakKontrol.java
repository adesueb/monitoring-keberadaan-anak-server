package org.ade.monak.server;


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
				if(args[0].equals("daftar")){
					BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String pesan = "";
					while(! (pesan= buff.readLine()).equals("end")){
						System.out.println(pesan);
					}
					System.out.println(pesan);
				}
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
