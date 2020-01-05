package network.networkMessages;

import network.MsgType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JoinMsg extends NetworkMsg {

    private String name;

    public JoinMsg(String name){
        super();
        this.msgType = MsgType.Join;
        this.name = name;
    }

    public JoinMsg(DataInputStream dis) throws IOException {
        deserializeBase(dis);
        name = readString(dis);
    }

    @Override
    public void serialize(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        serializeBase(dos);
        writeString(dos, name);
    }
}
