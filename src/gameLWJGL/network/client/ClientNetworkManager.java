package gameLWJGL.network.client;

import gameLWJGL.network.common.IMsgApplicator;
import gameLWJGL.network.common.networkMessageHandler.NetworkMsgHandler;
import gameLWJGL.network.common.networkMessages.NetworkMsg;
import gameLWJGL.network.common.connectionWorker.ConnectionWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ClientNetworkManager {

    private final List<IMsgApplicator> msgSenders;
    private Map<Integer, NetworkMsgHandler> msgHandlers;
    private ConnectionWorker connectionWorker = null;

    public ClientNetworkManager(Map<Integer, NetworkMsgHandler> msgHandlers, List<IMsgApplicator> msgSenders) {
        this.msgHandlers = msgHandlers;
        this.msgSenders = msgSenders;
    }

    public void sendMessages(){
        for (IMsgApplicator msgApplicator: msgSenders) {
            if(msgApplicator.shouldSendMessage()){
                NetworkMsg msg = msgApplicator.getMessage();
                sendMsg(msg);
            }
        }
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