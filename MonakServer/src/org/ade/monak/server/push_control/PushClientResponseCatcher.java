package org.ade.monak.server.push_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.ade.monak.server.util.Handler;

/*
 * catch all response from client in channel push....
 */
public class PushClientResponseCatcher implements Runnable{

	public PushClientResponseCatcher(BufferedReader buff){
		this.buff = buff;
	}
	
	// stop if necessary ex: channel change from bufferedreder to normal and progressive read 
	public void stopAndClearAll(){
		mapHandler.clear();
		mapHandler = null;
	}
		
	// when add handler if the thread did'nt start, so start the thread...
	public void addHandler(String key, Handler handler){
		
		if(mapHandler!=null){
			mapHandler = new HashMap<String, Handler>();
		}
		
		synchronized (this) {
			mapHandler.put(key, handler);	
			if(!isStart){
				start();
			}
		}
	}
	
	// send fail to all handler when connection is lost...
	private void failAllHandler(){
		for(Handler handler:mapHandler.values()){
			handler.sendMessage(FAIL);
		}
	}

	private void start(){
		new Thread(this).start();	
		isStart = true;
	}
	
	// remove handler is used local....
	private void removeHandler(String key){
		if(mapHandler!=null){
			if(mapHandler!=null){
				mapHandler.remove(key);
			}	
		}
	}
	
	@Override
	public void run() {
	
		while(true){
			
			synchronized (this) {
				try {
					if(mapHandler.isEmpty()){
						isStart = false;
						break;
					}else{
						
						isStart = true;
						
						String pesan = buff.readLine();
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
					e.printStackTrace();
					failAllHandler();
					isStart = false;
					break;
				}
			}
		}
		
	}
	
	private boolean isStart = false;
	private Map<String,Handler> mapHandler;
	private final BufferedReader buff;
	private final static String PONG = "Y";
	public final static String FAIL = "fail";

}
