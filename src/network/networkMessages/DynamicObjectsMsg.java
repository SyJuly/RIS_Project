package network.networkMessages;

import gameLWJGL.objects.GameObject;
import gameLWJGL.objects.ObjectHandler;
import network.MsgType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DynamicObjectsMsg extends NetworkMsg {

    List<GameObject> objects = new ArrayList<>();
    DataInputStream dis = null;

    public DynamicObjectsMsg(List<GameObject> objects){
        super();
        this.msgType = MsgType.DynamicObjects;
        this.objects = objects;
    }

    public DynamicObjectsMsg(DataInputStream dis){
        this.msgType = MsgType.DynamicObjects;
        this.dis = dis;
    }


    public void deserializeAndApplyData(ObjectHandler objectHandler) throws IOException {
        deserializeBase(dis);
        int objectsSize = dis.readInt();
        for(int i = 0; i < objectsSize; i++){
            float x = dis.readFloat();
            float y = dis.readFloat();
            float width = dis.readFloat();
            float height = dis.readFloat();
            String id = readString(dis);
            int objectTypeCode = dis.readInt();
            objectHandler.createOrUpdateObject(x,y,width,height,id,objectTypeCode);
        }
    }
    @Override
    public void serialize(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        serializeBase(dos);
        dos.writeInt(objects.size());
        for(GameObject gameObject: objects){
            dos.writeFloat(gameObject.x);
            dos.writeFloat(gameObject.y);
            dos.writeFloat(gameObject.width);
            dos.writeFloat(gameObject.height);
            writeString(dos, gameObject.id);
            dos.writeInt(gameObject.objectType.ordinal());
        }

    }
}
