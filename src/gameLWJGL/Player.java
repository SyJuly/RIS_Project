package gameLWJGL;

import static org.lwjgl.opengl.GL11.*;

public class Player extends GameObject {

    private float squareSize = 1;

    public Player(float x, float y, float size){
        super(x,y);
        squareSize = size;
    }

    @Override
    public void update() {
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
    }
}
