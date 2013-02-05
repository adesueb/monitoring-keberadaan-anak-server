package org.ade.monak.server;


import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class KoneksiPushFactory {

	
	
	public Map<String, Socket> getOrtuKoneks() {
		return ortuKoneks;
	}

	public void setOrtuKoneks(Map<String, Socket> ortuKoneks) {
		this.ortuKoneks = ortuKoneks;
	}

	private Map<String, Socket> ortuKoneks = new HashMap<String, Socket>();
}
