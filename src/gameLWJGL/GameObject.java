package gameLWJGL;

import gameLWJGL.collision.AABB;
import gameLWJGL.collision.Collision;

public abstract class GameObject {

    public float x, y;

    public AABB boundingBox;

    protected GameObject(float x, float y){
        this.x = x;
        this.y = y;
    }

    public abstract void update();
    public abstract void render();
    public abstract void handleCollision(Collision collisionData);
}

