package gameLWJGL.physics;

import gameLWJGL.collision.Collision;
import gameLWJGL.collision.CollisionDirection;
import gameLWJGL.objects.GameObject;
import gameLWJGL.objects.ObjectType;

public abstract class PhysicsObject extends GameObject {

    public static final float FRICTION = 0.99f;
    public static final float GRAVITY = 0.002f;
    public static final float COLLISION_THRESHOLD = 0.0001f;

    public float speedX = 0;
    public float speedY = 0;

    protected float maxSpeed = 0.05f;

    protected boolean isOnGround = false;
    protected boolean isJumping = false;
    protected float isOnGroundAtY = 0;

    protected float weight = 0.005f;

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
    public boolean update() {
        moveWithPhysics(speedX, speedY);
        speedX = Math.min(speedX * FRICTION, maxSpeed);
        speedY = Math.min(speedY * FRICTION, maxSpeed);
        if(isOnGround && !isJumping){
            y = isOnGroundAtY;
        } else {
            accelerate(0, -GRAVITY);
        }
        isJumping = false;
        return true; //TODO: could be improved
    }

    public void jump(double jump_strength) {
        if(!isJumping){
            isJumping = true;
            accelerate(0, jump_strength);
        }
    }


    @Override
    public void handleCollision(Collision collisionData) {
        if(collisionData.isStatic && collisionData.aMetBs == CollisionDirection.UPSIDE) {
            GameObject collidingGameObject = collisionData.gameObjects[1];
            float upperBorder = collidingGameObject.y + collidingGameObject.height;
            isOnGroundAtY = upperBorder + height - COLLISION_THRESHOLD;
            isOnGround = true;
            speedY = 0;
        }
    }

    @Override
    public float[] getSpecifics() {
        return new float[]{speedX, speedY};
    }

    @Override
    public void setSpecifics(float[] specifics) {
        speedX = specifics[0];
        speedY = specifics[1];
    }

}
