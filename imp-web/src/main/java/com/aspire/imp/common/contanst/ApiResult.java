package com.aspire.imp.common.contanst;

import java.io.Serializable;

public class ApiResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String retCode;
	private String retMsg;
	private Object data;

	public ApiResult(String retCode, String retMsg) {
		this(retCode, retMsg, null);
	}

	public ApiResult(Object data) {
		this(RetCode.SUCCESS, "", data);
	}

	public ApiResult(String retCode, String retMsg, Object data) {
		this.retCode = retCode;
		this.retMsg = retMsg;
		this.data = data;
	}
    
    public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
