package gameLWJGL.objects;

import gameLWJGL.input.IMoveable;
import gameLWJGL.physics.PhysicsObject;
import gameLWJGL.world.Camera;

import static org.lwjgl.opengl.GL11.*;

public class Player extends PhysicsObject implements IMoveable {


    public static final double JUMP_STRENGTH = 0.07f;
    public static final double SPEED = 0.03f;

    public Player(float x, float y, float size, String id){
        super(x,y, size, size, id, ObjectType.Player);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Camera camera){
        float xOffset = camera.getXOffset(x);
        float yOffset = camera.getYOffset(y);
        glBegin(GL_QUADS);
        glColor4f(1,0,0,0);
        glVertex2f(-width + xOffset, height + yOffset);
        glVertex2f(width + xOffset, height + yOffset);
        glVertex2f(width + xOffset, -height + yOffset);
        glVertex2f(-width + xOffset, -height + yOffset);
        glEnd();

        /*glBegin(GL_QUADS);
                glTexCoord2f(0,0);
                glVertex2f(-squareSize + x, squareSize + y);
                glTexCoord2f(1,0);
                glVertex2f(squareSize + x, squareSize + y);
                glTexCoord2f(1,1);
                glVertex2f(squareSize + x, -squareSize + y);
                glTexCoord2f(0,1);
                glVertex2f(-squareSize + x, -squareSize + y);
            glEnd();*/
    }

    @Override
    public void move(int xDirection, int yDirection) {
        if(yDirection > 0){
            jump();
        }
        moveWithPhysics(SPEED * xDirection, 0);
    }

    public void jump() {
        accelerate(0, JUMP_STRENGTH);
    }
}
