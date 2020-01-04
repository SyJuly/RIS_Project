package gameLWJGL.world;

import gameLWJGL.objects.GameObject;

import java.util.LinkedList;
import java.util.List;

public class World {

    private WorldArea[] world;
    private WorldArea[] worldCache;
    private Camera camera;
    private int worldSize = 3;
    private int worldCacheSize = 2;
    private float currentCentralX;
    private float worldAreaWidth;

    public World (float centralX, float worldAreaWidth, Camera camera){
        this.camera = camera;
        this.currentCentralX = centralX;
        this.worldAreaWidth = worldAreaWidth;
        world = new WorldArea[worldSize];
        worldCache = new WorldArea[worldCacheSize];
        float startingX = centralX - worldAreaWidth;
        for (int i = 0; i < worldSize; i++){
            WorldArea area = new WorldArea(startingX, worldAreaWidth);
            world[i] = area;
            area.buildWorld();
            startingX += worldAreaWidth;
        }
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
        int cachedAreaIndex = direction > 0 ? getRightCacheIndex() : getLeftCacheIndex();
        int toCacheIndex = direction > 0 ? getRightCacheIndex() : getLeftCacheIndex();
        float neededAreaStartingX = currentCentralX + direction * worldAreaWidth;
        WorldArea area;
        if(worldCache[cachedAreaIndex] != null && worldCache[cachedAreaIndex].startingX == neededAreaStartingX){
            area = worldCache[cachedAreaIndex];
            System.out.println("Loaded world area from cache in direction: " + direction);
        } else {
            area = new WorldArea(neededAreaStartingX, worldAreaWidth);
            area.buildWorld();
            System.out.println("Created world area in direction: " + direction);
        }
        worldCache[toCacheIndex] = world[1 + direction];
        int startIndex = direction > 0 ? 0 : worldSize - 1;
        int endIndex = direction > 0 ? worldSize - 1 : 0;
        for (int i = startIndex; i != endIndex; i += direction){
            world[i] = world[i + direction];
        }
        world[endIndex] = area;
    }

    private int getLeftCacheIndex(){
        return 0;
    }

    private int getRightCacheIndex(){
        return 1;
    }

    public void render(){
        for(int i = 0; i < world.length; i++){
            WorldArea area = world[i];
            area.render(camera);
        }
    }
}
