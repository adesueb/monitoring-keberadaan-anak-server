package org.ade.monak.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class BackendPingPong implements Runnable{

	public BackendPingPong (BufferedReader buff, DataOutputStream dos){
		this.buff 	= buff;
		this.dos	= dos;
	}
	@Override
	public void run() {
		try {
			while(true){
				String pesan = buff.readLine();
				if(pesan!=null && pesan.equals(PING)){
					dos.write((PONG+"\n").getBytes());
					dos.flush();	
				}else{
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private final BufferedReader 	buff;
	private final DataOutputStream 	dos;
	
	private final static String PING = "?";
	private final static String PONG = "Y";
	
}
