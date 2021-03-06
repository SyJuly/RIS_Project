package gameLWJGL.network.client;

import gameLWJGL.Timer;
import gameLWJGL.Window;
import gameLWJGL.input.Input;
import gameLWJGL.objects.AIManager;
import gameLWJGL.objects.ObjectHandler;
import gameLWJGL.objects.PlayerManager;
import gameLWJGL.world.Camera;
import gameLWJGL.world.World;
import gameLWJGL.network.common.IMsgApplicator;
import gameLWJGL.network.common.MsgType;
import gameLWJGL.network.common.networkMessageHandler.DynamicObjectsMsgHandler;
import gameLWJGL.network.common.networkMessageHandler.NetworkMsgHandler;
import gameLWJGL.network.common.networkMessageHandler.WorldMsgHandler;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class GameClient {

    public static String CLIENTID;
    public static float[] COLOR;
    private Camera camera;
    private World world;
    private ObjectHandler objectHandler;
    private PlayerManager playerManager;
    private AIManager aiManager;
    private Input input;
    private ClientNetworkManager networkManager;

    private List<IMsgApplicator> msgSenders;

    public GameClient(String id, float[] color){
        CLIENTID = id;
        COLOR = color;
        aiManager = new AIManager();
        playerManager = new PlayerManager(aiManager);
        camera = new Camera(playerManager);
        world = new World(4, camera);
        objectHandler = new ObjectHandler(playerManager, aiManager, world);
        input = new Input();
        msgSenders = new ArrayList<>();
        msgSenders.add(input);
        msgSenders.add(playerManager);
        networkManager = new ClientNetworkManager(getMsgHandlers(), msgSenders);
    }

    private void runGame(){
        networkManager.start();

        if(!glfwInit()){
            throw new IllegalStateException("Failed to initialise GLFW.");
        }

        Window window = new Window(1024, 740, "Game");

        GL.createCapabilities();

        double frame_cap = 1.0/60.0; // 60 frames per second
        double frame_time = 0;
        int frames = 0;
        double lastTime = Timer.getTime();
        double unprocessed = 0; // executionTime that hasn't been processed

        System.out.println("clients main thread: " + Thread.currentThread());
        while(!window.shouldClose()){
            boolean can_render = false;
            double currentTime = Timer.getTime();
            double delta = currentTime - lastTime;
            unprocessed += delta;
            frame_time += delta;

            lastTime = currentTime;

            while(unprocessed >= frame_cap){
                // UPDATE
                unprocessed -= frame_cap;
                can_render = true;

                glfwPollEvents();

                input.handleInput(window.window);
                world.update();
                objectHandler.updateObjectsList();
                playerManager.updateOnClient(); // for interpolation/prediction
                camera.update();
                networkManager.sendMessages();


                if(frame_time >= 1.0){
                    frame_time = 0;
                    //System.out.println("FPS: " + frames);
                    //System.out.println("X: " + player.x + "| Y: " + player.y);
                    frames = 0;
                }
            }

            //RENDER
            if(can_render){
                glClear(GL_COLOR_BUFFER_BIT);

                world.render();
                objectHandler.render(camera);

                window.swapBuffers();
                frames++;
            }
        }

        //networkManager.stop();
        glfwTerminate();
    }

    private Map<Integer, NetworkMsgHandler> getMsgHandlers(){
        Map<Integer, NetworkMsgHandler> msgHandlers = new HashMap<>();
        msgHandlers.put(MsgType.World.getCode(), new WorldMsgHandler(world));
        msgHandlers.put(MsgType.DynamicObjects.getCode(), new DynamicObjectsMsgHandler(objectHandler));
        return msgHandlers;
    }

    public static void main(String[] args) {
        float[] color = new float[]{Float.parseFloat(args[1]),Float.parseFloat(args[2]),Float.parseFloat(args[3])};
        GameClient gameClient = new GameClient(args[0], color);
        gameClient.runGame();
    }
}
