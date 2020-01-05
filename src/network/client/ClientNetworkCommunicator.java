package network.client;

import network.NetworkCommunicator;
import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessages.NetworkMsg;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.io.IOException;
import java.util.Map;

public class ClientNetworkCommunicator extends NetworkCommunicator{

    protected Socket clientSocket = null;

    public ClientNetworkCommunicator(Map<Integer, NetworkMsgHandler> msgHandlers) {
        super(msgHandlers);
    }

    public void run() {
        System.out.println("Starting client.");
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        try {
            clientSocket = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!isStopped()) {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                if(inputStream.available() > 0){
                    System.out.println("inputstream available: " + inputStream.available() );
                    DataInputStream dis = new DataInputStream((inputStream));

                    int msgCode = dis.readInt();
                    if(msgHandlers.containsKey((msgCode))) {
                        msgHandlers.get(msgCode).handleMsg(dis);
                    } else {
                        System.out.println("Code not found: " + msgCode);
                    }

                    //WorldMsg msg = new WorldMsg(inputStream);
                    //System.out.println("WE DID IT: " + msg.centralX + "| " + msg.id);
                }
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("ClientNetworkCommunicator Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error connecting client", e);
            }
        }
        System.out.println("ClientNetworkCommunicator Stopped.");
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    @Override
    protected void send(NetworkMsg networkMsg) {

    }
}