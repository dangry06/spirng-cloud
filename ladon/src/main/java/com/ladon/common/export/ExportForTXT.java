package com.ladon.common.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * <p>txt导出类</p>
 * 
 * <p>导出txt格式文档</p>
 * 
 * <p>Copyright: 版权所有 (c) 2010 - 2012</p>
 * <p>Company: 中体彩</p>
 * 
 * @ClassName ExportForTXT
 * @author duanyong
 * @version 1.0
 * 
 */
public class ExportForTXT {

	/**
	 * <p>向txt文件中写入数据</p>
	 * 
	 * @param filePathAndName
	 * @param headerList
	 * @param dataList
	 * @param separator
	 *            分隔符 ,默认以空格为分隔符
	 * @return void
	 * @see
	 */
	public static void write(String filePathAndName, List<String> headerList, List<List<String>> dataList, String separator) {
		BufferedWriter output = null;
		// 默认以空格为分隔符
		String strSeparator = " ";
		if(separator != null && !separator.equals(""))
			strSeparator = separator;
		if(separator != null && separator.equalsIgnoreCase("TAB"))
			strSeparator = "\t";

		// 每列最大宽度
		long[] fieldMaxLength = new long[headerList == null ? 1 : headerList.size()];
		try {
			File file = new File(filePathAndName);
			// 可以多次查询，写入到一个文件中，所以boolean append = true;
			output = new BufferedWriter(new FileWriter(file, true));
			// 计算每列最大宽度
			if(headerList != null) {
				int m = 0;
				for(String header : headerList) {
					fieldMaxLength[m] = header.getBytes().length;
					m++;
				}
			}
			
			// 计算每列最大宽度
			for(int i = 0; i < dataList.size(); i++) {
				int n = 0;
				for(String data : dataList.get(i)) {
					if(data.getBytes().length > fieldMaxLength[n])
						fieldMaxLength[n] = data.getBytes().length;
					n++;
				}
			}

			if(headerList != null) {
				int m = 0;
				for(String header : headerList) {
					// 如果以空格或tab为分隔符，自动调整每列的宽度
					StringBuffer tmp = new StringBuffer();
					if(strSeparator.equals(" ") || strSeparator.equals("\t")) {
						// 计算每列是否达到最大宽度，如果没有加空格补齐
						for(int j = 0; j < (fieldMaxLength[m] - header.getBytes().length); j++) {
							tmp.append(" ");
						}
						header = header + tmp.toString();
					}
					m++;
					output.write(header + ((m < headerList.size()) ? strSeparator : ""));
				}
				output.write("\r\n");
			}
			
			if(dataList.size() > 0) {
				for(int i = 0; i < dataList.size(); i++) {
					int n = 0;
					for(String data : dataList.get(i)) {
						// 如果以空格或tab为分隔符，自动调整每列的宽度
						StringBuffer tmp = new StringBuffer();
						if(strSeparator.equals(" ") || strSeparator.equals("\t")) {
							// 计算每列是否达到最大宽度，如果没有加空格补齐
							for(int j = 0; j < (fieldMaxLength[n] - data.getBytes().length); j++) {
								tmp.append(" ");
							}
							data = data + tmp.toString();
						}
						n++;
						output.write(data + ((n < dataList.get(i).size()) ? strSeparator : ""));
					}
					output.write("\r\n");
				}
			} else {
				output.write("无数据\r\n");
			}

		} catch(Exception e) {
			
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch(IOException e) {
					
				}
			}
		}

	}
}
