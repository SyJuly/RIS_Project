package network.server;

import network.NetworkCommunicatorMessager;
import network.networkMessageHandler.NetworkMsgHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class ClientWorker extends NetworkCommunicatorMessager {

    protected Socket clientSocket = null;
    protected String serverText   = null;
    public long id = 0;

    public ClientWorker(Socket clientSocket, String serverText, Map<Integer, NetworkMsgHandler> msgHandlers) {
        super(msgHandlers);
        this.clientSocket = clientSocket;
        this.serverText   = serverText;

    }

    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()){
            while (!isStopped()) {
                handleOutgoingMessages(outputStream);
                handleIncomingMessages(inputStream);
            }
        } catch (IOException e) {
            if (isStopped()) {
                System.out.println("Client worker stopped with Exception: " + e );
                return;
            }
            throw new RuntimeException("Error connecting client", e);
        }
        System.out.println("Client worker stopped running.");
    }

    public boolean isStopped() {
        if(clientSocket.isClosed()) {
            System.out.println("isStopped() returns true, because socket was closed, isStopped == " + isStopped);
            return true;
        }
        return super.isStopped();
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            System.out.println("Stopping ServerNetworkCommunicator");
            this.clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
}