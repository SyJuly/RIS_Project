package network.client;

import java.net.Socket;
import java.io.IOException;

public class ClientNetworkCommunicator implements Runnable{
    private int port = 8080;
    protected boolean isStopped = false;
    protected Thread runningThread = null;
    protected Socket clientSocket = null;

    public ClientNetworkCommunicator(int port) {
        this.port = port;
    }

    public void run() {
        System.out.println("Starting client.");
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        while (!isStopped()) {
            try {
                clientSocket = new Socket("localhost", port);
                clientSocket.getOutputStream().write(4);
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


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
}