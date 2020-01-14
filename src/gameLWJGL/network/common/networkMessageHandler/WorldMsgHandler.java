package gameLWJGL.network.common.networkMessageHandler;

import gameLWJGL.network.common.IMsgApplicator;
import gameLWJGL.network.common.networkMessages.WorldMsg;

import java.io.DataInputStream;
import java.io.IOException;

public class WorldMsgHandler extends NetworkMsgHandler<WorldMsg> {

    public WorldMsgHandler(IMsgApplicator<WorldMsg> applicator){
        super(applicator);
    }

    @Override
    public void handleMsg(DataInputStream dis) throws IOException {
        WorldMsg msg = new WorldMsg(dis);
        applicator.receive(msg);
    }
}
