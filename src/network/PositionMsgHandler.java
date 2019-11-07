package network;

public class PositionMsgHandler extends NetworkMsgHandler{

	@Override
	public void handleMsg(NetworkMsg msg) {
		PositionMsg positionMsg = (PositionMsg) msg;
		System.out.println("Received position: " + positionMsg.position);
	}
	

}
