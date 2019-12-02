package gameLWJGL.collision;

import gameLWJGL.world.GameObject;

public class Collision {
    public boolean isColliding;
    public GameObject[] gameObjects;

    public Collision(boolean isColliding, GameObject[] gameObjects) { // no check if objects are inside each other
        this.isColliding = isColliding;
        this.gameObjects = gameObjects;
    }
}