package network.server;

import network.common.networkMessageHandler.NetworkMsgHandler;
import network.common.networkMessages.NetworkMsg;

import java.util.Map;

public class ServerNetworkManager {

    private ConnectionWorkerPool connectionWorkerPool;

    public ServerNetworkManager(Map<Integer, NetworkMsgHandler> msgHandlers){
        this.connectionWorkerPool = new ConnectionWorkerPool(msgHandlers);
    }

    public void start(){
        Thread thread = new Thread(connectionWorkerPool);
        thread.start();
    }

    public void stop(){
        System.out.println("Stopping ServerNetworkManager.");
        connectionWorkerPool.stop();
    }

    public void sendMsg(NetworkMsg msg) {
        connectionWorkerPool.sendMsgToAllClients(msg);
    }
}
