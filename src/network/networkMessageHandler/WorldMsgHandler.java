package network.networkMessageHandler;

import gameLWJGL.world.World;
import network.networkMessages.WorldMsg;

import java.io.DataInputStream;
import java.io.IOException;

public class WorldMsgHandler extends NetworkMsgHandler {

    private World world;

    public WorldMsgHandler(World world){
        this.world = world;
    }

    @Override
    public void handleMsg(DataInputStream dis) throws IOException {
        WorldMsg msg = new WorldMsg(dis);
        world.buildWorld(msg.centralX);
    }
}
