package network.server;

import network.NetworkInputCommunicator;
import network.networkMessageHandler.NetworkMsgHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class ConnectionInputWorker extends NetworkInputCommunicator {

    protected Socket clientSocket = null;

    public ConnectionInputWorker(Socket clientSocket, Map<Integer, NetworkMsgHandler> msgHandlers) {
        super(msgHandlers);
        this.clientSocket = clientSocket;

    }

    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream()){
            while (!isStopped()) {
                handleIncomingMessages(inputStream);
            }
        } catch (IOException e) {
            if (isStopped()) {
                System.out.println("Input worker stopped with Exception: " + e );
                return;
            }
            throw new RuntimeException("Error connecting in output worker", e);
        }
        System.out.println("Input worker stopped running.");
    }
}
