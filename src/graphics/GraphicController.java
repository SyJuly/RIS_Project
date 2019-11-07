package graphics;
import javax.swing.*;

public class GraphicController {
    public static void main(String[] args) {
        JFrame f=new JFrame();//creating instance of JFrame

        Player p = new Player();

        f.getContentPane().add(p);//adding button in JFrame
        f.pack();
        f.setLocationByPlatform(true);
        f.setSize(800,600);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible


    }
}