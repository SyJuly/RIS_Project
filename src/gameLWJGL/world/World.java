package gameLWJGL.world;

import gameLWJGL.objects.GameObject;

import java.util.LinkedList;
import java.util.List;

public class World {

    private WorldArea[] world;
    private int worldSize = 3;

    public World (float centralX, float worldAreaWidth){
        world = new WorldArea[worldSize];
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

    public void render(Camera camera){
        for(int i = 0; i < world.length; i++){
            WorldArea area = world[i];
            area.render(camera);
        }
    }
}
