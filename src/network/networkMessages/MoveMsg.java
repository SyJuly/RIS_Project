package network.networkMessages;

import network.MsgType;

import java.io.IOException;
import java.io.OutputStream;

public class MoveMsg extends NetworkMsg{
	
	public static MsgType MSGTYPE = MsgType.Move;
	
	public float[] position;
	
	MoveMsg(float[] position){
		this.position = position;
	}

	@Override
	public void serialize(OutputStream outputStream) throws IOException {

	}
}
