package org.ade.monak.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackendRequest implements Runnable{

	public BackendRequest(){

		backendPush	= new BackendPush();
		
		try {
			this.serverRequest 	= new ServerSocket(PORT_REQUEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void startServer(){
		jalan = true;
		threadRequest = new Thread(this);
		threadRequest.start();
		backendPush.startServer();
		
	}
	
	public BackendPush getBackendPush(){
		return backendPush;
	}

	
	public void stopServer(){
		jalan = false;
		try {
			serverRequest.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadRequest.stop();
		backendPush.stopServer();
	}
	
	public void run() {
		while(jalan){
			try {
				Socket socket = serverRequest.accept();

				new Thread(new ServerPenerimaRequest
						(socket, backendPush.getMapPush()))
							.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
	}

	private Thread				threadRequest;
	private BackendPush			backendPush;
	private static final int 	PORT_REQUEST	= 2525;
	private ServerSocket	 	serverRequest;
	private boolean jalan;
	
	private final static String SUKSES = "Y";
	
	private static class ServerPenerimaRequest implements Runnable{

	    public ServerPenerimaRequest(Socket socket, Map<String, Socket> mapPush){
	    	this.socketRequest		= socket;
	    	this.mapPush	= mapPush;
	    	try {
				dosRequest = new DataOutputStream(socketRequest.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		       
	    }
	    
	    public void run() {
	        BufferedReader buffRequest = null;
	           
	        try {
	            
	            buffRequest 			= new BufferedReader(new InputStreamReader(this.socketRequest.getInputStream()));
	            String    []    kiriman = buffRequest.readLine().split("#");
	            idKoneksi 				= kiriman[0];
	            Socket socketPush 		= mapPush.get(idKoneksi);
	            
	            if(socketPush!=null && socketPush.isConnected()){
					DataOutputStream dosPush = new DataOutputStream(socketPush.getOutputStream());
		            dosPush.write((kiriman[1]+"\n").getBytes());
		            dosPush.flush();
		            BufferedReader buffPush = new BufferedReader(new InputStreamReader(socketPush.getInputStream()));
			        String pesan = buffPush.readLine();
			        if(pesan!=null&&pesan.equals(SUKSES)){
			        	dosRequest.write(("sukses"+"\n").getBytes());
						dosRequest.flush();    	
			            System.out.print(SUKSES);
			        }else{
			        	
			        	 dosRequest.write(("gagal"+"\n").getBytes());
			        	 dosRequest.flush();
			        	 buffPush.close();
			             socketPush = null;
			             mapPush.remove(idKoneksi);
			        }
			        dosRequest.close();
	            	buffRequest.close();
		            this.socketRequest.close();
		            System.out.print(kiriman[1]+pesan);
		            
				}else{
					
		            dosRequest.write(("gagal"+"\n").getBytes());
		            dosRequest.flush();
		            dosRequest.close();
		            buffRequest.close();
		            socketRequest.close();
		            socketPush = null;
		            mapPush.remove(idKoneksi);
				}
	            
	        } catch(SocketTimeoutException e){	
	        	try {
					dosRequest.write(("gagal"+"\n").getBytes());
		        	dosRequest.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	        	mapPush.remove(idKoneksi);
			}catch (IOException ex) {
	            Logger.getLogger(ServerPenerimaRequest.class.getName()).log(Level.SEVERE, null, ex);
	        } finally {
	           
	        }
	    }
	    
	    private final Socket             	socketRequest;
	    private final Map<String, Socket>	mapPush;
	    private String 						idKoneksi;
	    private DataOutputStream 			dosRequest;
	}
}	
