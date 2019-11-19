package graphics_depri;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GraphicsSystem extends Canvas implements Runnable{

    private Thread graphicsThread;
    private boolean running = false;

    public synchronized void start(){
        graphicsThread = new Thread(this);
        graphicsThread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            graphicsThread.join();
            running = false;
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Window(new GraphicsSystem());
    }
}
