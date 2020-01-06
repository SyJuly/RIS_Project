package gameLWJGL.objects;

import gameLWJGL.collision.Collision;
import gameLWJGL.input.IMoveable;
import gameLWJGL.world.Camera;

import static org.lwjgl.opengl.GL11.*;

public class NonGravityObject extends GameObject implements IMoveable {

    private final static float MOVING_SPEED = 0.005f;

    private int xDirection = 0;
    private int yDirection = 0;

    public NonGravityObject(float x, float y, float size, String id, ObjectType objectType){
        super(x,y, size, size, id, objectType);
    }

    @Override
    public void update() {
        x += xDirection * MOVING_SPEED;
        y += yDirection * MOVING_SPEED;
    }

    @Override
    public void render(Camera camera){
        float xOffset = camera.getXOffset(x);
        float yOffset = camera.getYOffset(y);
        glBegin(GL_QUADS);
        glColor4f(1,1,1,0);
        glVertex2f(-width + xOffset, height + yOffset);
        glVertex2f(width + xOffset, height + yOffset);
        glVertex2f(width + xOffset, -height + yOffset);
        glVertex2f(-width + xOffset, -height + yOffset);
        glEnd();
    }

    @Override
    public void handleCollision(Collision collisionData) {
        x -= xDirection * MOVING_SPEED;
        y -= yDirection * MOVING_SPEED;
        xDirection = 0;
        yDirection = 0;
    }

    @Override
    public void move(int xDirection, int yDirection) {
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }
}