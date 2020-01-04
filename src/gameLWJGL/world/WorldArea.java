package gameLWJGL.world;

import game.Game;
import gameLWJGL.objects.GameObject;

import java.util.LinkedList;
import java.util.List;

public class WorldArea {

    private float startingX, width;
    private LinkedList<GameObject> staticObjects;

    public WorldArea (float startingX, float width) {
        this.startingX = startingX;
        this.width = width;
        staticObjects = new LinkedList<>();
    }

    public void buildWorld(){
        float worldX = 0;
        for (float x = 0; x < width; x+=0.7f){
            worldX = x + startingX;
            GroundBlock block = new GroundBlock(worldX, getY(worldX), 0.4f, 0.02f);
            staticObjects.add(block);
        }
    }

    public void render(Camera camera){
        for(int i = 0; i < staticObjects.size(); i++){
            GameObject tempObject = staticObjects.get(i);
            tempObject.render(camera);
        }
    }

    private float getY(float x){
        return (float)(0.4f * Math.cos(0.5f * x) - 0.4f * Math.cos(1f * x) - 0.4f * Math.cos(2f * x) + 0.3f);
    }


    public List<GameObject> getStaticObjects(){
        return staticObjects;
    }
}
