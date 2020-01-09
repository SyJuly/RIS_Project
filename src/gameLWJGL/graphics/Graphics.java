package gameLWJGL.graphics;

/*public class Graphics implements Runnable {

    private ObjectHandler objectHandler;
    private World world;
    private Window window;

    private Thread thread;

    private boolean shouldRender = false;
    private boolean isRunning = false;

    public Graphics(ObjectHandler objectHandler, World world) {
        this.world = world;
        this.objectHandler = objectHandler;

        if(!glfwInit()){
            throw new IllegalStateException("Failed to initialise GLFW.");
        }
        window = new Window(700, 700, "Game");
        GL.createCapabilities();
    }

    public void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (isRunning){
            if(!shouldRender){
            world.render();
            objectHandler.render();
            window.swapBuffers();
            shouldRender = false;
            }
        }
        glfwTerminate();
    }

    public void stop(){
        isRunning = false;
        try{
            thread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }


    public void renderGame(){
       shouldRender = true;
    }

    public Window getWindow(){
        return window;
    }
}*/
