package org.ade.monak.server;


public class MonakServer {

	public static void main(String [] args){
		RunnableKontrol kontrol = new RunnableKontrol();
		kontrol.startServer();
		System.out.println("taek");
	}	
}
