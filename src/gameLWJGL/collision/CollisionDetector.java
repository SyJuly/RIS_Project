package gameLWJGL.collision;

import gameLWJGL.GameObject;
import gameLWJGL.ObjectHandler;
import gameLWJGL.world.World;

public class CollisionDetector {

    private World world;
    private ObjectHandler objectHandler;

    public CollisionDetector(World world, ObjectHandler objectHandler){
        this.world = world;
        this.objectHandler = objectHandler;
    }

    public void detectCollisions(){
        for (GameObject staticObject : world.getStaticObjects()) {
            for (GameObject dynamicObject: objectHandler.getDynamicObjects()) {
                Collision collision = staticObject.boundingBox.getCollision(dynamicObject.boundingBox);
                if(collision.isIntersecting){
                    collision.gameObjects = new GameObject[]{dynamicObject, staticObject};
                    dynamicObject.handleCollision(collision);
                }
            }
        }
    }
}
