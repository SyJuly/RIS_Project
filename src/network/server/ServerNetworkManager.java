package network.server;

import network.common.IMsgApplicator;
import network.common.networkMessageHandler.NetworkMsgHandler;
import network.common.networkMessages.NetworkMsg;

import java.util.List;
import java.util.Map;

public class ServerNetworkManager {

    private ConnectionWorkerPool connectionWorkerPool;
    private List<IMsgApplicator> msgSenders;

    private boolean firedFirstPlayerArrivalEvent = false;

    public ServerNetworkManager(Map<Integer, NetworkMsgHandler> msgHandlers, List<IMsgApplicator> msgSenders){
        this.connectionWorkerPool = new ConnectionWorkerPool(msgHandlers);
        this.msgSenders = msgSenders;
    }

    public void sendMessages(){
        for (IMsgApplicator msgApplicator: msgSenders) {
            if(connectionWorkerPool.newPlayerConnected){
                NetworkMsg msg = msgApplicator.getStartMessage();
                if(msg != null){
                    sendMsg(msg);
                }
            }
            if(msgApplicator.shouldSendMessage()){
                NetworkMsg msg = msgApplicator.getMessage();
                sendMsg(msg);
            }
        }
        connectionWorkerPool.newPlayerConnected = false;
    }

    public void start(){
        Thread thread = new Thread(connectionWorkerPool);
        thread.start();
    }

    public void stop(){
        System.out.println("Stopping ServerNetworkManager.");
        connectionWorkerPool.stop();
    }

    private void sendMsg(NetworkMsg msg) {
        connectionWorkerPool.sendMsgToAllClients(msg);
    }

    public boolean firstPlayerHasConnected(){
        if(!firedFirstPlayerArrivalEvent && connectionWorkerPool.newPlayerConnected){
            firedFirstPlayerArrivalEvent = true;
            return true;
        }
        return false;
    }
}
