package org.ade.monak.server.push_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Queue;

import org.ade.monak.server.util.Handler;

public class PushRead implements Runnable{

	public PushRead(BufferedReader buff){
		this.buff = buff;
	}
	
	public void start(){
		start = true;
		new Thread(this).start();
	}
	
	public void stop(){
		start = false;
	}
	
	@Override
	public void run() {
		while(start){
			try {
				String pesan = buff.readLine();
				if(pesan!=PONG){
					Handler handler = queue.poll();
					if(handler!=null){
						handler.sendMessage(pesan);
					}
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private Queue<Handler> queue;
	private boolean start = false;
	private final BufferedReader buff;
	private final static String PONG = "Y";

}
