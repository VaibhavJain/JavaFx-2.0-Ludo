package org.vaib;

public enum AcessType {

	RISTRICTED(0), ALL(1), GREEN(2), YELLOW(3), RED(4), BLUE(5);
	private int typeCode;

	AcessType(int typeCode) {
		this.typeCode = typeCode;
	}

	public int value() {
		return typeCode;
	}
}