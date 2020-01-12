package network.common.connectionWorker;

import network.common.networkMessageHandler.NetworkMsgHandler;
import network.common.networkMessages.NetworkMsg;

import java.net.Socket;
import java.util.Map;

public class ConnectionWorker {

    private Map<Integer, NetworkMsgHandler> msgHandlers;
    private Socket clientSocket;
    private ConnectionInputWorker inputWorker;
    private ConnectionOutputWorker outputWorker;
    public long id = 0;

    public ConnectionWorker(Socket clientSocket, Map<Integer, NetworkMsgHandler> msgHandlers) {
        this.clientSocket = clientSocket;
        this.msgHandlers = msgHandlers;
    }

    public void start() {
        inputWorker= new ConnectionInputWorker(clientSocket, msgHandlers);
        outputWorker= new ConnectionOutputWorker(clientSocket);
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