package network.client;

import gameLWJGL.Timer;
import gameLWJGL.Window;
import gameLWJGL.input.Input;
import gameLWJGL.world.Camera;
import gameLWJGL.world.ObjectHandler;
import gameLWJGL.world.World;
import network.IMsgRecipient;
import network.MsgType;
import network.networkMessageHandler.NetworkMsgHandler;
import network.networkMessageHandler.WorldMsgHandler;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class GameClient {

    private Camera camera;
    private World world;
    private ObjectHandler objectHandler;
    private Input input;
    private ClientNetworkManager networkManager;

    public GameClient(){
        camera = new Camera();
        world = new World(4, camera);
        objectHandler = new ObjectHandler();
        input = new Input();
        networkManager = new ClientNetworkManager(createMsgRecipients());
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
        double unprocessed = 0; // time that hasn't been processed

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


                if(frame_time >= 1.0){
                    frame_time = 0;
                    System.out.println("FPS: " + frames);
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

        networkManager.stop();
        glfwTerminate();
    }

    private Map<Integer, NetworkMsgHandler> createMsgRecipients(){
        Map<Integer, NetworkMsgHandler> msgHandlers = new HashMap<>();
        msgHandlers.put(MsgType.World.getCode(), new WorldMsgHandler(world));
        return msgHandlers;
    }

    public static void main(String[] args) {
        GameClient gameClient = new GameClient();
        gameClient.runGame();
    }
}
