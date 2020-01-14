package gameLWJGL.world;

import gameLWJGL.objects.GameObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorldUpdates {

    Queue<GameObject> objectUpdate;
    Queue<String> objectRemove;

    private static WorldUpdates single_instance = null;

    private WorldUpdates()
    {
        objectUpdate = new ConcurrentLinkedQueue();
        objectRemove = new ConcurrentLinkedQueue();
    }

    // static method to create instance of Singleton class
    public static WorldUpdates getInstance()
    {
        if (single_instance == null)
            single_instance = new WorldUpdates();

        return single_instance;
    }


    public void addGameObjectToUpdate(GameObject gameObject){
        objectUpdate.add(gameObject);
    }

    public void addGameObjectToRemove(String gameObjectId){
        objectRemove.add(gameObjectId);
    }


    public Queue<String> getObjectRemove() {
        return objectRemove;
    }

    public Queue<GameObject> getObjectUpdate() {
        return objectUpdate;
    }
}
