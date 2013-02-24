package org.ade.monak.server.push_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	
	public void addHandler(String key, Handler handler){
		if(mapHandler!=null){
			mapHandler = new HashMap<String, Handler>();
		}
		mapHandler.put(key, handler);
	}
	
	public void removeHandler(String key){
		if(mapHandler!=null){
			mapHandler.remove(key);
		}
	}
	
	@Override
	public void run() {
		while(start){
			try {
				String pesan = buff.readLine();
				if(pesan!=PONG){
					String [] pesanSplit = pesan.split("#");
					if(pesanSplit.length>0){
						String key = pesanSplit[0];
						if(key!=null && key.equals("") && mapHandler!=null){
							Handler handler = mapHandler.get(key);
							if(handler!=null){
								handler.sendMessage(pesan);
								removeHandler(key);
							}	
						}		
					}
				}
			}catch (IOException e) {
				stop();
				e.printStackTrace();
			}
			
		}
	}
	
	private Map<String,Handler> mapHandler;
	private boolean start = false;
	private final BufferedReader buff;
	private final static String PONG = "Y";

}
