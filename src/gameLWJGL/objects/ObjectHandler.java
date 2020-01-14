package gameLWJGL.objects;

import gameLWJGL.world.Camera;
import gameLWJGL.world.World;
import gameLWJGL.world.WorldUpdates;
import network.common.IMsgApplicator;
import network.common.networkMessages.DynamicObjectsMsg;

import java.io.IOException;
import java.util.*;


public class ObjectHandler implements IMsgApplicator<DynamicObjectsMsg>{

    private PlayerManager playerManager;
    private AIManager aiManager;
    private Map<String, GameObject> objects = new HashMap<>();
    private ArrayList<GameObject> updatedObjects = new ArrayList<>();
    private ArrayList<GameObject> removedObjects = new ArrayList<>();
    private List<IObjectHolder> objectHolders = new ArrayList<>();

    public ObjectHandler(PlayerManager playerManager, AIManager aiManager, World world){
        this.playerManager = playerManager;
        this.aiManager = aiManager;
        objectHolders.add(playerManager);
        objectHolders.add(world);
        objectHolders.add(aiManager);
    }

    public void updateObjects(){
        for (GameObject gameObject : objects.values()) {
            if(gameObject.update()){
                updatedObjects.add(gameObject);
            }
        }
    }

    public void render(Camera camera){
        List<GameObject> objectsToRender = new ArrayList<>(objects.values());
        Iterator<GameObject> iter = objectsToRender.iterator();
        while(iter.hasNext()) {
            GameObject gameObject= iter.next();
            gameObject.render(camera);
        }
    }

    public void updateObjectsList(){
        Queue<GameObject> objectsToUpdate = WorldUpdates.getInstance().getObjectUpdate();
        while(!objectsToUpdate.isEmpty()) {
            GameObject gameObject = objectsToUpdate.poll();
            objects.put(gameObject.id, gameObject);
        }
        Queue<String> objectsToRemove = WorldUpdates.getInstance().getObjectRemove();
        while(!objectsToRemove.isEmpty()) {
            String removedObjectId = objectsToRemove.poll();
            GameObject removedGameObject = objects.remove(removedObjectId);
            removedObjects.add(removedGameObject);
            if(updatedObjects.contains(removedGameObject)){
                updatedObjects.remove(removedGameObject);
            }
        }
    }

    public List<GameObject> getDynamicObjects(){
        return new ArrayList<>(objects.values());
    }

    public void createOrUpdateObject(float x, float y, float width, float height, String id, int objectTypeCode, float[] specifics){
        if(!objects.containsKey(id)){
            ObjectType type = ObjectType.values()[objectTypeCode];
            switch (type) {
                case PLAYER:
                    playerManager.createPlayer(x,y,id, specifics); break;
                case AI:
                    aiManager.createAI(x,y,id, specifics); break;
                default: return;
            }
        } else {
            GameObject gameObject = objects.get(id);
            gameObject.x = x;
            gameObject.y = y;
            gameObject.width = width;
            gameObject.height = height;
            gameObject.setSpecifics(specifics);
        }
    }

    public void acknowledgeEndOfDynamicObjectsMsg(){
        updateObjectsList();
    }

    public boolean shouldSendMessage() {
        return (updatedObjects.size() > 0 || removedObjects.size() > 0);
    }

    @Override
    public DynamicObjectsMsg getMessage() {
        return new DynamicObjectsMsg(this, false);
    }

    @Override
    public DynamicObjectsMsg getStartMessage() {
        return new DynamicObjectsMsg(this, true);
    }

    @Override
    public void receive(DynamicObjectsMsg networkMsg) {
        try {
            networkMsg.deserializeAndApplyData(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeObject(String removedObjectId) {
        if(!objects.containsKey(removedObjectId)){
            return;
        }
        Iterator<IObjectHolder> iter = objectHolders.iterator();
        while(iter.hasNext()) {
            IObjectHolder objectHolder = iter.next();
            objectHolder.removeObject(removedObjectId);
        }
        objects.remove(removedObjectId);
    }

    public List<GameObject> getUpdatedObjects() {
        return updatedObjects;
    }

    public List<GameObject> getRemovedObjects() {
        return removedObjects;
    }

    public void clearMsgLists() {
        updatedObjects.clear();
        removedObjects.clear();
    }
}
