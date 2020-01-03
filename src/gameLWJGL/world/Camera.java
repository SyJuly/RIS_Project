package gameLWJGL.world;

import gameLWJGL.objects.Player;

public class Camera {

    private Player player;
    public float x, y;

    public Camera(Player player){
        this.player = player;
    }

    public void update(){
        x = player.x;
        y = player.y;
    }

    public float getXOffset(float objX){
        return objX - x;
    }

    public float getYOffset(float objY){
        return objY - y;
    }
}
