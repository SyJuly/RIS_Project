package gameLWJGL.objects;

import gameLWJGL.input.IMoveable;
import gameLWJGL.physics.PhysicsObject;

import static org.lwjgl.opengl.GL11.*;

public class Player extends PhysicsObject implements IMoveable {


    public static final double JUMP_STRENGTH = 0.07f;
    public static final double SPEED = 0.03f;

    public Player(float x, float y, float size){
        super(x,y, size, size);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(){
        glBegin(GL_QUADS);
        glColor4f(1,0,0,0);
        glVertex2f(-width + x, height + y);
        glVertex2f(width + x, height + y);
        glVertex2f(width + x, -height + y);
        glVertex2f(-width + x, -height + y);
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
