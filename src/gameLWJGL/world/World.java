package gameLWJGL.world;

import gameLWJGL.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class World {

    private List<GameObject> ground;
    private int numberOfGroundBoxes = 3;
    private int worldSize = 10;
    private float groundBoxDistance = 0.3f;

    public World (){
        ground = new ArrayList<>();
    }

    public void buildWorld(){
        for (float x = 0; x < worldSize; x+=0.7f){
            GroundBlock block = new GroundBlock(x, getY(x), 0.4f, 0.02f);
            ground.add(block);
        }
    }

    public void render(){
        for(int i = 0; i < ground.size(); i++){
            GameObject tempObject = ground.get(i);
            tempObject.render();
        }
    }

    private float getY(float x){
        return (float)(0.4f * Math.cos(0.5f * x) - 0.4f * Math.cos(1f * x) - 0.4f * Math.cos(2f * x) + 0.3f);
    }

    // y= 0.4 cos(x) - 0.4 cos(2 x) - 0.4 cos(4 x) + 0.3


    public List<GameObject> getStaticObjects(){
        return ground;
    }
}
