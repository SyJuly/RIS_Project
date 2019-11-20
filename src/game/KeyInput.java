package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyInput implements KeyListener {

    private List<IMoveable> moveables;
    private boolean up, down, right, left = false;

    public KeyInput(ArrayList<IMoveable> moveables){
        this.moveables = moveables != null ? moveables : new ArrayList<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        updateMoveables(key, true);
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        updateMoveables(key, false);
    }

    private void updateMoveables(int key, boolean state) {
        if(key == KeyEvent.VK_UP) up = state;
        if(key == KeyEvent.VK_DOWN) down = state;
        if(key == KeyEvent.VK_RIGHT) right = state;
        if(key == KeyEvent.VK_LEFT) left = state;

        for (int i = 0; i < moveables.size(); i++){
            IMoveable tempMovable = moveables.get(i);
            tempMovable.move(up, down, right, left);
        }
    }


    public void addMoveable(IMoveable moveable){
        moveables.add(moveable);
    }

    public void removeMoveable(IMoveable moveable){
        moveables.remove(moveable);
    }
}
