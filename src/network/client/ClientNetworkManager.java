package network.client;

import network.networkMessageHandler.NetworkMsgHandler;
import java.util.Map;

public class ClientNetworkManager {

    private final int PORT = 8080;
    private Map<Integer, NetworkMsgHandler> msgHandlers;
    private ClientNetworkCommunicator communicator;

    public ClientNetworkManager(Map<Integer, NetworkMsgHandler> msgHandlers){
        this.msgHandlers = msgHandlers;
    }

    public void start(){
        communicator = new ClientNetworkCommunicator(PORT, msgHandlers);
        Thread thread = new Thread(communicator);
        thread.start();
    }

    public void stop(){
        System.out.println("Stopping ClientNetworkManager.");
        communicator.stop();
    }
}
