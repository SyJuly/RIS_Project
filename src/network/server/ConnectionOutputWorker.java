package network.server;

import network.NetworkOutputCommunicator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionOutputWorker extends NetworkOutputCommunicator {

    public ConnectionOutputWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;

    }

    public void run() {
        try (OutputStream outputStream = clientSocket.getOutputStream()){
            while (!isStopped()) {
                handleOutgoingMessages(outputStream);
            }
        } catch (IOException e) {
            if (isStopped()) {
                System.out.println("Output worker stopped with Exception: " + e );
                return;
            }
            throw new RuntimeException("Error connecting in output worker", e);
        }
        System.out.println("Output worker stopped running.");
    }
}
