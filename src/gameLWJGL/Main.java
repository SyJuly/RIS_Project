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

        glEnable(GL_TEXTURE_2D);
        Texture tex = new Texture("/home/july/Projects/RIS/Projekt/player.jpeg");
        Model modelSquare = Utility.GetSquareWithTexture(0.5f);

        float squareSize = 0.2f;
        float speed = 0.005f;
        float x = 0;
        float y = 0;

        while(!glfwWindowShouldClose(window)){

            glfwPollEvents();

            if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE){
                glfwSetWindowShouldClose(window, true);
            }
            if(glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE){
                x-= speed;
            }
            if(glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE){
                x+= speed;
            }

            if(glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE){
                y+= speed;
            }
            if(glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE){
                y-= speed;
            }


            glClear(GL_COLOR_BUFFER_BIT);


            tex.bind();
            modelSquare.render();

            /*glBegin(GL_QUADS);
                glColor4f(1,0,0,0);
                glVertex2f(-squareSize + x, squareSize + y);
                glVertex2f(squareSize + x, squareSize + y);
                glVertex2f(squareSize + x, -squareSize + y);
                glVertex2f(-squareSize + x, -squareSize + y);
            glEnd();*/

            /*glBegin(GL_QUADS);
                glTexCoord2f(0,0);
                glVertex2f(-squareSize + x, squareSize + y);
                glTexCoord2f(1,0);
                glVertex2f(squareSize + x, squareSize + y);
                glTexCoord2f(1,1);
                glVertex2f(squareSize + x, -squareSize + y);
                glTexCoord2f(0,1);
                glVertex2f(-squareSize + x, -squareSize + y);
            glEnd();*/

            glfwSwapBuffers(window);
        }

        glfwTerminate();
    }

    public static void main(String[] args) {
        new Main();
    }
}