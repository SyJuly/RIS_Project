package gameLWJGL.world;

import gameLWJGL.objects.GameObject;

import java.util.LinkedList;
import java.util.List;

public class ObjectHandler {

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

}
