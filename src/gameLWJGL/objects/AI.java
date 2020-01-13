package gameLWJGL.objects;

import gameLWJGL.collision.Collision;
import gameLWJGL.collision.CollisionDirection;
import gameLWJGL.physics.PhysicsObject;
import gameLWJGL.world.Camera;

import static org.lwjgl.opengl.GL11.*;

public class AI extends PhysicsObject implements IDynamicObject {

    public static final double JUMP_STRENGTH = 0.3f;
    public static final long JUMP_RECOVERY = 1000;
    public static final double SPEED = 0.005f;
    public static final float[] DEFAULT_COLOR = new float[]{1,1,1};
    public static final float INITIAL_SIZE = 0.02f;

    private  long lastJumped = 0;
    private  Float obstacleX = null;
    private  boolean hasBeenDestroyed = false;
    private Player player;

    protected AI(float x, float y, String id, Player player) {
        super(x, y, INITIAL_SIZE, INITIAL_SIZE, id, ObjectType.AI);
        this.player = player;
    }

    protected AI(float x, float y, String id) {
        super(x, y, INITIAL_SIZE, INITIAL_SIZE, id, ObjectType.AI);
    }

    @Override
    public boolean update() {
        if(hasBeenDestroyed || player == null) return false;
        boolean physicsHasBeenUpdated = super.update();
        x += move();

        obstacleX = null;
        isOnGround = false;
        return true; //TODO: could be improved
    }

    @Override
    public void render(Camera camera) {
        float xOffset = camera.getXOffset(x);
        float yOffset = camera.getYOffset(y);
        glBegin(GL_QUADS);

        glColor4f(DEFAULT_COLOR[0],DEFAULT_COLOR[1],DEFAULT_COLOR[2],0);
        glVertex2f(-width + xOffset, height + yOffset);
        glVertex2f(width + xOffset, height + yOffset);
        glVertex2f(width + xOffset, -height + yOffset);
        glVertex2f(-width + xOffset, -height + yOffset);
        glEnd();
    }

    private boolean wayIsBlocked = false;
    private float wayIsBlockedY = 0;

    private float move(){
        float xPlayerDiff = player.x - x;
        System.out.println("wayIsBlockedY? "  + wayIsBlockedY + " | y: " + y);
        if(wayIsBlocked && !isOnGround && y+ 0.1f < wayIsBlockedY){
            wayIsBlocked = false;
            System.out.println("WAY IS UNNNNNNNNNNNNNNnBLOCKED ");
        } else if(xPlayerDiff < 0.01 && xPlayerDiff > -0.01){
            wayIsBlocked = true;
            wayIsBlockedY = y;
            System.out.println("WAY IS BLOCKED ");
        }
        float playerDirection = xPlayerDiff/ Math.abs(xPlayerDiff);
        float obstacleDirection = playerDirection;

        float yDiff = player.y - y;
        if(yDiff > 0){
            jump(yDiff);
        }
        if(obstacleX != null){
            float xObstacleDiff = x - obstacleX;
            obstacleDirection = (xObstacleDiff/ Math.abs(xObstacleDiff));
        }
        float xDirection = wayIsBlocked ? obstacleDirection : playerDirection;
        xDirection = xDirection/ Math.abs(xDirection);
        float xDelta = (float) SPEED * xDirection;
        return xDelta;
    }

    public void jump(float yDiff) {
        if(System.currentTimeMillis() - lastJumped > JUMP_RECOVERY){
            //System.out.println("yDiff: " + yDiff + " | y: " + y);
            accelerate(0, JUMP_STRENGTH * 0.2 * (double) yDiff);
            lastJumped = System.currentTimeMillis();
        }
    }

    @Override
    public void handleCollision(Collision collisionData) {
        super.handleCollision(collisionData);
        GameObject collidingGameObject = collisionData.gameObjects[0].id == id ? collisionData.gameObjects[1] : collisionData.gameObjects[0];
        if(collidingGameObject.objectType == ObjectType.PLAYER && collisionData.aMetBs != CollisionDirection.UPSIDE){
            hasBeenDestroyed = true;
        } else if(collisionData.isStatic){
            obstacleX = collidingGameObject.x;
        }
    }

    @Override
    public boolean shouldBeDestroyed() {
        return hasBeenDestroyed;
    }

    @Override
    public GameObject getGameObject() {
        return this;
    }
}
