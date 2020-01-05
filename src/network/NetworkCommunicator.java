package network;

import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessages.NetworkMsg;

import java.util.Map;

public abstract class NetworkCommunicator implements Runnable{
    protected int port = 8080; //default if port isn't set
    protected Map<Integer, NetworkMsgHandler> msgHandlers;
    protected Thread runningThread = null;
    protected boolean isStopped = false;

    public NetworkCommunicator(Map<Integer, NetworkMsgHandler> msgHandlers){
        this.msgHandlers = msgHandlers;
    }

    protected synchronized boolean isStopped() {
        return this.isStopped;
    }

    protected void setPort(int port){
        this.port = port;
    }

    protected abstract void send(NetworkMsg networkMsg);
    protected abstract void stop();
}
