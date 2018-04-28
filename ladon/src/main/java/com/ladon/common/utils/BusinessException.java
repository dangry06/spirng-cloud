package com.ladon.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>BusinessException</p>
 *
 * <p>业务处理异常类</p>
 * 
 * @ClassName BusinessException
 * @version 1.0
 *
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -8489650434297798389L;
	private static final Logger log = LoggerFactory.getLogger(BusinessException.class);

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

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
