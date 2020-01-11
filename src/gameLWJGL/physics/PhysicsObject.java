package gameLWJGL.physics;

import gameLWJGL.collision.Collision;
import gameLWJGL.collision.CollisionDirection;
import gameLWJGL.objects.GameObject;
import gameLWJGL.objects.ObjectType;

public abstract class PhysicsObject extends GameObject {

    public static final float FRICTION = 0.99f;
    public static final float GRAVITY = 0.002f;
    public static final float GROUND_THRESHOLD = 0.0021f;
    public static final float COLLISION_THRESHOLD = 0.00001f;

    public float speedX = 0;
    public float speedY = 0;

    protected float maxSpeed = 0.05f;

    protected boolean isOnGround = false;
    protected float isOnGroundAt = 0;

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
        if(!isOnGround()){
            accelerate(0, -GRAVITY);
        }
        isOnGround = false;
    }

    private boolean isOnGround(){
        return isOnGround && Math.abs(y - isOnGroundAt) <  GROUND_THRESHOLD;
    }


    @Override
    public void handleCollision(Collision collisionData) {
        if(collisionData.isStatic && collisionData.aMetBs == CollisionDirection.UPSIDE) {
            GameObject collidingGameObject = collisionData.gameObjects[1];
            float upperBorder = collidingGameObject.y + collidingGameObject.height;
            y = upperBorder + height - COLLISION_THRESHOLD;
            isOnGroundAt = y;
            isOnGround = true;
            speedY = 0;
        }

    }

    @Override
    public Float[] getSpecifics() {
        return new Float[]{speedX, speedY};
    }

    @Override
    public void setSpecifics(Float[] specifics) {
        speedX = specifics[0];
        speedY = specifics[1];
    }

}
