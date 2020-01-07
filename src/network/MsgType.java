package network;

public enum MsgType {
	World(6001),
	DynamicObjects(6002),
	Input(6003),
	Join(6000);

	private final int code;

	MsgType(final int newValue) {
		code = newValue;
	}

	public int getCode() { return code; }
}
