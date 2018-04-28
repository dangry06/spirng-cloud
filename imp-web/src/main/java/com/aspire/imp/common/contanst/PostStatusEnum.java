package com.aspire.imp.common.contanst;

public enum PostStatusEnum {
	DELETED(-1),
	DRAFT(0),
	INHERIT(5),
	PUBLISH(10);
	
	private int value;
	PostStatusEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
