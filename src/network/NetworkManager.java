package network;

import network.networkMessageHandler.NetworkMsgHandler;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager {

	private Map<String, NetworkMsgHandler> msgHandlers;

	public NetworkManager(){
		msgHandlers = new HashMap();
	}

	public void register(NetworkMsgHandler msgHandler, MsgType type){
		msgHandlers.put(type.getCode(), msgHandler);
	}

	public void receiveMessage(byte[] message, String networkCode){
		//msgHandlers.get(networkCode).handleMsg();
	}
}
