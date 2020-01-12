package network;

import network.networkMessages.NetworkMsg;
import network.server.ServerNetworkCommunicator;

public class ServerNetworkManager {

    private ServerNetworkCommunicator communicator;

    public ServerNetworkManager(ServerNetworkCommunicator communicator){
        this.communicator = communicator;
    }

    public void start(){
        Thread thread = new Thread(communicator);
        thread.start();
    }

    public void stop(){
        System.out.println("Stopping NetworkManager.");
        communicator.stop();
    }

    public void sendMsg(NetworkMsg msg) {
        communicator.sendMsgToAllClients(msg);
    }
}
