package gameLWJGL.world;

import gameLWJGL.objects.GameObject;

import java.util.LinkedList;
import java.util.List;

public class World {

    private WorldArea[] world;
    private Camera camera;
    private int worldSize = 3;
    private float currentCentralX = 0;
    private float worldAreaWidth = 0;

    public World (float centralX, float worldAreaWidth, Camera camera){
        this.camera = camera;
        this.currentCentralX = centralX;
        this.worldAreaWidth = worldAreaWidth;
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

    public void update(){
        if (camera.x > currentCentralX + worldAreaWidth){
            currentCentralX += worldAreaWidth;
            WorldArea area = new WorldArea(currentCentralX + worldAreaWidth, worldAreaWidth);
            area.buildWorld();
            world[0] = world[1];
            world[1] = world[2];
            world[2] = area;
        } else if (camera.x < currentCentralX){
            currentCentralX -= worldAreaWidth;
            WorldArea area = new WorldArea(currentCentralX - worldAreaWidth, worldAreaWidth);
            area.buildWorld();
            world[2] = world[1];
            world[1] = world[0];
            world[0] = area;
        }
    }

    public void render(){
        for(int i = 0; i < world.length; i++){
            WorldArea area = world[i];
            area.render(camera);
        }
    }
}
