package gameLWJGL;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public class Window {
    public long window;
    private int width, height;

    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        createWindow(title);
    }

    private void createWindow(String title){
        window = glfwCreateWindow(width, height, title, 0, 0);

        if(window == 0){
            throw new IllegalStateException("Failed to create Window");
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        glfwShowWindow(window);

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - 640) / 2, (videoMode.height() - 480) / 2);

        glfwShowWindow(window);

        glfwMakeContextCurrent(window);

    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }

    public void swapBuffers(){
        glfwSwapBuffers(window);
    }
}
