package gameLWJGL.objects;

import gameLWJGL.collision.Collision;
import gameLWJGL.world.Camera;

public abstract class GameObject {

    public float x, y;
    public float width, height;
    public String id;
    public ObjectType objectType;

    protected GameObject(float x, float y, float width, float height, String id, ObjectType objectType){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.objectType = objectType;
    }

    public abstract void update();
    public abstract void render(Camera camera);
    public abstract void handleCollision(Collision collisionData);
}

