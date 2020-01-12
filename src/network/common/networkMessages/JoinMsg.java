package network.common.networkMessages;

import network.common.MsgType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JoinMsg extends NetworkMsg {

    public String name;
    public float[] color;

    public JoinMsg(String name, float[] color){
        super();
        this.msgType = MsgType.Join;
        this.name = name;
        this.color = color;
    }

    public JoinMsg(DataInputStream dis) throws IOException {
        deserializeBase(dis);
        name = readString(dis);
        color = new float[3];
        color[0] = dis.readFloat();
        color[1] = dis.readFloat();
        color[2] = dis.readFloat();
    }

    @Override
    public void serialize(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        serializeBase(dos);
        writeString(dos, name);
        dos.writeFloat(color[0]);
        dos.writeFloat(color[1]);
        dos.writeFloat(color[2]);
    }
}
