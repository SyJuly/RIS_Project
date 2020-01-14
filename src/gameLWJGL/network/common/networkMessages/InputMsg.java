package gameLWJGL.network.common.networkMessages;

import gameLWJGL.network.common.MsgType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class InputMsg  extends NetworkMsg {

    public int xDirection, yDirection;
    public String clientID;

    public InputMsg(String clientID,int xDirection, int yDirection) {
        super();
        this.msgType = MsgType.Input;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.clientID = clientID;
    }

    public InputMsg(DataInputStream dis) throws IOException {
        deserializeBase(dis);
        xDirection = dis.readInt();
        yDirection = dis.readInt();
        clientID = readString(dis);
    }

    @Override
    public void serialize(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        serializeBase(dos);
        dos.writeInt(xDirection);
        dos.writeInt(yDirection);
        writeString(dos, clientID);
    }
}