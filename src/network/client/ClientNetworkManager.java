package network.client;

import network.MsgType;
import network.networkMessageHandler.NetworkMsgHandler;

import java.util.HashMap;
import java.util.Map;

public class ClientNetworkManager {

    private final int PORT = 8080;
    private Map<String, NetworkMsgHandler> msgHandlers;
    private ClientNetworkCommunicator communicator;

    public ClientNetworkManager(){
        msgHandlers = new HashMap();
    }

    public void start(){
        communicator = new ClientNetworkCommunicator(PORT);
        Thread thread = new Thread(communicator);
        thread.start();
    }

    public void stop(){
        System.out.println("Stopping ClientNetworkManager.");
        communicator.stop();
    }

    public void register(NetworkMsgHandler msgHandler, MsgType type){
        msgHandlers.put(type.getCode(), msgHandler);
    }

    public void receiveMessage(byte[] message, String networkCode){
        //msgHandlers.get(networkCode).handleMsg();
    }
}
