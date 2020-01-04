package network;

import java.net.Socket;
import java.io.IOException;

public class Client implements Runnable{
  private static final int PORT = 8080;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    protected Socket clientSocket = null;

    public void run(){
        System.out.println("Starting client.");
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        while(! isStopped()){
            try {
                clientSocket = new Socket( "localhost", PORT);
                clientSocket.getOutputStream().write(4);
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Client Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                        "Error connecting client", e);
            }
        }
        System.out.println("Client Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

  public static void main(String[] args) throws IOException {
      Client client = new Client();
      Thread thread = new Thread(client);
      thread.start();
      try {
          while(thread.isAlive()){
          }
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          System.out.println("Stopping client application.");
          client.stop();
      }
  }
  
}