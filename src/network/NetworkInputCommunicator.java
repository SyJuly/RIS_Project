package network;

import network.networkMessageHandler.NetworkMsgHandler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public abstract class NetworkInputCommunicator extends NetworkCommunicator {
    protected Map<Integer, NetworkMsgHandler> msgHandlers;

    public NetworkInputCommunicator(Map<Integer, NetworkMsgHandler> msgHandlers) {
        this.msgHandlers = msgHandlers;
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
}
