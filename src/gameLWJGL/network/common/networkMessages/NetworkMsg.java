package gameLWJGL.network.common.networkMessages;

import gameLWJGL.network.common.MsgType;

import java.io.*;
import java.util.UUID;

public abstract class NetworkMsg {
	
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
		writeString(dos, id);
		dos.writeLong(createdAt);
	}

	public void deserializeBase(DataInputStream dis) throws IOException {
		id = readString(dis);
		createdAt = dis.readLong();
	}

	public void writeString(DataOutputStream dos, String string) throws IOException {
		dos.writeInt(string.length());
		for(int i = 0; i < string.length(); i++){
			dos.writeChar(string.charAt(i));
		}
	}

	public String readString(DataInputStream dis) throws IOException {
		String string = "";
		int stringLength = dis.readInt();
		for(int i = 0; i < stringLength; i++){
			string += dis.readChar();
		}
		return string;
	}
}
