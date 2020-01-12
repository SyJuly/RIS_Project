package network.common.networkMessageHandler;

import network.common.IMsgApplicator;
import network.common.networkMessages.DynamicObjectsMsg;

import java.io.DataInputStream;
import java.io.IOException;

public class DynamicObjectsMsgHandler extends NetworkMsgHandler<DynamicObjectsMsg> {

    public DynamicObjectsMsgHandler(IMsgApplicator applicator) {
        super(applicator);
    }

    @Override
    public void handleMsg(DataInputStream dis) throws IOException {
        DynamicObjectsMsg msg = new DynamicObjectsMsg(dis);
        applicator.receive(msg);
    }
}
