package network.server;

import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessages.NetworkMsg;
import java.util.HashMap;
import java.util.Map;

public class ServerNetworkManager{

	private final int PORT = 8080;
	private Map<String, NetworkMsgHandler> msgHandlers;
	private ServerNetworkCommunicator communicator;

	public ServerNetworkManager(){
		msgHandlers = new HashMap();
	}

	public void start(){
		communicator = new ServerNetworkCommunicator(PORT);
		Thread thread = new Thread(communicator);
		thread.start();
	}

	public void stop(){
		communicator.stop();
	}

	public void sendMsg(NetworkMsg msg) {
		communicator.sendMsgToAllClients(msg);
	}
}
