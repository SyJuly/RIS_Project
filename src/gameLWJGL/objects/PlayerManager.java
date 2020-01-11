package gameLWJGL.objects;

import gameLWJGL.input.Input;
import gameLWJGL.world.Camera;
import network.IMsgApplicator;
import network.client.GameClient;
import network.networkMessages.JoinMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements IObjectHolder, IMsgApplicator<JoinMsg> {

    private Map<String, Player> players = new HashMap<>();
    private ArrayList<String> createdPlayers = new ArrayList<>();

    private boolean hasNewPlayer = false;
    private boolean createLocalPlayerOnServer = true;

    private Input input;
    private Camera camera;

    public PlayerManager(Input input, Camera camera){
        this.input = input; //for server
        this.camera = camera;
    }

    public PlayerManager(Camera camera){
        this.camera = camera; // for client
    }

    public void createPlayer(float x, float y, float width, String id, Float[] specifics){
        System.out.println("Created player instance on client.");
        Player player = new Player(x,y,width,id);
        player.setSpecifics(specifics);
        createdPlayers.add(player.id);
        players.put(player.id, player);
        if(id.equals(GameClient.CLIENTID)){
            camera.setPlayer(player);
        }
    }

    public boolean hasNewPlayer() {
        return hasNewPlayer;
    }

    public void setHasNewPlayer(boolean hasNewPlayer) {
        this.hasNewPlayer = hasNewPlayer;
    }

    @Override
    public boolean shouldSendMessage() {
        return createLocalPlayerOnServer;
    }

    @Override
    public JoinMsg getMessage() {
        createLocalPlayerOnServer = false;
        return new JoinMsg(GameClient.CLIENTID);
    }

    @Override
    public void receive(JoinMsg networkMsg) {
        System.out.println("Creating player.");
        Player player = new Player(0,0, 0.06f, networkMsg.name);
        createdPlayers.add(player.id);
        players.put(player.id, player);
        camera.setPlayer(player); // latest player controlls camera
        input.addMoveable(player);
        hasNewPlayer = true;
    }

    @Override
    public GameObject[] getNewlyCreatedObjects() {
        GameObject[] newlyCreatedObjects = new GameObject[createdPlayers.size()];
        for(int i = 0; i < newlyCreatedObjects.length; i++){
            newlyCreatedObjects[i] = players.get(createdPlayers.get(i));
        }
        createdPlayers.clear();
        return newlyCreatedObjects;
    }

    @Override
    public String[] getRemovedObjects() {
        return new String[0]; //TODO
    }
}
