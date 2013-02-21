package org.ade.monak.server.util;

public abstract class Handler {

	public abstract void handlerMessage(String message);
	public void sendMessage(String message){
		handlerMessage(message);
	}
}
