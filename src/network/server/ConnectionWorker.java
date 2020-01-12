package network.server;

import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessages.NetworkMsg;

import java.net.Socket;
import java.util.Map;

public class ClientWorker {

    private Map<Integer, NetworkMsgHandler> msgHandlers;
    private Socket clientSocket = null;
    private ClientInputWorker inputWorker;
    private ClientOutputWorker outputWorker;
    public long id = 0;

    public ClientWorker(Socket clientSocket, Map<Integer, NetworkMsgHandler> msgHandlers) {
        this.clientSocket = clientSocket;
        this.msgHandlers = msgHandlers;
    }

    public void start() {
        inputWorker= new ClientInputWorker(clientSocket, msgHandlers);
        outputWorker= new ClientOutputWorker(clientSocket);
        Thread inputWorkerThread = new Thread(inputWorker);
        Thread outputWorkerThread = new Thread(outputWorker);
        inputWorkerThread.start();
        outputWorkerThread.start();
    }

    public void stop(){
        inputWorker.stop();
        outputWorker.stop();
    }

    public void send(NetworkMsg msg) {
        outputWorker.send(msg);
    }
}