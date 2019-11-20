package graphics_depri;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
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
    public int speed = 100;

    private final Set<Integer> pressed = new HashSet<>();

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
        return new Dimension(1200, 800);
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
    public synchronized void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
        if (pressed.size() > 0) {
            int xMovement = 0;
            int yMovement = 0;
            for(Integer key : pressed){

                if(key == KeyEvent.VK_RIGHT){
                    xMovement = 1;
                }
                if(key == KeyEvent.VK_LEFT){
                    xMovement = -1;
                }
                if(key == KeyEvent.VK_UP){
                    yMovement = -1;
                }
                if(key == KeyEvent.VK_DOWN){
                    yMovement = 1;
                }
            }
            updatePlayer(xMovement * speed, yMovement * speed);
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }
}