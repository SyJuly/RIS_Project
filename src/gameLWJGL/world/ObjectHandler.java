package gameLWJGL.world;

import gameLWJGL.objects.GameObject;
import gameLWJGL.objects.ObjectType;
import gameLWJGL.objects.Player;
import network.IMsgApplicator;
import network.networkMessages.JoinMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectHandler implements IMsgApplicator<JoinMsg> {

    Map<String, GameObject> objects = new HashMap<>();

    private boolean hasNewPlayer = false;

    public void update(){
        for (GameObject gameObject : objects.values()) {
            gameObject.update();
        }
    }

    public void render(Camera camera){
        for (GameObject gameObject : objects.values()) {
            gameObject.render(camera);
        }
    }

    public void addObject(GameObject tempObject){
        objects.put(tempObject.id, tempObject);
    }

    public void removeObject(GameObject tempObject){
        objects.remove(tempObject.id);
    }

    public List<GameObject> getDynamicObjects(){
        return new ArrayList<GameObject>(objects.values());
    }
    public void createOrUpdateObject(float x, float y, float width, float height, String id, int objectTypeCode){
        if(!objects.containsKey(id)){
            ObjectType type = ObjectType.values()[objectTypeCode];
            switch (type) {
                case PLAYER:
                    addObject(new Player(x,y,width,id)); break;
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

    @Override
    public boolean shouldSendMessage() {
        return false;
    }

    @Override
    public JoinMsg getMessage() {
        return null;
    }

    @Override
    public void receive(JoinMsg networkMsg) {
        System.out.println("Creating player.");
        Player player = new Player(0,0, 0.06f, networkMsg.name);
        addObject(player);
        hasNewPlayer = true;
    }

    public boolean hasNewPlayer() {
        return hasNewPlayer;
    }

    public void setHasNewPlayer(boolean hasNewPlayer) {
        this.hasNewPlayer = hasNewPlayer;
    }
}
