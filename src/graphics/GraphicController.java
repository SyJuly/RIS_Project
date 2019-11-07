package graphics;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicController {
    public static void main(String[] args) {
        JFrame f=new JFrame();//creating instance of JFrame

        Player p = new Player();

        p.updatePlayer(20, 5);

        f.getContentPane().add(p);//adding button in JFrame
        f.pack();
        f.setLocationByPlatform(true);
        f.setSize(800,600);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.addKeyListener(p);


    }
}