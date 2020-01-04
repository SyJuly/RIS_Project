package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetworkCommunicator implements Runnable{

  protected int          serverPort   = 8080;
  protected ServerSocket serverSocket = null;
  protected boolean      isStopped    = false;
  protected Thread       runningThread= null;

  public ServerNetworkCommunicator(int port){
    this.serverPort = port;
  }

  public void run(){
    System.out.println("Starting server on port: " + serverPort);
    synchronized(this){
      this.runningThread = Thread.currentThread();
    }
    openServerSocket();
    while(! isStopped()){
      Socket clientSocket = null;
      try {
        clientSocket = this.serverSocket.accept();
        new Thread(
                new ClientWorker(
                        clientSocket, "Multithreaded ServerNetworkCommunicator")
        ).start();
        System.out.println("created new worker");
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


  private synchronized boolean isStopped() {
    return this.isStopped;
  }

  public synchronized void stop(){
    this.isStopped = true;
    try {
      System.out.println("Stopping ServerNetworkCommunicator");
      this.serverSocket.close();
    } catch (IOException e) {
      throw new RuntimeException("Error closing server", e);
    }
  }

  private void openServerSocket() {
    try {
      this.serverSocket = new ServerSocket(this.serverPort);
    } catch (IOException e) {
      throw new RuntimeException("Cannot open port 8080", e);
    }
  }
}
