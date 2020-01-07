package network.client;

import network.NetworkCommunicatorMessager;
import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessages.JoinMsg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class ClientNetworkCommunicator extends NetworkCommunicatorMessager {
    private boolean sentJoinMsg = false;

    public ClientNetworkCommunicator(Map<Integer, NetworkMsgHandler> msgHandlers) {
        super(msgHandlers);
    }

    public void run() {
        System.out.println("Starting client at port " + port);
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        try (Socket clientSocket = new Socket("localhost", port);
             InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()){
             sendJoinMsg(outputStream);
            while (!isStopped()) {
                handleOutgoingMessages(outputStream);
                handleIncomingMessages(inputStream);
            }
        } catch (IOException e) {
            if (isStopped()) {
                System.out.println("ClientNetworkCommunicator stopped with Exception: " + e );
                return;
            }
            throw new RuntimeException("Error connecting client", e);
        }
        System.out.println("ClientNetworkCommunicator stopped running.");
    }

    private void sendJoinMsg(OutputStream outputStream) throws IOException {
        synchronized (outputStream){
            sentJoinMsg = true;
            JoinMsg msg = new JoinMsg("readyplayerone");
            msg.serialize(outputStream);
        }
    }

    public synchronized void stop() {
        this.isStopped = true;
    }
}