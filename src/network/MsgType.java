package network;

public enum MsgType {
	Move("6001");

	private final String code;

	MsgType(final String newValue) {
		code = newValue;
	}

	public String getCode() { return code; }
}
