package network.client;

import gameLWJGL.world.World;
import network.MsgType;
import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessageHandler.WorldMsgHandler;

import java.util.HashMap;
import java.util.Map;

public class ClientNetworkManager {

    private final int PORT = 8080;
    private Map<Integer, NetworkMsgHandler> msgHandlers;
    private ClientNetworkCommunicator communicator;

    public ClientNetworkManager(World world){
        msgHandlers = new HashMap();
        msgHandlers.put(MsgType.World.getCode(), new WorldMsgHandler(world));
    }

    public void start(){
        communicator = new ClientNetworkCommunicator(PORT, msgHandlers);
        Thread thread = new Thread(communicator);
        thread.start();
    }

    public void stop(){
        System.out.println("Stopping ClientNetworkManager.");
        communicator.stop();
    }

    public void register(NetworkMsgHandler msgHandler, MsgType type){
        msgHandlers.put(type.getCode(), msgHandler);
    }

    public void receiveMessage(byte[] message, String networkCode){
        //msgHandlers.get(networkCode).handleMsg();
    }
}
