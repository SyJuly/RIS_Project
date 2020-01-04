package network.server;

import network.MsgType;
import network.networkMessageHandler.NetworkMsgHandler;
import java.util.HashMap;
import java.util.Map;

public class ServerNetworkManager {

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

		try {
			while(thread.isAlive()){
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Stopping server communicator.");
			communicator.stop();
		}
	}

	public void register(NetworkMsgHandler msgHandler, MsgType type){
		//msgHandlers.put(type.getCode(), msgHandler);
	}

	public void receiveMessage(byte[] message, String networkCode){
		//msgHandlers.get(networkCode).handleMsg();
	}
}
