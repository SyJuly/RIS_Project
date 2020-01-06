package network.networkMessageHandler;

import gameLWJGL.world.ObjectHandler;
import network.IMsgApplicator;
import network.networkMessages.DynamicObjectsMsg;

import java.io.DataInputStream;
import java.io.IOException;

public class DynamicObjectsMsgHandler extends NetworkMsgHandler {

    public DynamicObjectsMsgHandler(IMsgApplicator applicator) {
        super(applicator);
    }

    @Override
    public void handleMsg(DataInputStream dis) throws IOException {
        DynamicObjectsMsg msg = new DynamicObjectsMsg(dis, (ObjectHandler) applicator);
    }
}
