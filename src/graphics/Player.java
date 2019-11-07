package graphics;
import java.awt.*;
import javax.swing.*;

public class Player extends JPanel {
    private static final int RECT_X = 20;
    private static final int RECT_Y = RECT_X;
    private static final int RECT_WIDTH = 20;
    private static final int RECT_HEIGHT = RECT_WIDTH;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        g.drawPolygon(GetTriangleXPoints(), GetTriangleYPoints(), 3);


        //g.drawImage(image, 0,0, this);
        g.setColor(Color.BLACK);
        g.fillRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        g.fillPolygon(GetTriangleXPoints(), GetTriangleYPoints(), 3);
    }

    @Override
    public Dimension getPreferredSize() {
        // so that our GUI is big enough
        return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
    }

    private int[] GetTriangleXPoints(){
        int[] triangleXPoints = new int[3];
        triangleXPoints[0] = RECT_X;
        triangleXPoints[1] = RECT_X + RECT_WIDTH;
        triangleXPoints[2] = Math.round(RECT_X + RECT_WIDTH/ 2f);
        return triangleXPoints;
    }

    private int[] GetTriangleYPoints(){
        int[] triangleYPoints = new int[3];
        triangleYPoints[0] = RECT_Y + RECT_HEIGHT;
        triangleYPoints[1] = RECT_Y + RECT_HEIGHT;
        triangleYPoints[2] = RECT_Y + RECT_HEIGHT + 15;
        return triangleYPoints;
    }
}