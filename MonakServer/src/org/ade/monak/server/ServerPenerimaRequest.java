package org.ade.monak.server;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ade
 */
public class ServerPenerimaRequest implements Runnable{

    public ServerPenerimaRequest(Socket socket, KoneksiPushFactory koneksiPush){
    	this.socket			= socket;
    	this.koneksiPush	= koneksiPush;
    }
    
    public void run() {
        BufferedReader in = null;
        try {
            
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String    []    kiriman = in.readLine().split("#");
            DataOutputStream dosRequest = new DataOutputStream(socket.getOutputStream());

            Socket socket = koneksiPush.getOrtuKoneks().get(kiriman[0]);
            
            if(socket.isConnected()){
				dosRequest.write(("sukses"+"\n").getBytes());
				dosRequest.flush();
				dosRequest.close();
            	in.close();
	            this.socket.close();
	            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	            dos.write((kiriman[1]+"\n").getBytes());
	            System.out.print("ayolah");
	            System.out.print(kiriman[1]);
	            dos.flush();	
			}else{

	            dosRequest.write(("gagal"+"\n").getBytes());
	            dosRequest.flush();
	            dosRequest.close();
	            in.close();
	            this.socket.close();
			}
            
        } catch (IOException ex) {
            Logger.getLogger(ServerPenerimaRequest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           
        }
    }
    
    private final Socket             	socket;
    private final KoneksiPushFactory		koneksiPush;
   
}
