package graphics_depri;
import javax.swing.*;

public class Window {

    Window(GraphicsSystem game) {
        JFrame f=new JFrame();//creating instance of JFrame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Player p = new Player();

        p.updatePlayer(20, 5);

        f.getContentPane().add(p);//adding button in JFrame
        f.pack();
        f.setLocationByPlatform(true);
        f.setSize(1200,800);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.add(game);
        f.addKeyListener(p);
        game.start();
    }
}