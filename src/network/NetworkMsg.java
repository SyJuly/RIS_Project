package network;

import java.util.UUID;

public class NetworkMsg {
	
	public String id;
	public long createdAt;
	
	public MsgType msgType;
	
	NetworkMsg() {
		id = UUID.randomUUID().toString();
		createdAt = System.currentTimeMillis();
	}
}
