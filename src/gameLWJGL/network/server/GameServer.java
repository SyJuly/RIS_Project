package gameLWJGL.network.server;

import gameLWJGL.Timer;
import gameLWJGL.collision.CollisionDetector;
import gameLWJGL.input.Input;
import gameLWJGL.objects.AIManager;
import gameLWJGL.objects.ObjectHandler;
import gameLWJGL.objects.PlayerManager;
import gameLWJGL.world.Camera;
import gameLWJGL.world.World;
import gameLWJGL.world.events.WorldEvents;
import gameLWJGL.network.common.IMsgApplicator;
import gameLWJGL.network.common.MsgType;
import gameLWJGL.network.common.networkMessageHandler.InputMsgHandler;
import gameLWJGL.network.common.networkMessageHandler.JoinMsgHandler;
import gameLWJGL.network.common.networkMessageHandler.NetworkMsgHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServer {

    private Camera camera;
    private Input input;
    private World world;
    private ObjectHandler objectHandler;
    private PlayerManager playerManager;
    private AIManager aiManager;
    private CollisionDetector collisionDetector;
    private ServerNetworkManager networkManager;
    private WorldEvents eventHandler;

    private Thread worldEventThread;

    private List<IMsgApplicator> msgSenders;

    public GameServer(){
        eventHandler = new WorldEvents();
        camera = new Camera();
        world = new World(4, camera);
        input = new Input();
        aiManager = new AIManager();
        playerManager = new PlayerManager(input, camera, aiManager, eventHandler);
        objectHandler = new ObjectHandler(playerManager, aiManager, world);
        collisionDetector = new CollisionDetector(world, objectHandler);
        msgSenders = new ArrayList<>();
        msgSenders.add(world);
        msgSenders.add(objectHandler);
        networkManager = new ServerNetworkManager(getMsgHandlers(), msgSenders);
    }

    public void runGame(){
        world.buildWorld(0);
        worldEventThread = new Thread(eventHandler);
        worldEventThread.start();
        networkManager.start();

        double frame_cap = 1.0/60.0; // 60 frames per second
        double frame_time = 0;

        int frames = 0;
        double lastTime = Timer.getTime();
        double unprocessed = 0; // executionTime that hasn't been processed

        while(true){

            if(networkManager.firstPlayerHasConnected()) {
                aiManager.setUpSpawningAIs(eventHandler);
            }

            double currentTime = Timer.getTime();
            double delta = currentTime - lastTime;
            unprocessed += delta;
            frame_time += delta;

            lastTime = currentTime;

            while(unprocessed >= frame_cap){
                // UPDATE
                unprocessed -= frame_cap;


                objectHandler.updateObjectsList();
                objectHandler.updateObjects();
                camera.update();
                world.update();
                collisionDetector.detectCollisions();
                networkManager.sendMessages();

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
        //networkManager.stop();
    }

    private  Map<Integer, NetworkMsgHandler> getMsgHandlers(){
        Map<Integer, NetworkMsgHandler> msgHandlers = new HashMap<>();
        msgHandlers.put(MsgType.Join.getCode(), new JoinMsgHandler(playerManager));
        msgHandlers.put(MsgType.Input.getCode(), new InputMsgHandler(input));
        return msgHandlers;
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.runGame();
    }
}
