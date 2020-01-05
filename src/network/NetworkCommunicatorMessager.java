package network;

import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessages.NetworkMsg;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class NetworkCommunicatorMessager extends NetworkCommunicator {

    private Queue<NetworkMsg> messagesToSend;

    public NetworkCommunicatorMessager(Map<Integer, NetworkMsgHandler> msgHandlers) {
        super(msgHandlers);
        messagesToSend = new ConcurrentLinkedQueue<>();
    }

    protected synchronized void handleIncomingMessages(InputStream inputStream) throws IOException {
        if(inputStream.available() > 0){
            System.out.println("inputstream available: " + inputStream.available() );
            DataInputStream dis = new DataInputStream((inputStream));

            int msgCode = dis.readInt();
            if(msgHandlers.containsKey((msgCode))) {
                msgHandlers.get(msgCode).handleMsg(dis);
            } else {
                System.out.println("Code not found: " + msgCode);
            }
        }
    }

    protected synchronized void handleOutgoingMessages(OutputStream output) throws IOException{
        for (NetworkMsg msg : messagesToSend) {
            msg.serialize(output);
        }
    }

    @Override
    public void send(NetworkMsg networkMsg) {
        messagesToSend.add(networkMsg);
    }
}
