package gameLWJGL.network.common.networkMessageHandler;

import gameLWJGL.network.common.IMsgApplicator;
import gameLWJGL.network.common.networkMessages.JoinMsg;

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