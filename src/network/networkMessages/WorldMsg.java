package network.networkMessages;

import network.MsgType;

import java.io.*;

public class WorldMsg<WorldMsg> extends NetworkMsg {

    public float centralX;

    public WorldMsg(float centralX){
        super();
        this.msgType = MsgType.World;
        this.centralX = centralX;
    }

    public WorldMsg(DataInputStream dis) throws IOException {
        deserializeBase(dis);
        centralX = dis.readFloat();
    }

    @Override
    public void serialize(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        serializeBase(dos);
        dos.writeFloat(centralX);
    }
}