package gameLWJGL.world;

import gameLWJGL.objects.*;

import java.util.*;

public class WorldArea implements IObjectHolder {

    private final float GROUNDBLOCK_WIDTH = 0.3f;
    private final float GROUNDBLOCK_HEIGHT = 0.05f;

    public float startingX;
    public float lastUsed;
    private float width;
    private LinkedList<GameObject> staticObjects;
    private LinkedList<IDynamicObject> dynamicObjects;

    private Random pillGenerator = new Random(5);
    private Random blockWidthGenerator = new Random((int)startingX);
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
            float worldY = getY(worldX);
            GroundBlock block = new GroundBlock(worldX, worldY, getWidth(), GROUNDBLOCK_HEIGHT);
            staticObjects.add(block);
            spawnWeightPill(worldX, worldY);
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

    private float getWidth(){
        return GROUNDBLOCK_WIDTH + (blockWidthGenerator.nextFloat() - 0.5f) * 0.5f;
    }

    private void spawnWeightPill(float x, float y){
        if(pillGenerator.nextDouble() > 0.6){
            WeightPill pill = new WeightPill(x, y + 0.2f, "pill" + x + y);
            dynamicObjects.add(pill);
        }
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
