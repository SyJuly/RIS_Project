package network.networkMessageHandler;

import network.IMsgApplicator;
import network.networkMessages.NetworkMsg;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class NetworkMsgHandler<TNetworkMessage extends NetworkMsg> {

	protected IMsgApplicator<TNetworkMessage> recipient;

	public NetworkMsgHandler(IMsgApplicator<TNetworkMessage> recipient){
		this.recipient = recipient;
	}
	public abstract void handleMsg(DataInputStream dis) throws IOException;
}
