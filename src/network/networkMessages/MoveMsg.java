package network.networkMessages;

import network.MsgType;

public class MoveMsg extends NetworkMsg{
	
	public static MsgType MSGTYPE = MsgType.Move;
	
	public float[] position;
	
	MoveMsg(float[] position){
		this.position = position;
	}

}
