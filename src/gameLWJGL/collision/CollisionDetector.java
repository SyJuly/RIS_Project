package gameLWJGL.collision;

import gameLWJGL.objects.GameObject;
import gameLWJGL.objects.ObjectHandler;
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
                Collision collision = AABB.getCollision(dynamicObject, staticObject);
                if(collision.isColliding){
                    dynamicObject.handleCollision(collision);
                }
            }
        }
    }
}
