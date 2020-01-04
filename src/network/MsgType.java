package network;

public enum MsgType {
	World(6000);

	private final int code;

	MsgType(final int newValue) {
		code = newValue;
	}

	public int getCode() { return code; }
}
