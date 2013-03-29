package org.ade.monak.server.push;

import java.io.DataOutputStream;
import java.io.IOException;

public class PushPingSender implements Runnable{

	public PushPingSender (Push push){
		this.push = push;
		try {
			this.dos	= new DataOutputStream(push.getSocket().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
		this.start = true;
		new Thread(this).start();
	}
	
	public void stop(){
		this.start = false;
	}
	
	@Override
	public void run() {
		
		while(start){
			try {
				dos.write((PING+"\n").getBytes());
				dos.flush();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				stop();
				e.printStackTrace();
			}
		}
		
	}
	
	private DataOutputStream 	dos;
	
	private final static String PING = "?";
	
	private boolean 		start = false;
	
	private final Push 	push;
	
	
}
