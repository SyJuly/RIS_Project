package gameLWJGL.network.common.communicator;

import gameLWJGL.network.common.networkMessageHandler.NetworkMsgHandler;

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
        synchronized (inputStream) {
            DataInputStream dis = new DataInputStream((inputStream));

            int msgCode = dis.readInt();
            if (msgHandlers.containsKey((msgCode))) {
                //System.out.println("got msg: " + msgHandlers.get(msgCode));
                msgHandlers.get(msgCode).handleMsg(dis);
            } else {
                System.out.println("Code not found: " + msgCode);
            }
        }
    }
}
