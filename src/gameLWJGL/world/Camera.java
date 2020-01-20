package gameLWJGL.world;

import gameLWJGL.objects.Player;
import gameLWJGL.objects.PlayerManager;

import java.util.Iterator;
import java.util.Map;

public class Camera {

    private final float CAMERA_THRESHOLD = 0.3f;
    private PlayerManager playerManager;
    public float x, y;

    public Camera(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    public void update(){
        Map<String, Player> players = playerManager.getPlayers();
        if(players.isEmpty()) return;
        Player player = getPlayerOnTheRight(players);
        float xDiff = Math.abs(x - player.x);
        float yDiff = Math.abs(y - player.y);
        if(xDiff > CAMERA_THRESHOLD){
            x = player.x > x ? player.x - CAMERA_THRESHOLD : player.x + CAMERA_THRESHOLD;
        }
        if(yDiff > CAMERA_THRESHOLD){
            y = player.y > y ? player.y - CAMERA_THRESHOLD : player.y + CAMERA_THRESHOLD;
        }
    }

    private Player getPlayerOnTheRight(Map<String, Player> players) {
        Iterator<Player> iter = players.values().iterator();
        Player rightPlayer = iter.next();
        while (iter.hasNext()){
            Player player = iter.next();
            if(player.x > rightPlayer.x){
                rightPlayer = player;
            }
        }
        return rightPlayer;
    }

    public float getXOffset(float objX){
        return objX - x;
    }

    public float getYOffset(float objY){
        return objY - y;
    }
}
