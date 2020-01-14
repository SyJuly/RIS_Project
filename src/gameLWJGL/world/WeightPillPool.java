package gameLWJGL.world;

import gameLWJGL.objects.WeightPill;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WeightPillPool {

    Queue<WeightPill> weightPills;
    int idCounter = 0;

    WeightPillPool(int numberOfObjectsInPool){
        weightPills = new ConcurrentLinkedQueue();
        for (int i = 0; i <= numberOfObjectsInPool; i++) {
            weightPills.add(new WeightPill(0,0, "pill" + i));
        }
        idCounter = numberOfObjectsInPool;
    }

    public WeightPill getPill(float x, float y){
        WeightPill nextPill = weightPills.peek();
        if(nextPill == null || nextPill.isActive()){
            idCounter++;
            nextPill = new WeightPill(x,y,"pill" + idCounter);
        } else {
            nextPill = weightPills.poll();
            nextPill.x = x;
            nextPill.y = y;
        }
        nextPill.setActive(true);
        return nextPill;
    }

    public void resetPill(WeightPill pill){
        pill.setActive(false);
        weightPills.add(pill);
    }
}
