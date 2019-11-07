package graphics;
import java.awt.*;
import javax.swing.*;

public class Player extends JPanel {
    private static final int RECT_WIDTH = 20;
    private static final int RECT_HEIGHT = RECT_WIDTH;
    private static final int TRIA_POINTS = 3;
    private static final int TRIA_HEIGHT = 15;

    private int[] triangleXPoints = new int[TRIA_POINTS];
    private int[] triangleYPoints = new int[TRIA_POINTS];
    private int x = 0;
    private int y = 0;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, RECT_WIDTH, RECT_HEIGHT);
        g.fillRect(x, y, RECT_WIDTH, RECT_HEIGHT);
        g.drawPolygon(triangleXPoints, triangleYPoints, TRIA_POINTS);
        g.fillPolygon(triangleXPoints, triangleYPoints, TRIA_POINTS);
    }

    @Override
    public Dimension getPreferredSize() {
        // so that our GUI is big enough
        return new Dimension(RECT_WIDTH + 2 * 20, RECT_HEIGHT + 2 * 20);
    }

    public void updatePlayer(int newX, int newY){
        x = newX;
        y = newY;

        triangleXPoints[0] = newX;
        triangleXPoints[1] = newX + RECT_WIDTH;
        triangleXPoints[2] = Math.round(newX + RECT_WIDTH/ 2f);

        triangleYPoints[0] = newY + RECT_HEIGHT;
        triangleYPoints[1] = newY + RECT_HEIGHT;
        triangleYPoints[2] = newY + RECT_HEIGHT + TRIA_HEIGHT;
    }
}