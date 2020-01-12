package network.common.networkMessageHandler;

import network.common.IMsgApplicator;
import network.common.networkMessages.NetworkMsg;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class NetworkMsgHandler<TNetworkMessage extends NetworkMsg> {

	protected IMsgApplicator<TNetworkMessage> applicator;

	public NetworkMsgHandler(IMsgApplicator<TNetworkMessage> applicator){
		this.applicator = applicator;
	}
	public abstract void handleMsg(DataInputStream dis) throws IOException;
}
