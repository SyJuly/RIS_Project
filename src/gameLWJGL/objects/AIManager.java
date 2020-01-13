package gameLWJGL.objects;

import java.util.*;

public class AIManager implements IObjectHolder{

    private static final long SPAWNING_INTERVAL = 10000;
    Map<String, AI> ais = new HashMap<>();
    List<String> createdAIs = new ArrayList<>();
    Queue<Player> targets = new LinkedList<>();
    private long lastSpawned;
    private int idCounter;

    public void update(){
        if(targets.size() > 0 && System.currentTimeMillis() - lastSpawned > SPAWNING_INTERVAL){
            spawnAI();
        }
    }

    private void spawnAI(){
        System.out.println("Spawning AI.");
        Player target = targets.poll();
        AI ai = new AI( 1,2, "ai" + idCounter, target);
        ais.put(ai.id, ai);
        createdAIs.add(ai.id);
        targets.add(target);
        idCounter++;
        lastSpawned = System.currentTimeMillis();
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
        lastSpawned = System.currentTimeMillis();
    }
}
