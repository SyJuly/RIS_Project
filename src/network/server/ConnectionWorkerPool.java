package network.server;

import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessages.NetworkMsg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerNetworkCommunicator implements Runnable{

  protected int port = 8080;
  protected Thread runningThread = null;
  private Map<Integer, NetworkMsgHandler> msgHandlers;
  protected ServerSocket serverSocket = null;
  protected boolean isStopped = false;

  private List<ConnectionWorker> clientWorkers;

  public ServerNetworkCommunicator(Map<Integer, NetworkMsgHandler> msgHandlers){
    this.clientWorkers = new ArrayList<>();
    this.msgHandlers = msgHandlers;
  }

  public void run(){
    System.out.println("Starting server on port: " + port);
    synchronized(this){
      this.runningThread = Thread.currentThread();
    }
    openServerSocket();
    while(! isStopped()){
      Socket clientSocket = null;
      try {
        clientSocket = this.serverSocket.accept();
        setUpIncomingClient(clientSocket);
      } catch (IOException e) {
        if(isStopped()) {
          System.out.println("ServerNetworkCommunicator Stopped.") ;
          return;
        }
        throw new RuntimeException(
                "Error accepting client connection", e);
      }
    }
    System.out.println("ServerNetworkCommunicator Stopped.") ;
  }

  protected synchronized boolean isStopped() {
    return this.isStopped;
  }

  public synchronized void stop(){
    this.isStopped = true;
    try {
      System.out.println("Stopping ServerNetworkCommunicator");
      for (ConnectionWorker worker: clientWorkers) {
        worker.stop();
      }
      this.serverSocket.close();
    } catch (IOException e) {
      throw new RuntimeException("Error closing server", e);
    }
  }

  private void openServerSocket() {
    try {
      this.serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      throw new RuntimeException("Cannot open port " + port, e);
    }
  }

  private void setUpIncomingClient(Socket clientSocket){
    ConnectionWorker worker = new ConnectionWorker(clientSocket, msgHandlers);
    clientWorkers.add(worker);
    worker.start();
  }

  public void sendMsgToAllClients(NetworkMsg msg) {
    for (ConnectionWorker worker: clientWorkers) {
      worker.send(msg);
    }
  }
}
