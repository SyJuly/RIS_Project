package gameLWJGL.collision;

import gameLWJGL.objects.GameObject;

public class Collision {

    public CollisionDirection aMetBs;
    public boolean isColliding;
    public GameObject[] gameObjects;

    public Collision(boolean isColliding, GameObject[] gameObjects, CollisionDirection aMetBs) { // no check if objects are inside each other
        this.isColliding = isColliding;
        this.gameObjects = gameObjects;
        this.aMetBs = aMetBs;
    }
}