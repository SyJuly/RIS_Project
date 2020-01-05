package gameLWJGL.world;

import gameLWJGL.objects.GameObject;
import network.IMsgApplicator;
import network.networkMessages.JoinMsg;

import java.util.LinkedList;
import java.util.List;

public class ObjectHandler implements IMsgApplicator<JoinMsg> {

    List<GameObject> objects = new LinkedList<>();

    public void update(){
        for(int i = 0; i < objects.size(); i++){
            GameObject tempObject = objects.get(i);
            tempObject.update();
        }
    }

    public void render(Camera camera){
        for(int i = 0; i < objects.size(); i++){
            GameObject tempObject = objects.get(i);
            tempObject.render(camera);
        }
    }

    public void addObject(GameObject tempObject){
        objects.add(tempObject);
    }

    public void removeObject(GameObject tempObject){
        objects.remove(tempObject);
    }

    public List<GameObject> getDynamicObjects(){
        return objects;
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
    }
}
