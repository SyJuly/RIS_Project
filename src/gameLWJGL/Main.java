package gameLWJGL;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main(){
    /*    if(!glfwInit()){
            throw new IllegalStateException("Failed to initialise GLFW.");
        }

        Window window = new Window(1024, 740, "Game");

        GL.createCapabilities();

        double frame_cap = 1.0/60.0; // 60 frames per second
        double frame_time = 0;
        int frames = 0;
        double lastTime = Timer.getTime();
        double unprocessed = 0; // time that hasn't been processed


        Player player = new Player(0,0, 0.06f);
        Camera camera = new Camera(player);
        World world = new World(0, 4, camera);
        Input input = new Input();
        ObjectHandler objectHandler = new ObjectHandler();
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

            //RENDER
            if(can_render){
                glClear(GL_COLOR_BUFFER_BIT);

                world.render();
                objectHandler.render(camera);

                window.swapBuffers();
                frames++;
            }
        }

        glfwTerminate();*/
    }
}