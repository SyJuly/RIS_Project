package gameLWJGL.objects;

import gameLWJGL.world.events.PrintEvent;
import gameLWJGL.world.events.SpawnAIEvent;
import gameLWJGL.world.events.WorldEvents;

import java.util.*;

public class AIManager implements IObjectHolder{

    private static final long[] LEVEL_SPAWNING_INTERVALS = new long[]{
            5000,
            1000,
            100
    };
    private static final long LEVEL_DURANCE = 10000;
    private static final long START_SPAWNING = 1000;

    Map<String, AI> ais = new HashMap<>();
    List<String> createdAIs = new ArrayList<>();
    Queue<Player> targets = new LinkedList<>();
    private int idCounter;

    public AIManager(){}

    public AIManager(WorldEvents eventHandler){
        long first_spawning = System.currentTimeMillis() + START_SPAWNING;
        long nextSpawning = first_spawning;
        for(int i = 0; i < LEVEL_SPAWNING_INTERVALS.length; i++){
            int numberOfEvents = Math.round(LEVEL_DURANCE * 1f/ LEVEL_SPAWNING_INTERVALS[i]);
            eventHandler.addEvent(new PrintEvent("LEVEL " + (i + 1) + " BEGINS.", nextSpawning - 1));
            for(int j = 0; j < numberOfEvents; j++) {
                nextSpawning += LEVEL_SPAWNING_INTERVALS[i];
                SpawnAIEvent event = new SpawnAIEvent(this, nextSpawning, i);
                eventHandler.addEvent(event);
            }
            System.out.println("Spawing per level: " + numberOfEvents);
        }
    }

    public void spawnAI(){
        System.out.println("Spawning AI.");
        Player target = targets.poll();
        AI ai = new AI( 1,2, "ai" + idCounter, target);
        ais.put(ai.id, ai);
        createdAIs.add(ai.id);
        targets.add(target);
        idCounter++;
    }

    public void addPlayer(Player player){
        targets.add(player);
    }

    @Override
    public GameObject[] getNewlyCreatedObjects() {
        GameObject[] newlyCreatedObjects = new GameObject[createdAIs.size()];
        for (int i = 0; i < newlyCreatedObjects.length; i++) {
            newlyCreatedObjects[i] = ais.get(createdAIs.get(i));
        }
        createdAIs.clear();
        return newlyCreatedObjects;
    }

    @Override
    public String[] getRemovedObjects() {
        ArrayList<String> objectIdList = new ArrayList<>();
        Iterator<AI> iter = ais.values().iterator();
        while(iter.hasNext()) {
            AI ai = iter.next();
            if(ai.shouldBeDestroyed()){
                objectIdList.add(ai.id);
                iter.remove();
            }
        }
        String[] gameObjectIds = new String[objectIdList.size()];
        return objectIdList.toArray(gameObjectIds);
    }

    @Override
    public void removeObject(String id) {
        ais.remove(id);
    }

    public void createAI(float x, float y, String id, float[] specifics) {
        AI ai = new AI( x,y, id);
        ai.setSpecifics(specifics);
        ais.put(ai.id, ai);
        createdAIs.add(ai.id);
        idCounter++;
    }
}
