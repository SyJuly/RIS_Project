package gameLWJGL;

import gameLWJGL.collision.CollisionDetector;
import gameLWJGL.input.Input;
import gameLWJGL.world.ObjectHandler;
import gameLWJGL.world.Player;
import gameLWJGL.world.World;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        if(!glfwInit()){
            throw new IllegalStateException("Failed to initialise GLFW.");
        }

        Window window = new Window(700, 700, "Game");

        GL.createCapabilities();

        double frame_cap = 1.0/60.0; // 60 frames per second
        double frame_time = 0;
        int frames = 0;
        double lastTime = Timer.getTime();
        double unprocessed = 0; // time that hasn't been processed


        World world = new World();
        world.buildWorld();
        Input input = new Input();
        ObjectHandler objectHandler = new ObjectHandler();

        Player player = new Player(0,0, 0.06f);
        objectHandler.addObject(player);
        input.addMoveable(player);

        CollisionDetector collisionDetector = new CollisionDetector(world, objectHandler);


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
                objectHandler.update();
                collisionDetector.detectCollisions();


                if(frame_time >= 1.0){
                    frame_time = 0;
                    //System.out.println("FPS: " + frames);
                    System.out.println("X: " + player.x + "| Y: " + player.y);
                    frames = 0;
                }
            }

            //RENDER
            if(can_render){
                glClear(GL_COLOR_BUFFER_BIT);

                world.render();
                objectHandler.render();

                window.swapBuffers();
                frames++;
            }
        }

        glfwTerminate();
    }

    //https://www.youtube.com/watch?v=S7W-zjppzlI&list=PLILiqflMilIxta2xKk2EftiRHD4nQGW0u&index=17
}