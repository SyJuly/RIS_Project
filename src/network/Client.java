package network;

import java.net.Socket;
import java.io.IOException;

public class Client {
  private static final int PORT = 8088;

  public static void main(String[] args) throws IOException {
	  Socket socket = new Socket( "localhost", PORT);
	  socket.getOutputStream().write(4);
	  socket.close();
  }

  public void sendMoveMsg() {

  }
  
}