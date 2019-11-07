package graphics;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Player extends JPanel implements KeyListener {
    private static final int POLYGON_WIDTH = 20;
    private static final int POLYGON_HEIGHT_BODY = POLYGON_WIDTH;
    private static final int POLYGON_HEIGHT = POLYGON_WIDTH + 15;
    private static final int POLYGON_POINTS = 5;

    private int[] polygonXPoints = new int[POLYGON_POINTS];
    private int[] polygonYPoints = new int[POLYGON_POINTS];
    public int x = 0;
    public int y = 0;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawPolygon(polygonXPoints, polygonYPoints, POLYGON_POINTS);
        g.fillPolygon(polygonXPoints, polygonYPoints, POLYGON_POINTS);
    }

    @Override
    public Dimension getPreferredSize() {
        // so that our GUI is big enough
        return new Dimension(500, 500);
    }

    public void updatePlayer(int newX, int newY){
        x += newX;
        y += newY;

        polygonXPoints[0] = x;
        polygonXPoints[1] = x + POLYGON_WIDTH;
        polygonXPoints[2] = x + POLYGON_WIDTH;
        polygonXPoints[3] = Math.round(x + POLYGON_WIDTH/ 2f);
        polygonXPoints[4] = x;

        polygonYPoints[0] = y;
        polygonYPoints[1] = y;
        polygonYPoints[2] = y + POLYGON_HEIGHT_BODY;
        polygonYPoints[3] = y + POLYGON_HEIGHT;
        polygonYPoints[4] = y + POLYGON_HEIGHT_BODY;

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            updatePlayer(1, 0);
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            updatePlayer(-1, 0);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            updatePlayer(0, -1);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            updatePlayer(0, 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}