package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private static final int PORT = 8088;

  public static void main(String[] args) throws IOException {

    ServerSocket server = new ServerSocket(PORT);
    System.out.println("Server listening on port " + PORT + "...");
    while (true) {
      Socket socket = server.accept();
      //byte[] result = socket.getInputStream().readAllBytes();
      
    }
  }
}
