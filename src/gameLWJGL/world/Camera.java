package gameLWJGL.world;

import gameLWJGL.objects.Player;

public class Camera {

    private final float CAMERA_THRESHOLD = 0.3f;
    private Player player;
    public float x, y;

    public Camera(Player player){
        this.player = player;
    }
    public Camera(){ }

    public void update(){
        if(player == null) return;
        float xDiff = Math.abs(x - player.x);
        float yDiff = Math.abs(y - player.y);
        if(xDiff > CAMERA_THRESHOLD){
            x = player.x > x ? player.x - CAMERA_THRESHOLD : player.x + CAMERA_THRESHOLD;
        }
        if(yDiff > CAMERA_THRESHOLD){
            y = player.y > y ? player.y - CAMERA_THRESHOLD : player.y + CAMERA_THRESHOLD;
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getXOffset(float objX){
        return objX - x;
    }

    public float getYOffset(float objY){
        return objY - y;
    }
}
