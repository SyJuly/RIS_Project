package gameLWJGL.world;

import gameLWJGL.GameObject;
import gameLWJGL.collision.AABB;
import gameLWJGL.collision.Collision;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class GroundBlock extends GameObject {

    private float width;
    private float height;

    public GroundBlock (float x, float y, float width, float height){
        super(x, y);
        this.height = height;
        this.width = width;

        boundingBox = new AABB( new Vector2f(x+ (width /2f), y - (height /2f)), new Vector2f(width, height));
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        glBegin(GL_QUADS);
        glColor4f(1,1,1,0);
        glVertex2f(-width + x, height + y);
        glVertex2f(width + x, height + y);
        glVertex2f(width + x, -height + y);
        glVertex2f(-width + x, -height + y);
        glEnd();
    }

    @Override
    public void handleCollision(Collision collisionData) {

    }
}
