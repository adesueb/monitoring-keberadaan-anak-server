package org.ade.monak.server.test;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class RequestSender implements Runnable{
	public static void main(String [] args){
		new Thread(new RequestSender()).start();
	}

	@Override
	public void run() {
		try {
			Socket socket = new Socket("localhost", 2525);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.write("1717#test 1 2 3\n".getBytes());
			System.out.println("masuk");
			dos.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
