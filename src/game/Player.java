package game;

import java.awt.*;

public class Player extends GameObject implements IMoveable {

    private boolean up, down, right, left = false;

    protected Player(int x, int y, ID id) {
        super(x, y, id);
    }
    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(up) velY = -5;
        else if(!down) velY = 0;

        if(down) velY = 5;
        else if(!up) velY = 0;

        if(right) velX = 5;
        else if(!left) velX = 0;

        if(left) velX = -5;
        else if(!right) velX = 0;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x,y, 32, 32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y, 32, 32);
    }

    @Override
    public void move(boolean up, boolean down, boolean right, boolean left) {
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
    }
}
