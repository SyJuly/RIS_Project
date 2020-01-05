package network.server;

import network.networkMessages.NetworkMsg;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientWorker implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;
    protected boolean isStopped   = false;
    public long id = 0;

    private Queue<NetworkMsg> messagesToSend;

    public ClientWorker(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
        messagesToSend = new ConcurrentLinkedQueue<>();
    }

    public void run() {
        System.out.println();
        while(! isStopped()) {
            try {
                InputStream input = clientSocket.getInputStream();

                OutputStream output = clientSocket.getOutputStream();
                for (NetworkMsg msg : messagesToSend) {
                    msg.serialize(output);
                }
            /*WorldMsg worldMsg = new WorldMsg(0);
            System.out.println("Serializing world.");
            worldMsg.serialize(output);*/

            /*int result = input.read();
            System.out.println(result);
            long time = System.currentTimeMillis();*/
                output.close();
                input.close();
                //System.out.println("Request processed: " + time);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
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


    public void sendMsg(NetworkMsg msg) {
        messagesToSend.add(msg);
    }
}