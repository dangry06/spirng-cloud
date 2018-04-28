package com.ladon.common.utils;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>反射完全ToString</p>
 *
 * <p>类用途详细说明</p>
 * 
 * @ClassName FullToStringBuilder
 * @version 1.0
 *
 */
public class FullToStringBuilder {
	private static FullToStringStyle fullToStringStyle = new FullToStringStyle();

	private static FullToStringStyle multiLineFullToStringStyle = new MultiLineFullToStringStyle();

	public static String reflectionToString(Object object) {
		return ToStringBuilder.reflectionToString(object, fullToStringStyle);
	}

	public static String reflectionToMultiLineString(Object object) {
		return ToStringBuilder.reflectionToString(object, multiLineFullToStringStyle);
	}
}
