package com.aspire.imp.common.contanst;

public enum UserGenderEnum {
	UNKNOWN(0),
	MALE(1),
	FEMALE(2);

	private int value;
	UserGenderEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
