package gameLWJGL.network.common.communicator;

import java.io.IOException;
import java.net.Socket;

public abstract class NetworkCommunicator implements Runnable{
    protected Socket clientSocket = null;
    protected Thread runningThread = null;
    protected int id;
    protected boolean isStopped = false;

    protected synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            System.out.println("Stopping NetworkCommunicator");
            this.clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
}
