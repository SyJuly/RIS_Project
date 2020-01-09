package network.server;

import gameLWJGL.Timer;
import gameLWJGL.collision.CollisionDetector;
import gameLWJGL.input.Input;
import gameLWJGL.world.Camera;
import gameLWJGL.objects.ObjectHandler;
import gameLWJGL.world.World;
import network.IMsgApplicator;
import network.MsgType;
import network.NetworkManager;
import network.networkMessageHandler.InputMsgHandler;
import network.networkMessageHandler.JoinMsgHandler;
import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessages.NetworkMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServer {

    private Camera camera;
    private Input input;
    private World world;
    private ObjectHandler objectHandler;
    private CollisionDetector collisionDetector;
    private NetworkManager networkManager;

    private List<IMsgApplicator> msgSenders;

    public GameServer(){
        camera = new Camera();
        world = new World(4, camera);

        world.buildWorld(0);
        input = new Input();
        objectHandler = new ObjectHandler(input);
        collisionDetector = new CollisionDetector(world, objectHandler);
        networkManager = new NetworkManager(getServerNetworkCommunicator());
        msgSenders = new ArrayList<>();
        msgSenders.add(world);
        msgSenders.add(objectHandler.getDynamicObjectsApplicator());
    }

    public void runGame(){
        networkManager.start();

        world.buildWorld(0);

        double frame_cap = 1.0/60.0; // 60 frames per second
        double frame_time = 0;

        int frames = 0;
        double lastTime = Timer.getTime();
        double unprocessed = 0; // time that hasn't been processed

        while(true){
            double currentTime = Timer.getTime();
            double delta = currentTime - lastTime;
            unprocessed += delta;
            frame_time += delta;

            lastTime = currentTime;

            while(unprocessed >= frame_cap){
                // UPDATE
                unprocessed -= frame_cap;

                //input.handleInput(window.window);
                world.update();
                objectHandler.update();
                //camera.update();
                collisionDetector.detectCollisions();

                for (IMsgApplicator msgApplicator: msgSenders) {
                    if(msgApplicator.shouldSendMessage() || objectHandler.hasNewPlayer()){
                        NetworkMsg msg = msgApplicator.getMessage();
                        networkManager.sendMsg(msg);
                        System.out.println("send msg: " + msg.msgType);
                    }
                }
                if(objectHandler.hasNewPlayer()){
                    objectHandler.setHasNewPlayer(false);
                }
                // for each IMsgSender check if shouldSend
                // if true -> send msg to SNM

                if(frame_time >= 1.0){
                    frame_time = 0;
                    //System.out.println("FPS: " + frames);
                    //System.out.println("X: " + player.x + "| Y: " + player.y);
                    frames = 0;
                }
            }
        }
    }

    private ServerNetworkCommunicator getServerNetworkCommunicator(){
        Map<Integer, NetworkMsgHandler> msgHandlers = new HashMap<>();
        msgHandlers.put(MsgType.Join.getCode(), new JoinMsgHandler(objectHandler.getJoinApplicator()));
        msgHandlers.put(MsgType.Input.getCode(), new InputMsgHandler(input));
        return new ServerNetworkCommunicator(msgHandlers);
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.runGame();
    }
}
