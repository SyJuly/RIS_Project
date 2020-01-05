package network.server;

import gameLWJGL.Timer;
import gameLWJGL.collision.CollisionDetector;
import gameLWJGL.world.Camera;
import gameLWJGL.world.ObjectHandler;
import gameLWJGL.world.World;
import network.IMsgApplicator;
import network.NetworkManager;
import network.networkMessages.NetworkMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameServer {

    private Camera camera;
    private World world;
    private ObjectHandler objectHandler;
    private CollisionDetector collisionDetector;
    private NetworkManager networkManager;

    private List<IMsgApplicator> msgApplicators;

    public GameServer(){
        camera = new Camera();
        world = new World(4, camera);

        world.buildWorld(0);
        //Input input = new Input();
        objectHandler = new ObjectHandler();
        collisionDetector = new CollisionDetector(world, objectHandler);
        networkManager = new NetworkManager(new ServerNetworkCommunicator(new HashMap<>()));
        msgApplicators = new ArrayList<>();
        msgApplicators.add(world);
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
                camera.update();
                collisionDetector.detectCollisions();

                for (IMsgApplicator msgApplicator: msgApplicators) {
                    if(msgApplicator.shouldSendMessage()){
                        NetworkMsg msg = msgApplicator.getMessage();
                        networkManager.sendMsg(msg);
                    }
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

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.runGame();
    }
}
