package gameLWJGL.objects;

import gameLWJGL.world.WorldUpdates;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WeightPillPool {

    Queue<WeightPill> weightPills;
    int idCounter = 0;

    WeightPillPool(int numberOfObjectsInPool){
        weightPills = new ConcurrentLinkedQueue();
        for (int i = 0; i <= numberOfObjectsInPool; i++) {
            weightPills.add(new WeightPill(0,0, "pill" + i, this));
        }
    }

    public WeightPill getPill(float x, float y){
        WeightPill nextPill = weightPills.peek();
        if(nextPill == null || nextPill.isActive()){
            nextPill = new WeightPill(x,y,"pill" + idCounter, this);
        } else {
            nextPill = weightPills.poll();
            nextPill.x = x;
            nextPill.y = y;
            nextPill.id = "pill" + idCounter;
        }
        idCounter++;
        nextPill.setActive(true);
        WorldUpdates.getInstance().addGameObjectToUpdate(nextPill);
        return nextPill;
    }

    public void resetPill(WeightPill pill){
        pill.setActive(false);
        weightPills.add(pill);
    }
}
