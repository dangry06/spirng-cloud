/**============================================================
 * 版权： 中体彩 版权所有 (c) 2010 - 2012
 * 文件：com.cslc.ump.dataImport.fileParser.FileParaser.java
 * 所含类: FileParaser.java
 * 修改记录：
 * 日期                           作者                            内容
 * =============================================================
 * 2011-11-07       ZhanShan       创建文件，实现基本功能
 * ============================================================
 * */
package com.ladon.common.fileparse;

import java.util.List;

/**
 * <p>
 * </p>
 * 
 * <p>
 * 功能描述:导入文件解析接口
 * </p>
 * 
 * <p>
 * Copyright: 版权所有 (c) 2010 - 2012
 * </p>
 * <p>
 * Company: 中体彩
 * </p>
 * 
 * @author ZhangShan
 * @version 1.0
 * 
 */
public interface FileParser {

	/**
	 * 接口方法
	 * 
	 * @return List<String[]>
	 * @throws Exception
	 */
	public List<String[]> parse() throws Exception;
}
