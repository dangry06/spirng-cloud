package com.aspire.imp.common.contanst;

public enum StatusEnum {
	DELETED(-1),
	DISABLED(0),
	ENABLED(10);
	
	private int value;
	StatusEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
