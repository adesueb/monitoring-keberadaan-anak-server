package org.ade.monak.server.push;

public class PushService {

	public PushService(){

		pushFactory				= new PushFactory(PORT_PUSH);
		pushMessageRouter		= new PushMessageRouter(this, PORT_REQUEST);
	}
	
	public void startServer(){

		pushFactory.startServer();
		pushMessageRouter.startServer();
		
		start = true;
	}
		
	public void stopServer(){
		pushMessageRouter.stopServer();
		pushFactory.stopServer();
		start = false;
	}
	
	
	public boolean isStart(){
		return start;
	}
	
	
	// getters...........................................................
	public PushMessageRouter getPushMessageRouter() {
		return pushMessageRouter;
	}

	public PushFactory getPushFactory() {
		return pushFactory;
	}
	//......................................................................



	private static final int PORT_PUSH	= 4442;
	private static final int PORT_REQUEST	= 2525;

	private PushMessageRouter		pushMessageRouter;
	private PushFactory				pushFactory;
	
	private boolean start;
	
}
