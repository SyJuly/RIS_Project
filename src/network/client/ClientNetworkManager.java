package network.client;

import network.NetworkInputCommunicator;
import network.networkMessageHandler.NetworkMsgHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class ClientNetworkCommunicator {

    private Map<Integer, NetworkMsgHandler> msgHandlers;

    public ClientNetworkCommunicator(Map<Integer, NetworkMsgHandler> msgHandlers) {
        this.msgHandlers = msgHandlers;
    }

    public void run() {
        System.out.println("Starting client at port " + port);
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        try (Socket clientSocket = new Socket("localhost", port);
             InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()){
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

}