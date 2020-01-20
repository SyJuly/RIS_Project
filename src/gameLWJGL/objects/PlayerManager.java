package gameLWJGL.objects;

import gameLWJGL.input.Input;
import gameLWJGL.network.client.GameClient;
import gameLWJGL.network.common.IMsgApplicator;
import gameLWJGL.network.common.networkMessages.JoinMsg;
import gameLWJGL.world.WorldUpdates;
import gameLWJGL.world.events.WorldEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerManager implements IObjectHolder, IMsgApplicator<JoinMsg> {

    private Map<String, Player> players = new HashMap<>();
    private ArrayList<String> createdPlayers = new ArrayList<>();

    private boolean createLocalPlayerOnServer = true;

    private Input input;
    private AIManager aiManager;
    private WorldEvents eventsHandler;

    public PlayerManager(Input input, AIManager aiManager, WorldEvents eventsHandler){
        this.input = input; //for server
        this.aiManager = aiManager;
        this.eventsHandler = eventsHandler;
    }

    public PlayerManager(AIManager aiManager){
        this.aiManager = aiManager; // for client
    }

    public void updateOnClient(){
        Set<String> playerKeys = players.keySet();
        for(String playerKey: playerKeys){
            players.get(playerKey).updateOnClient();
        }
    }

    public void createPlayer(float x, float y, String id, float[] specifics){
        System.out.println("Created player instance on client with id: " + id);
        Player player = new Player(x,y,id);
        player.setSpecifics(specifics);
        createdPlayers.add(player.id);
        players.put(player.id, player);
        aiManager.addPlayer(player);
        WorldUpdates.getInstance().addGameObjectToUpdate(player);
    }

    public Map<String, Player> getPlayers() {
        return players;
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
        Player player = new Player(0,0, networkMsg.name, networkMsg.color, eventsHandler);
        aiManager.addPlayer(player);
        createdPlayers.add(player.id);
        players.put(player.id, player);
        input.addMoveable(player);
        WorldUpdates.getInstance().addGameObjectToUpdate(player);
    }

    @Override
    public void removeObject(String id) {
        players.remove(id);
    }
}
