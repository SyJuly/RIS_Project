package network.client;

import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessages.NetworkMsg;
import network.server.ConnectionWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ClientNetworkManager {

    private Map<Integer, NetworkMsgHandler> msgHandlers;
    private ConnectionWorker connectionWorker = null;

    public ClientNetworkManager(Map<Integer, NetworkMsgHandler> msgHandlers) {
        this.msgHandlers = msgHandlers;
    }

    public void start() {
        System.out.println("Starting client at port " + 8080);
        try {
            Socket clientSocket = new Socket("localhost", 8080);
            connectionWorker = new ConnectionWorker(clientSocket, msgHandlers);
            connectionWorker.start();
        } catch (IOException e) {
            if(connectionWorker != null){
                connectionWorker.stop();
            }
            throw new RuntimeException("Error in ClientNetworkManager, stopping connection worker.", e);
        }
    }

    public void sendMsg(NetworkMsg msg) {
        connectionWorker.send(msg);
    }
}