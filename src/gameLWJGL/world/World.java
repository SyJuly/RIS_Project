package gameLWJGL.world;

import gameLWJGL.GameObject;

import java.util.ArrayList;
import java.util.List;

public class World {

    private List<GameObject> ground;
    private int numberOfGroundBoxes = 3;
    private float groundBoxDistance = 0.3f;

    public World (){
        ground = new ArrayList<>();
    }

    public void buildWorld(){
        float distanceCounter = 0f;
        for (int i = 0; i < numberOfGroundBoxes; i++){
            GroundBlock block = new GroundBlock(-0.5f + distanceCounter,-0.5f, 0.2f, 0.02f);
            distanceCounter += groundBoxDistance;
            ground.add(block);
        }
    }

    public void render(){
        for(int i = 0; i < ground.size(); i++){
            GameObject tempObject = ground.get(i);
            tempObject.render();
        }
    }

    public List<GameObject> getStaticObjects(){
        return ground;
    }
}
