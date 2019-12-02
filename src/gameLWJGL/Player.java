package gameLWJGL;

import gameLWJGL.collision.AABB;
import gameLWJGL.collision.Collision;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class Player extends GameObject implements IMoveable{

    private final static float MOVING_SPEED = 0.005f;

    private float squareSize = 0.5f;

    private int xDirection = 0;
    private int yDirection = 0;

    public Player(float x, float y, float size){
        super(x,y);
        squareSize = size;

        boundingBox = new AABB( new Vector2f(x+ (squareSize /2f), y - (squareSize /2f)), new Vector2f(squareSize, squareSize));
    }

    @Override
    public void update() {
        x += xDirection * MOVING_SPEED;
        y += yDirection * MOVING_SPEED;
    }

    @Override
    public void render(){
        glBegin(GL_QUADS);
        glColor4f(1,0,0,0);
        glVertex2f(-squareSize + x, squareSize + y);
        glVertex2f(squareSize + x, squareSize + y);
        glVertex2f(squareSize + x, -squareSize + y);
        glVertex2f(-squareSize + x, -squareSize + y);
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
    public void handleCollision(Collision collisionData) {
        System.out.println("Player has collision.");
    }

    @Override
    public void move(int xDirection, int yDirection) {
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }
}
