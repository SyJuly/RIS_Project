package gameLWJGL.objects;

import gameLWJGL.collision.Collision;
import gameLWJGL.world.Camera;

public abstract class GameObject {

    public float x, y;
    public float width, height;

    protected GameObject(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update();
    public abstract void render(Camera camera);
    public abstract void handleCollision(Collision collisionData);
}

