package network.networkMessages;

import gameLWJGL.objects.GameObject;
import gameLWJGL.objects.ObjectHandler;
import network.MsgType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DynamicObjectsMsg extends NetworkMsg {

    ObjectHandler objectHandler;
    DataInputStream dis = null;

    public DynamicObjectsMsg(ObjectHandler objectHandler){
        super();
        this.msgType = MsgType.DynamicObjects;
        this.objectHandler = objectHandler;
    }

    public DynamicObjectsMsg(DataInputStream dis){
        this.msgType = MsgType.DynamicObjects;
        this.dis = dis;
    }


    public void deserializeAndApplyData(ObjectHandler objectHandler) throws IOException {
        deserializeBase(dis);
        int updatedObjectsSize = dis.readInt();
        for(int i = 0; i < updatedObjectsSize; i++){
            float x = dis.readFloat();
            float y = dis.readFloat();
            float width = dis.readFloat();
            float height = dis.readFloat();
            String id = readString(dis);
            int objectTypeCode = dis.readInt();
            int specificsLength = dis.readInt();
            Float[] specifics = new Float[specificsLength];
            for(int j = 0; j < specificsLength; j++) {
                specifics[j] = dis.readFloat();
            }
            objectHandler.createOrUpdateObject(x,y,width,height,id,objectTypeCode, specifics);
        }
        int removedObjectsSize = dis.readInt();
        for(int i = 0; i < removedObjectsSize; i++){
            String removedObjectId = readString(dis);
            objectHandler.removeObject(removedObjectId);
        }
        objectHandler.acknowledgeEndOfDynamicObjectsMsg();
    }
    @Override
    public void serialize(OutputStream outputStream) throws IOException {
        List<GameObject> updatedObjects = objectHandler.getUpdatedObjects();
        List<GameObject> removedObjects = objectHandler.getRemovedObjects();

        DataOutputStream dos = new DataOutputStream(outputStream);
        serializeBase(dos);
        dos.writeInt(updatedObjects.size());
        for(int i = 0; i < updatedObjects.size(); i++){
            GameObject gameObject = updatedObjects.get(i);
            Float[] specifics = gameObject.getSpecifics();
            dos.writeFloat(gameObject.x);
            dos.writeFloat(gameObject.y);
            dos.writeFloat(gameObject.width);
            dos.writeFloat(gameObject.height);
            writeString(dos, gameObject.id);
            dos.writeInt(gameObject.objectType.ordinal());
            dos.writeInt(specifics.length);
            for(int j = 0; j < specifics.length; j++) {
                dos.writeFloat(specifics[j]);
            }
        }
        dos.writeInt(removedObjects.size());
        for(int i  = 0; i < removedObjects.size(); i++){
            GameObject gameObject = removedObjects.get(i);
            System.out.println("putting in msg to remove: " + gameObject.id);
            writeString(dos, gameObject.id);
        }
        objectHandler.clearMsgLists();
    }
}
