package network.server;

import gameLWJGL.Timer;
import gameLWJGL.collision.CollisionDetector;
import gameLWJGL.world.Camera;
import gameLWJGL.world.ObjectHandler;
import gameLWJGL.world.World;

public class GameServer {

    private Camera camera;
    private World world;
    private ObjectHandler objectHandler;
    private CollisionDetector collisionDetector;
    private ServerNetworkManager networkManager;

    public GameServer(){
        camera = new Camera();
        world = new World(4, camera);
        world.buildWorld(0);
        //Input input = new Input();
        objectHandler = new ObjectHandler();
        collisionDetector = new CollisionDetector(world, objectHandler);

        networkManager = new ServerNetworkManager();
    }

    public void runGame(){
        networkManager.start();

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
