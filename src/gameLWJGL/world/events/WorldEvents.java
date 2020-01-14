package gameLWJGL.world.events;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class WorldEvents implements Runnable{

    public Queue<WorldEvent> worldEvents;
    private boolean isStopped = false;

    public WorldEvents()
    {
        worldEvents = new PriorityBlockingQueue();
    }

    public void addEvent(WorldEvent event){
        worldEvents.add(event);
    }

    @Override
    public void run() {
        while(true) {
            if(worldEvents.isEmpty()) continue;
            long time = worldEvents.peek().executionTime;
            long timeout =  time - System.currentTimeMillis();
            if (timeout <= 0) {
                worldEvents.poll().execute();
            }
        }
    }

    protected synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
    }
}
