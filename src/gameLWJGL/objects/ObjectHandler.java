package gameLWJGL.objects;

import gameLWJGL.world.Camera;
import gameLWJGL.world.World;
import network.IMsgApplicator;
import network.networkMessages.DynamicObjectsMsg;

import java.io.IOException;
import java.util.*;


public class ObjectHandler implements IMsgApplicator<DynamicObjectsMsg>{

    private PlayerManager playerManager;
    private World world;
    private Map<String, GameObject> objects = new HashMap<>();
    private List<IObjectHolder> objectHolders = new ArrayList<>();

    private ArrayList<String> objectIdsThatWhereNotFound = new ArrayList<>();

    public ObjectHandler(PlayerManager playerManager, World world){
        this.playerManager = playerManager;
        this.world = world;
        objectHolders.add(playerManager);
        objectHolders.add(world);
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
        Iterator<IObjectHolder> iter = objectHolders.iterator();
        while(iter.hasNext()) {
            IObjectHolder objectHolder = iter.next();
            GameObject[] newlyCreatedObjects = objectHolder.getNewlyCreatedObjects();
            String[] removedObjects = objectHolder.getRemovedObjects();
            for(int i = 0; i < newlyCreatedObjects.length; i++){
                GameObject gameObject = newlyCreatedObjects[i];
                objects.put(gameObject.id, gameObject);
            }
            for(int i = 0; i < removedObjects.length; i++){
                String removedObjectId = removedObjects[i];
                objects.remove(removedObjectId);
            }
        }
    }

    public List<GameObject> getDynamicObjects(){
        return new ArrayList<>(objects.values());
    }

    public void createOrUpdateObject(float x, float y, float width, float height, String id, int objectTypeCode, Float[] specifics){
        if(!objects.containsKey(id)){
            ObjectType type = ObjectType.values()[objectTypeCode];
            switch (type) {
                case PLAYER:
                    playerManager.createPlayer(x,y,id, specifics); break;
                default:
                    System.out.println("didnt find id: " + id);
                    objectIdsThatWhereNotFound.add(id);
            }
        } else {
            GameObject gameObject = objects.get(id);
            gameObject.x = x;
            gameObject.y = y;
            gameObject.width = width;
            gameObject.height = height;
        }
    }

    public void acknowledgeEndOfDynamicObjectsMsg(){
        updateObjectsList();
        objects.keySet().removeAll(objectIdsThatWhereNotFound);
        objectIdsThatWhereNotFound.clear();
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
