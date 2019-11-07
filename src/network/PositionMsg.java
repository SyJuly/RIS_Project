package network;

public class PositionMsg extends NetworkMsg{
	
	public static MsgType MSGTYPE = MsgType.Position;
	
	public float[] position;
	
	PositionMsg(float[] position){
		this.position = position;
	}

}
