package gameLWJGL.objects;

import gameLWJGL.world.Camera;
import network.IMsgApplicator;
import network.networkMessages.DynamicObjectsMsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ObjectHandler implements IMsgApplicator<DynamicObjectsMsg>{

    private PlayerManager playerManager;
    private Map<String, GameObject> objects = new HashMap<>();
    private List<IObjectHolder> objectHolders = new ArrayList<>();

    public ObjectHandler(PlayerManager playerManager){
        this.playerManager = playerManager;
        objectHolders.add(playerManager);
    }

    public void updateObjects(){
        for (GameObject gameObject : objects.values()) {
            gameObject.update();
        }
    }

    public void render(Camera camera){
        for (GameObject gameObject : objects.values()) {
            gameObject.render(camera);
        }
    }

    public void updateObjectsList(){
        for(IObjectHolder objectHolder: objectHolders){
            GameObject[] newlyCreatedObjects = objectHolder.getNewlyCreatedObjects();
            String[] removedObjects = objectHolder.getRemovedObjects();
            for(GameObject gameObject: newlyCreatedObjects){
                objects.put(gameObject.id, gameObject);
            }
            for(String removedObjectId: removedObjects){
                objects.remove(removedObjectId);
            }
        }
    }

    public List<GameObject> getDynamicObjects(){
        return new ArrayList<>(objects.values());
    }

    public void createOrUpdateObject(float x, float y, float width, float height, String id, int objectTypeCode){
        if(!objects.containsKey(id)){
            ObjectType type = ObjectType.values()[objectTypeCode];
            switch (type) {
                case PLAYER:
                    playerManager.createPlayer(x,y,width,id); break;
                default: return;
            }
        } else {
            GameObject gameObject = objects.get(id);
            gameObject.x = x;
            gameObject.y = y;
            gameObject.width = width;
            gameObject.height = height;
        }
    }

    public boolean shouldSendMessage() {
        return true;
    }

    @Override
    public DynamicObjectsMsg getMessage() {
        return new DynamicObjectsMsg(ObjectHandler.this.getDynamicObjects());
    }

    @Override
    public void receive(DynamicObjectsMsg networkMsg) {
        try {
            networkMsg.deserializeAndApplyData(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
