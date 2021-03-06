package gameLWJGL.network.common.networkMessages;

import gameLWJGL.objects.GameObject;
import gameLWJGL.objects.ObjectHandler;
import gameLWJGL.network.common.MsgType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DynamicObjectsMsg extends NetworkMsg {

    private ObjectHandler objectHandler;
    private List<GameObject> updatedObjectList;
    private List<GameObject> allObjectList;
    private List<GameObject> removedObjectList;
    private DataInputStream dis = null;
    private boolean sendAll = false;

    public DynamicObjectsMsg(ObjectHandler objectHandler, boolean sendAll){
        super();
        this.sendAll = sendAll;
        this.msgType = MsgType.DynamicObjects;
        this.objectHandler = objectHandler;
        this.removedObjectList = new ArrayList<>(objectHandler.getRemovedObjects());
        this.allObjectList = new ArrayList<>(objectHandler.getDynamicObjects());
        this.updatedObjectList = new ArrayList<>(objectHandler.getUpdatedObjects());
    }

    public DynamicObjectsMsg(DataInputStream dis){
        this.msgType = MsgType.DynamicObjects;
        this.dis = dis;
    }


    public void deserializeAndApplyData(ObjectHandler objectHandler) throws IOException {
        deserializeBase(dis);
        int updatedObjectsSize = dis.readInt();
        for(int i = 0; i < updatedObjectsSize; i++){
            String id = readString(dis);
            if(id.equals("NULL")){
                continue;
            }
            float x = dis.readFloat();
            float y = dis.readFloat();
            float width = dis.readFloat();
            float height = dis.readFloat();
            int objectTypeCode = dis.readInt();
            int specificsLength = dis.readInt();
            float[] specifics = new float[specificsLength];
            for(int j = 0; j < specificsLength; j++) {
                specifics[j] = dis.readFloat();
            }
            objectHandler.createOrUpdateObject(x,y,width,height,id,objectTypeCode, specifics);
        }
        int removedObjectsSize = dis.readInt();
        for(int i = 0; i < removedObjectsSize; i++){
            String removedObjectId = readString(dis);
            if(removedObjectId.equals("NULL")){
                continue;
            }
            objectHandler.removeObject(removedObjectId);
        }
        objectHandler.acknowledgeEndOfDynamicObjectsMsg();
    }

    @Override
    public void serialize(OutputStream outputStream) throws IOException {
        List<GameObject> updatedObjects = sendAll ? allObjectList : new ArrayList<>(updatedObjectList);
        List<GameObject> removedObjects = sendAll ? new ArrayList<>() : removedObjectList;

        DataOutputStream dos = new DataOutputStream(outputStream);
        serializeBase(dos);
        dos.writeInt(updatedObjects.size());
        for(int i = 0; i < updatedObjects.size(); i++){
            GameObject gameObject = updatedObjects.get(i);
            if(gameObject != null){
                writeString(dos, gameObject.id);
            } else {
                writeString(dos, "NULL");
                continue;
            }
            float[] specifics = gameObject.getSpecifics();
            dos.writeFloat(gameObject.x);
            dos.writeFloat(gameObject.y);
            dos.writeFloat(gameObject.width);
            dos.writeFloat(gameObject.height);
            dos.writeInt(gameObject.objectType.ordinal());
            dos.writeInt(specifics.length);
            for(int j = 0; j < specifics.length; j++) {
                dos.writeFloat(specifics[j]);
            }
        }
        dos.writeInt(removedObjects.size());
        for(int i  = 0; i < removedObjects.size(); i++){
            GameObject gameObject = removedObjects.get(i);
            if(gameObject != null){
                writeString(dos, gameObject.id);
            } else {
                writeString(dos, "NULL");
                continue;
            }
        }
        objectHandler.clearMsgLists();
    }
}
