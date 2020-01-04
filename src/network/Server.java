package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

  protected int          serverPort   = 8080;
  protected ServerSocket serverSocket = null;
  protected boolean      isStopped    = false;
  protected Thread       runningThread= null;

  public Server(int port){
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
                new WorkerRunnable(
                        clientSocket, "Multithreaded Server")
        ).start();
      } catch (IOException e) {
        if(isStopped()) {
          System.out.println("Server Stopped.") ;
          return;
        }
        throw new RuntimeException(
                "Error accepting client connection", e);
      }
    }
    System.out.println("Server Stopped.") ;
  }


  private synchronized boolean isStopped() {
    return this.isStopped;
  }

  public synchronized void stop(){
    this.isStopped = true;
    try {
      System.out.println("Stopping Server");
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

  public static void main(String[] args) {
    Server server = new Server(8080);
    Thread thread = new Thread(server);
    thread.start();

    try {
      while(thread.isAlive()){
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Stopping server application.");
      server.stop();
    }
  }
}
