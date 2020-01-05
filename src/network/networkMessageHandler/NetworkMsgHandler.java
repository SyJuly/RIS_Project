package network.networkMessageHandler;

import network.IMsgRecipient;
import network.networkMessages.NetworkMsg;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class NetworkMsgHandler<TNetworkMessage extends NetworkMsg> {

	protected IMsgRecipient<TNetworkMessage> recipient;

	public NetworkMsgHandler(IMsgRecipient<TNetworkMessage> recipient){
		this.recipient = recipient;
	}
	public abstract void handleMsg(DataInputStream dis) throws IOException;
}
