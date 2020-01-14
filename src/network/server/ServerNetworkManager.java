package network.server;

import network.common.IMsgApplicator;
import network.common.networkMessageHandler.NetworkMsgHandler;
import network.common.networkMessages.NetworkMsg;

import java.util.List;
import java.util.Map;

public class ServerNetworkManager {

    private ConnectionWorkerWrapper connectionWorkerWrapper;
    private List<IMsgApplicator> msgSenders;

    private boolean firedFirstPlayerArrivalEvent = false;

    public ServerNetworkManager(Map<Integer, NetworkMsgHandler> msgHandlers, List<IMsgApplicator> msgSenders){
        this.connectionWorkerWrapper = new ConnectionWorkerWrapper(msgHandlers);
        this.msgSenders = msgSenders;
    }

    public void sendMessages(){
        for (IMsgApplicator msgApplicator: msgSenders) {
            if(connectionWorkerWrapper.newPlayerConnected){
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
        connectionWorkerWrapper.newPlayerConnected = false;
    }

    public void start(){
        Thread thread = new Thread(connectionWorkerWrapper);
        thread.start();
    }

    public void stop(){
        System.out.println("Stopping ServerNetworkManager.");
        connectionWorkerWrapper.stop();
    }

    private void sendMsg(NetworkMsg msg) {
        connectionWorkerWrapper.sendMsgToAllClients(msg);
    }

    public boolean firstPlayerHasConnected(){
        if(!firedFirstPlayerArrivalEvent && connectionWorkerWrapper.newPlayerConnected){
            firedFirstPlayerArrivalEvent = true;
            return true;
        }
        return false;
    }
}
