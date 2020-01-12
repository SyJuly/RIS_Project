package network.common.communicator;

import network.common.networkMessages.NetworkMsg;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class NetworkOutputCommunicator extends NetworkCommunicator {

    private Queue<NetworkMsg> messagesToSend;

    public NetworkOutputCommunicator() {
        messagesToSend = new ConcurrentLinkedQueue<>();
    }

    protected void handleOutgoingMessages(OutputStream output) throws IOException{
        synchronized (output) {
            while(messagesToSend.size()> 0){
                NetworkMsg networkMsg = messagesToSend.poll();
                synchronized (networkMsg){
                    networkMsg.serialize(output);
                }
                output.flush();
                System.out.println("send msg: " + networkMsg.msgType + " from " + id);
            }
        }
    }

    public void send(NetworkMsg networkMsg) {
        messagesToSend.add(networkMsg);
    }
}
