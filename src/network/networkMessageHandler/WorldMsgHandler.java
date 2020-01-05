package network.networkMessageHandler;

import network.IMsgRecipient;
import network.networkMessages.WorldMsg;

import java.io.DataInputStream;
import java.io.IOException;

public class WorldMsgHandler extends NetworkMsgHandler<WorldMsg> {

    public WorldMsgHandler(IMsgRecipient<WorldMsg> recipient){
        super(recipient);
    }

    @Override
    public void handleMsg(DataInputStream dis) throws IOException {
        WorldMsg msg = new WorldMsg(dis);
        recipient.receive(msg);
        //world.buildWorld(msg.centralX);
    }
}
