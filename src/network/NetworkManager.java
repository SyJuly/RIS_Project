package network;

import network.networkMessages.NetworkMsg;
public class NetworkManager {

    private NetworkCommunicator communicator;

    public NetworkManager(NetworkCommunicator communicator){
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
        communicator.send(msg);
    }
}
