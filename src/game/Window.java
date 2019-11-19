package game;
import javax.swing.*;
import java.awt.*;

public class Window {

    public Window(int width, int height, String title, Game game){
        JFrame frame = new JFrame(title);//creating instance of JFrame

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.add(game);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);//making the frame visible
    }
}
