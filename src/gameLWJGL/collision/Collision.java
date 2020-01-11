package gameLWJGL.collision;

import gameLWJGL.objects.GameObject;

public class Collision {

    public CollisionDirection aMetBs;
    public boolean isColliding;
    public boolean isStatic;
    public GameObject[] gameObjects;

    public Collision(boolean isColliding, GameObject[] gameObjects, CollisionDirection aMetBs, boolean isStatic) { // no check if objects are inside each other
        this.isColliding = isColliding;
        this.isStatic = isStatic;
        this.gameObjects = gameObjects;
        this.aMetBs = aMetBs;
    }
}