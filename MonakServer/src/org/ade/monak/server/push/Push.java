package org.ade.monak.server.push;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Push {

	public Push(Socket socket){
		
		this.socket = socket;
		
		try {
			
			// need push class... to close socket connection...
			pushPingSender = new PushPingSender(this);
			//.................................................
			
			pushResponseReceiver = 
					new PushResponseReceiver(new BufferedReader
							(new InputStreamReader(socket.getInputStream())));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//getters...............................................
	public Socket getSocket() {
		return socket;
	}
	public PushPingSender getPushPingSender() {
		return pushPingSender;
	}
	public PushResponseReceiver getPushResponseReceiver() {
		return pushResponseReceiver;
	}
	//......................................................

	private Socket socket;
	private PushPingSender pushPingSender;
	private PushResponseReceiver pushResponseReceiver;
}
