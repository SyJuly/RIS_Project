package gameLWJGL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Input {

    public void handleInput(long window, Player player){
        player.speed = 0.005f;
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE){
            glfwSetWindowShouldClose(window, true);
        }
        if(glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE){
            player.x-= player.speed;
        }
        if(glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE){
            player.x+= player.speed;
        }

        if(glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE){
            player.y+= player.speed;
        }
        if(glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE){
            player.y-= player.speed;
        }
    }
}
