package network.networkMessages;

import java.io.*;

public class WorldMsg<WorldMsg> extends NetworkMsg {

    public float centralX;

    public WorldMsg(float centralX){
        super();
        this.centralX = centralX;
    }

    public WorldMsg(InputStream inputStream) throws IOException {
        DataInputStream dis = new DataInputStream(inputStream);
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