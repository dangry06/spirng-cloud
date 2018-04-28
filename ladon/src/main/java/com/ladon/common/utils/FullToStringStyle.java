package com.ladon.common.utils;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <p>FullToStringStyle</p>
 *
 * <p>扩展ToStringStyle，单行格式</p>
 * 
 * @ClassName FullToStringStyle
 * @version 1.0
 *
 */
public class FullToStringStyle extends ToStringStyle {

	private static final long serialVersionUID = 1821981889132615921L;

	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
		if (!Object.class.isAssignableFrom(value.getClass())) {
			super.appendDetail(buffer, fieldName, value);
		} else if (value instanceof String) {
			super.appendDetail(buffer, fieldName, (String) value);
		} else if (value instanceof Boolean) {
			this.appendDetail(buffer, fieldName, (Boolean) value);
		} else if (value instanceof Byte) {
			this.appendDetail(buffer, fieldName, (Byte) value);
		} else if (value instanceof Character) {
			this.appendDetail(buffer, fieldName, (Character) value);
		} else if (value instanceof Double) {
			this.appendDetail(buffer, fieldName, (Double) value);
		} else if (value instanceof Float) {
			this.appendDetail(buffer, fieldName, (Float) value);
		} else if (value instanceof Integer) {
			this.appendDetail(buffer, fieldName, (Integer) value);
		} else if (value instanceof Long) {
			this.appendDetail(buffer, fieldName, (Long) value);
		} else if (value instanceof Short) {
			this.appendDetail(buffer, fieldName, (Short) value);
		} else {
			super.appendDetail(buffer, fieldName, ToStringBuilder.reflectionToString(value, this));
		}
	}

	protected void appendDetail(StringBuffer buffer, String fieldName, Boolean value) {
		super.append(buffer, fieldName, value.booleanValue());
	}

	protected void appendDetail(StringBuffer buffer, String fieldName, Byte value) {
		super.appendDetail(buffer, fieldName, value.byteValue());
	}

	protected void appendDetail(StringBuffer buffer, String fieldName, Character value) {
		super.appendDetail(buffer, fieldName, value.charValue());
	}

	protected void appendDetail(StringBuffer buffer, String fieldName, Double value) {
		super.appendDetail(buffer, fieldName, value.doubleValue());
	}

	protected void appendDetail(StringBuffer buffer, String fieldName, Float value) {
		super.appendDetail(buffer, fieldName, value.floatValue());
	}

	protected void appendDetail(StringBuffer buffer, String fieldName, Integer value) {
		super.appendDetail(buffer, fieldName, value.intValue());
	}

	protected void appendDetail(StringBuffer buffer, String fieldName, Long value) {
		super.appendDetail(buffer, fieldName, value.longValue());
	}

	protected void appendDetail(StringBuffer buffer, String fieldName, Short value) {
		super.appendDetail(buffer, fieldName, value.shortValue());
	}
}