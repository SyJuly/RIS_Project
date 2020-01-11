package gameLWJGL.world;

import gameLWJGL.objects.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WorldArea implements IObjectHolder {

    public float startingX;
    public float lastUsed;
    private float width;
    private LinkedList<GameObject> staticObjects;
    private LinkedList<IDynamicObject> dynamicObjects;

    private boolean registeredDynamicObjects = true;

    public WorldArea (float startingX, float width) {
        this.startingX = startingX;
        this.width = width;
        staticObjects = new LinkedList<>();
        dynamicObjects = new LinkedList<>();
    }

    public void buildWorld(){
        float worldX = 0;
        for (float x = 0; x < width; x+=0.7f){
            worldX = x + startingX;
            GroundBlock block = new GroundBlock(worldX, getY(worldX), 0.4f, 0.05f);
            WeightPill pill = new WeightPill(worldX, getY(worldX) + 0.2f, "pill" + worldX + getY(worldX));
            staticObjects.add(block);
            dynamicObjects.add(pill);
        }
        registeredDynamicObjects = false;
    }

    public void render(Camera camera){
        for(int i = 0; i < staticObjects.size(); i++){
            GameObject tempObject = staticObjects.get(i);
            tempObject.render(camera);
        }
        for(int i = 0; i < dynamicObjects.size(); i++){
            GameObject tempObject = dynamicObjects.get(i).getGameObject();
            tempObject.render(camera);
        }
    }

    private float getY(float x){
        return (float)(0.4f * Math.cos(0.5f * x) - 0.4f * Math.cos(1f * x) - 0.4f * Math.cos(2f * x) + 0.3f);
    }

    public List<GameObject> getStaticObjects(){
        return staticObjects;
    }

    @Override
    public GameObject[] getNewlyCreatedObjects() {
        if(!registeredDynamicObjects){
            GameObject[] gameObjects = new GameObject[dynamicObjects.size()];
            registeredDynamicObjects = true;
            gameObjects = dynamicObjects.toArray(gameObjects);
            return dynamicObjects.toArray(gameObjects);
        }
        return new GameObject[0];
    }

    @Override
    public String[] getRemovedObjects() {
        ArrayList<String> idsOfRemovedObjects = new ArrayList<>();
        Iterator<IDynamicObject> iter = dynamicObjects.iterator();
        while(iter.hasNext()) {
            IDynamicObject dynamicObject = iter.next();
            if (dynamicObject.shouldBeDestroyed()) {
                idsOfRemovedObjects.add(dynamicObject.getGameObject().id);
                iter.remove();
            }
        }
        String[] idsOfRemovedObjectsArray = new String[idsOfRemovedObjects.size()];
        return idsOfRemovedObjects.toArray(idsOfRemovedObjectsArray);
    }

    @Override
    public void removeObject(String id) {
        for(int i= 0; i < dynamicObjects.size(); i++){
            IDynamicObject dynamicObject = dynamicObjects.get(i);
            if (dynamicObject.getGameObject().id.equals(id)) {
                dynamicObjects.remove(dynamicObject);
            }
        }
    }
}
