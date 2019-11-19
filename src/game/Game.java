package game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

    private static final long serialVersionUID = 1L;

    private boolean isRunning = false;
    private Thread thread;

    public Game (){
        new Window(1000, 563, "Game", this);
    }

    public void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop(){
        isRunning = false;
        try{
            thread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void run(){
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("Frames: " + frames + " --- Updates: " + updates);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    public void tick() {

    }
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        ///////////////////////////////////////////////

        g.setColor(Color.red);
        g.fillRect(0,0, 1000, 563);

        /////////////////////////////////////
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
