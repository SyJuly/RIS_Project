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

        for (GameObject dynamicObject: objectHandler.getDynamicObjects()) {
            for (GameObject staticObject : world.getStaticObjects()) {
                handleCollision(dynamicObject, staticObject, true);
            }
            for (GameObject otherDynamicObject: objectHandler.getDynamicObjects()) {
                if(dynamicObject.id == otherDynamicObject.id) continue;
                handleCollision(dynamicObject, otherDynamicObject, false);
            }
        }
    }

    private void handleCollision(GameObject dynamicObject, GameObject otherObject, boolean isStatic) {
        Collision collision = AABB.getCollision(dynamicObject, otherObject, isStatic);
        if (collision.isColliding) {
            dynamicObject.handleCollision(collision);
        }
    }
}
