package gameLWJGL.world;

import gameLWJGL.objects.GameObject;
import network.IMsgApplicator;
import network.networkMessages.WorldMsg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class World implements IMsgApplicator<WorldMsg> {

    private WorldArea[] world;
    private Map<Float, WorldArea> worldCache;
    private Camera camera;
    private int worldSize = 3;
    private int worldCacheSize = 6;
    private float currentCentralX;
    private float worldAreaWidth;

    private boolean hasBeenUpdated = false;

    public World (float worldAreaWidth, Camera camera){
        this.camera = camera;
        this.worldAreaWidth = worldAreaWidth;
        worldCache = new HashMap<>();
    }

    public void buildWorld(float centralX){
        this.currentCentralX = centralX;
        float startingX = centralX - worldAreaWidth;

        world = new WorldArea[worldSize];
        for (int i = 0; i < worldSize; i++){
            WorldArea area = new WorldArea(startingX, worldAreaWidth);
            world[i] = area;
            area.buildWorld();
            startingX += worldAreaWidth;
        }
        hasBeenUpdated = true;
    }

    public List<GameObject> getStaticObjects(){
        LinkedList<GameObject> allStaticObjects = new LinkedList<>();
        for(int i = 0; i < world.length; i++){
            WorldArea area = world[i];
            allStaticObjects.addAll(area.getStaticObjects());
        }
        return allStaticObjects;
    }

    public void update(){
        if (camera.x > currentCentralX + worldAreaWidth){
            expandWorld(1);
        } else if (camera.x < currentCentralX){
            expandWorld(-1);
        }
    }

    private void expandWorld(int direction){
        currentCentralX = currentCentralX + direction * worldAreaWidth;
        float neededAreaStartingX = currentCentralX + direction * worldAreaWidth;
        WorldArea area = getNeededArea(direction, neededAreaStartingX);

        int startIndex = direction > 0 ? 0 : worldSize - 1;
        int endIndex = direction > 0 ? worldSize - 1 : 0;
        WorldArea areaToCache = world[startIndex];
        worldCache.put(areaToCache.startingX, areaToCache);
        areaToCache.lastUsed = System.currentTimeMillis();

        cleanupCache();

        for (int i = startIndex; i != endIndex; i += direction){
            world[i] = world[i + direction];
        }
        world[endIndex] = area;
        hasBeenUpdated = true;
    }

    public void render(){
        if(world == null) return;
        for(int i = 0; i < world.length; i++){
            WorldArea area = world[i];
            area.render(camera);
        }
    }

    private void cleanupCache(){
        //System.out.println("Current cache size: " + worldCache.size());
        if(worldCache.size() > worldCacheSize){
            WorldArea oldestArea = null;
            for (WorldArea cachedArea: worldCache.values()) {
                if(oldestArea == null || cachedArea.lastUsed < oldestArea.lastUsed){
                    oldestArea = cachedArea;
                }
            }
            worldCache.remove(oldestArea.startingX);
        }
    }

    private WorldArea getNeededArea(int direction, float neededAreaStartingX){
        WorldArea area;
        if(worldCache.containsKey(neededAreaStartingX)){
            area = worldCache.get(neededAreaStartingX);
            worldCache.remove(neededAreaStartingX);
            System.out.println("Loaded world area from cache in direction: " + direction);
        } else {
            area = new WorldArea(neededAreaStartingX, worldAreaWidth);
            area.buildWorld();
            System.out.println("Created world area in direction: " + direction);
        }
        return area;
    }

    @Override
    public boolean shouldSendMessage() {
        return hasBeenUpdated;
    }

    @Override
    public WorldMsg getMessage() {
        //hasBeenUpdated = false;
        return new WorldMsg(currentCentralX);
    }

    @Override
    public void receive(WorldMsg worldMsg) {
        if(world == null) {
            buildWorld(worldMsg.centralX);
        } else {
            currentCentralX = worldMsg.centralX;
            update();
        }
    }
}
