package com.aspire.imp.web.entity;

public class Option {
	private Long id;
	private Long releatedObjId;
	private String releatedObjType;
	private String optionKey;
	private String optionValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getReleatedObjId() {
		return releatedObjId;
	}
	public void setReleatedObjId(Long releatedObjId) {
		this.releatedObjId = releatedObjId;
	}
	public String getReleatedObjType() {
		return releatedObjType;
	}
	public void setReleatedObjType(String releatedObjType) {
		this.releatedObjType = releatedObjType;
	}
	public String getOptionKey() {
		return optionKey;
	}
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
}
