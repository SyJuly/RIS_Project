package gameLWJGL.objects;

import gameLWJGL.world.WorldUpdates;
import gameLWJGL.world.events.PrintEvent;
import gameLWJGL.world.events.SpawnAIEvent;
import gameLWJGL.world.events.WorldEvents;

import java.util.*;

public class AIManager implements IObjectHolder{

    private static final long[] LEVEL_SPAWNING_INTERVALS = new long[]{
            5000,
            1000,
            1000
    };
    private static final long LEVEL_DURANCE = 10000;
    private static final long START_SPAWNING = 0;

    Map<String, AI> ais = new HashMap<>();
    List<String> createdAIs = new ArrayList<>();
    Queue<Player> targets = new LinkedList<>();
    private int idCounter;

    public AIManager(){}

    public void setUpSpawningAIs(WorldEvents eventHandler) {
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
        WorldUpdates.getInstance().addGameObjectToUpdate(ai);
    }

    public void addPlayer(Player player){
        targets.add(player);
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
