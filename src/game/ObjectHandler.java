package game;

import java.awt.*;
import java.util.LinkedList;

public class ObjectHandler {

    LinkedList<GameObject> objects = new LinkedList<>();

    public void tick(){
        for(int i = 0; i < objects.size(); i++){
            GameObject tempObject = objects.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g){
        for(int i = 0; i < objects.size(); i++){
            GameObject tempObject = objects.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject tempObject){
        objects.add(tempObject);
    }

    public void removeObject(GameObject tempObject){
        objects.remove(tempObject);
    }

}
