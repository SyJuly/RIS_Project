package network.networkMessageHandler;

import network.networkMessages.NetworkMsg;

public abstract class NetworkMsgHandler {
	public abstract void handleMsg(NetworkMsg msg);
}
