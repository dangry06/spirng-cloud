package com.aspire.imp.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>BusinessException</p>
 *
 * <p>业务处理异常类</p>
 * 
 * @ClassName BusinessException
 * @author duanyong
 * @version 1.0
 *
 */
public class BusinessException extends RuntimeException {
	private final static Logger log = LoggerFactory.getLogger(BusinessException.class);
	private static final long serialVersionUID = -8489650434297798389L;

	private String code;
	private String description;

	public BusinessException(String code, String description) {
		this(code, description, null);
		log.error(description);
	}

	public BusinessException(String code, String description, Throwable cause) {
		super(description, cause);
		this.code = code;
		this.description = description;
		log.error(description, cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
