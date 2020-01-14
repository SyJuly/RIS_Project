package gameLWJGL.objects;

import gameLWJGL.input.Input;
import gameLWJGL.world.Camera;
import network.common.IMsgApplicator;
import network.client.GameClient;
import network.common.networkMessages.JoinMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements IObjectHolder, IMsgApplicator<JoinMsg> {

    private Map<String, Player> players = new HashMap<>();
    private ArrayList<String> createdPlayers = new ArrayList<>();

    private boolean createLocalPlayerOnServer = true;

    private Input input;
    private Camera camera;
    private AIManager aiManager;

    public PlayerManager(Input input, Camera camera, AIManager aiManager){
        this.input = input; //for server
        this.camera = camera;
        this.aiManager = aiManager;
    }

    public PlayerManager(Camera camera, AIManager aiManager){
        this.camera = camera;
        this.aiManager = aiManager; // for client
    }

    public void createPlayer(float x, float y, String id, float[] specifics){
        System.out.println("Created player instance on client with id: " + id);
        Player player = new Player(x,y,id);
        player.setSpecifics(specifics);
        createdPlayers.add(player.id);
        players.put(player.id, player);
        aiManager.addPlayer(player);
        if(id.equals(GameClient.CLIENTID)){
            camera.setPlayer(player);
        }
    }

    @Override
    public boolean shouldSendMessage() {
        return createLocalPlayerOnServer;
    }

    @Override
    public JoinMsg getMessage() {
        createLocalPlayerOnServer = false;
        return new JoinMsg(GameClient.CLIENTID, GameClient.COLOR);
    }

    @Override
    public JoinMsg getStartMessage() {
        return null;
    }

    @Override
    public void receive(JoinMsg networkMsg) {
        System.out.println("Creating player: " + networkMsg.name);
        Player player = new Player(0,0, networkMsg.name, networkMsg.color);
        aiManager.addPlayer(player);
        createdPlayers.add(player.id);
        players.put(player.id, player);
        camera.setPlayer(player); // latest player controlls camera
        input.addMoveable(player);
    }

    @Override
    public GameObject[] getNewlyCreatedObjects() {
        GameObject[] newlyCreatedObjects = new GameObject[createdPlayers.size()];
        for (int i = 0; i < newlyCreatedObjects.length; i++) {
            newlyCreatedObjects[i] = players.get(createdPlayers.get(i));
        }
        createdPlayers.clear();
        return newlyCreatedObjects;
    }

    @Override
    public String[] getRemovedObjects() {
        return new String[0]; //TODO
    }

    @Override
    public void removeObject(String id) {
        players.remove(id);
    }
}
