package gameLWJGL;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public Main(){
        if(!glfwInit()){
            throw new IllegalStateException("Failed to initialise GLFW.");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long window = glfwCreateWindow(640, 480, "Game", 0, 0);

        if(window == 0){
            throw new IllegalStateException("Failed to create Window");
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - 640) / 2, (videoMode.height() - 480) / 2);

        glfwShowWindow(window);

        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        float x = 0;

        while(!glfwWindowShouldClose(window)){

            glfwPollEvents();

            if(glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE){
                x+= 0.001f;
            }

            glClear(GL_COLOR_BUFFER_BIT);

            glBegin(GL_QUADS);
            glColor4f(1,0,0,0);
            glVertex2f(-0.5f + x, 0.5f);
            glVertex2f(0.5f + x, 0.5f);
            glVertex2f(0.5f + x, -0.5f);
            glVertex2f(-0.5f + x, -0.5f);
            glEnd();

            glfwSwapBuffers(window);
        }

        glfwTerminate();
    }

    public static void main(String[] args) {
        new Main();
    }
}