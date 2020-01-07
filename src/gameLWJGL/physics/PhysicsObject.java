package gameLWJGL.physics;

import gameLWJGL.collision.Collision;
import gameLWJGL.objects.GameObject;
import gameLWJGL.objects.ObjectType;

public abstract class PhysicsObject extends GameObject {

    public static final float FRICTION = 0.99f;
    public static final float GRAVITY = 0.002f;

    protected float speedX = 0;
    protected float speedY = 0;

    protected float maxSpeed = 0.05f;

    protected PhysicsObject(float x, float y, float width, float height, String id, ObjectType objectType) {
        super(x, y, width, height, id, objectType);
    }

    public void accelerate(double accelerationX, double accelerationY) {
        speedX += accelerationX;
        speedY += accelerationY;
    }

    public void moveWithPhysics(double xDelta, double yDelta) {
        x += xDelta;
        y += yDelta;
    }

    @Override
    public void update() {
        moveWithPhysics(speedX, speedY);
        speedX = Math.min(speedX * FRICTION, maxSpeed);
        speedY = Math.min(speedY * FRICTION, maxSpeed);
        accelerate(0, -GRAVITY);
    }


    @Override
    public void handleCollision(Collision collisionData) {
        //speedX = 0;
        speedY = 0;
    }
}
