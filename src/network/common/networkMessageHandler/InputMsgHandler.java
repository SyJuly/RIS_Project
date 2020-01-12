package network.common.networkMessageHandler;

import network.common.IMsgApplicator;
import network.common.networkMessages.InputMsg;

import java.io.DataInputStream;
import java.io.IOException;

public class InputMsgHandler extends NetworkMsgHandler<InputMsg> {

    public InputMsgHandler(IMsgApplicator<InputMsg> applicator){
        super(applicator);
    }

    @Override
    public void handleMsg(DataInputStream dis) throws IOException {
        InputMsg msg = new InputMsg(dis);
        applicator.receive(msg);
    }
}
