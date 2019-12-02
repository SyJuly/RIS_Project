package gameLWJGL.input;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Input {

    private List<IMoveable> moveables;

    public Input(){
        this.moveables = moveables != null ? moveables : new ArrayList<>();
    }


    public void handleInput(long window){
        int xDirection = 0, yDirection = 0;

        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE){
            glfwSetWindowShouldClose(window, true);
        }
        if(glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE){
            xDirection = -1;
        }
        if(glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE){
            xDirection = 1;
        }

        if(glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE){
            yDirection = 1;
        }
        if(glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE){
            yDirection = - 1;
        }


        for (int i = 0; i < moveables.size(); i++){
            IMoveable tempMovable = moveables.get(i);
            tempMovable.move(xDirection, yDirection);
        }
    }

    public void addMoveable(IMoveable moveable){
        moveables.add(moveable);
    }

    public void removeMoveable(IMoveable moveable){
        moveables.remove(moveable);
    }
}
