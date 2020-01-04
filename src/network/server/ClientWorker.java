package network.server;

import network.networkMessages.WorldMsg;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientWorker implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public ClientWorker(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();

            WorldMsg worldMsg = new WorldMsg(0);
            System.out.println("Serializing world.");
            worldMsg.serialize(output);

            /*int result = input.read();
            System.out.println(result);
            long time = System.currentTimeMillis();*/
            output.close();
            output.flush();
            input.close();
            //System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}