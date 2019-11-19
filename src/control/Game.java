package control;


import graphics.GraphicsSystem;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game {

    private boolean running = false;

    private GraphicsSystem graphics;

    public void start(){
        running = true;
        graphics = new GraphicsSystem();
        run();
    }

    public synchronized void stop(){
        running = false;
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                delta--;
            }
            if(running){
                render();
            }
            /*frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }*/
        }
        stop();
    }

    private void update(){}

    private void render(){
        graphics.render();
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
