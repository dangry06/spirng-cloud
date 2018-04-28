/**============================================================
 * 版权： 中体彩 版权所有 (c) 2010 - 2012
 * 文件：com.cslc.ump.dataImport.fileParser.FileParserFactory.java
 * 所含类: FileParserFactory.java
 * 修改记录：
 * 日期                           作者                            内容
 * =============================================================
 * 2011-11-07       ZhanShan       创建文件，实现基本功能
 * ============================================================
 * */
package com.ladon.common.fileparse;

import java.io.File;

/**
 * <p>
 * </p>
 * 
 * <p>
 * 功能描述:导入文件解析工厂
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
public class FileParserFactory {

	private static FileParserFactory factory;

	private FileParserFactory() {

	}

	public static synchronized FileParserFactory getFileParserFactory() {
		if(factory == null) {
			factory = new FileParserFactory();
		}
		return factory;
	}

	/**
	 * 通过文件类型创建解析器
	 * 
	 * @param fileType
	 *            文件类型
	 * @param file
	 *            文件流
	 * @param separator
	 *            分隔符
	 * @param startLineMarker
	 *            起始行数标识，出现此标识后，下一行数据为有效的数据
	 * @return FileParser
	 */
	public FileParser createFileParser(String fileType, File file, String separator, String startLineMarker) {
		if(fileType.equals(FileTypeCode.TXT_FILE)) {
			return new TXTFileParser(file, separator, startLineMarker);
		} else if(fileType.equals(FileTypeCode.CSV_FILE)) {
			return new CSVFileParser(file, startLineMarker);
		} else if(fileType.equals(FileTypeCode.EXCEL_FILE) || fileType.equals(FileTypeCode.EXCEL_FILE_2007)) {
			//return new ExcelFileParser(file, startLineMarker);
			return null;
		} else {
			return null;
		}
	}

}
