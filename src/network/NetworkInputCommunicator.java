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

public abstract class NetworkMsgInputHandler extends NetworkCommunicator {

    private Queue<NetworkMsg> messagesToSend;

    public NetworkMsgInputHandler(Map<Integer, NetworkMsgHandler> msgHandlers) {
        super(msgHandlers);
        messagesToSend = new ConcurrentLinkedQueue<>();
    }

    protected void handleIncomingMessages(InputStream inputStream) throws IOException {
        /*
        *PushbackInputStream pbi = new PushbackInputStream(inputStream, 1);
        int singleByte;
        DataInputStream dis = new DataInputStream(pbi);
        while((singleByte = pbi.read()) != -1) {
            pbi.unread(singleByte);
         */
        synchronized (inputStream) {
            if (inputStream.available() > 0) {
                //System.out.println("inputstream available: " + inputStream.available());
                DataInputStream dis = new DataInputStream((inputStream));

                int msgCode = dis.readInt();
                if (msgHandlers.containsKey((msgCode))) {
                    System.out.println("got msg: " + msgHandlers.get(msgCode));
                    msgHandlers.get(msgCode).handleMsg(dis);
                } else {
                    System.out.println("Code not found: " + msgCode);
                }
            }
        }
    }

    protected void handleOutgoingMessages(OutputStream output) throws IOException{
        synchronized (output) {
            while(messagesToSend.size()> 0){
                messagesToSend.poll().serialize(output);
            }
        }
    }

    @Override
    public void send(NetworkMsg networkMsg) {
        messagesToSend.add(networkMsg);
    }
}
