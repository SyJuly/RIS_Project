package network.networkMessageHandler;

import network.networkMessages.MoveMsg;
import network.networkMessages.NetworkMsg;

public class MoveMsgHandler extends NetworkMsgHandler{

	@Override
	public void handleMsg(NetworkMsg msg) {
		MoveMsg moveMsg = (MoveMsg) msg;
		System.out.println("Received moveMsg");
	}
	

}
