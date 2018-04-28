package com.ladon.common.utils;

import org.apache.commons.lang.SystemUtils;

/**
 * <p>MultiLineFullToStringStyle</p>
 *
 * <p>扩展ToStringStyle，多行格式</p>
 * 
 * @ClassName MultiLineFullToStringStyle
 * @author duanyong
 * @version 1.0
 *
 */
public class MultiLineFullToStringStyle extends FullToStringStyle {

	private static final long serialVersionUID = 8664471399579548706L;

	public MultiLineFullToStringStyle() {
		super();
		super.setContentStart("[");
		super.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
		super.setFieldSeparatorAtStart(true);
		super.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
	}

}
