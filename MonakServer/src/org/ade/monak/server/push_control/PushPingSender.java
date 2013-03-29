package org.ade.monak.server.push_control;

import java.io.DataOutputStream;
import java.io.IOException;

public class PushPingSender implements Runnable{

	public PushPingSender (DataOutputStream dos){
		this.dos	= dos;
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
	
	private final DataOutputStream 	dos;
	
	private final static String PING = "?";
	
	private boolean start = false;
	
	
}
