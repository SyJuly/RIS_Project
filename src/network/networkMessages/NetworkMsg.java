package network.networkMessages;

import network.MsgType;

import java.io.*;
import java.util.UUID;

public abstract class NetworkMsg<T> {
	
	public String id;
	public long createdAt;
	
	public MsgType msgType;
	
	public NetworkMsg() {
		id = UUID.randomUUID().toString();
		createdAt = System.currentTimeMillis();
	}

	public abstract void serialize(OutputStream outputStream) throws IOException;

	public void serializeBase(DataOutputStream dos) throws IOException {
		dos.writeInt(msgType.getCode());
		dos.writeInt(id.length());
		for(int i = 0; i < id.length(); i++){
			dos.writeChar(id.charAt(i));
		}
		dos.writeLong(createdAt);
	}

	public void deserializeBase(DataInputStream dis) throws IOException {
		int idLength = dis.readInt();
		for(int i = 0; i < idLength; i++){
			id += dis.readChar();
		}
		createdAt = dis.readLong();
	}
}
