package network.common.networkMessageHandler;

import network.common.IMsgApplicator;
import network.common.networkMessages.JoinMsg;

import java.io.DataInputStream;
import java.io.IOException;

public class JoinMsgHandler extends NetworkMsgHandler {

    public JoinMsgHandler(IMsgApplicator<JoinMsg> applicator) {
        super(applicator);
    }

    @Override
    public void handleMsg(DataInputStream dis) throws IOException {
        JoinMsg msg = new JoinMsg(dis);
        applicator.receive(msg);
    }
}