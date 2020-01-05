package network.networkMessageHandler;

import network.IMsgApplicator;
import network.networkMessages.JoinMsg;

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