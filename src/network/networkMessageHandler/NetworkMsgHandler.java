package network.networkMessageHandler;

import network.networkMessages.NetworkMsg;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class NetworkMsgHandler {
	public abstract void handleMsg(DataInputStream dis) throws IOException;
}
