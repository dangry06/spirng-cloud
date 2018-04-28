/**============================================================
 * 版权： 中体彩 版权所有 (c) 2010 - 2012
 * 文件：com.cslc.ump.dataImport.fileParser.CSVFileParser.java
 * 所含类: CSVFileParser.java
 * 修改记录：
 * 日期                           作者                            内容
 * =============================================================
 * 2011-11-07       ZhanShan       创建文件，实现基本功能
 * ============================================================
 * */
package com.ladon.common.fileparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * </p>
 * 
 * <p>
 * 功能描述:导入CSV文件的解析
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
public class CSVFileParser implements FileParser {
	private static final Logger log = LoggerFactory.getLogger(CSVFileParser.class);
	// 文件
	private File file;
	// 分隔符 默认为逗号
	private String separator = ",";

	private String startLineMarker;

	public CSVFileParser(File file, String startLineMarker) {
		this.file = file;
		this.startLineMarker = startLineMarker;
	}

	/**
	 * 解析
	 */
	public List<String[]> parse() throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		InputStream input = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			input = new FileInputStream(file);
			// 默认使用GBK编码
			reader = new InputStreamReader(input, "GBK");
			br = new BufferedReader(reader);

			String line = null;
			// 循环读文件中每一行数据
			// 是否开始读有效数据
			log.info("开始读取.csv文件");
			boolean marker = false;
			List<String[]> nonMarkerList = new ArrayList<String[]>();
			while((line = br.readLine()) != null) {
				if(line.trim().length() == 0)
					continue;
				// 读取每行数据
				String[] fields = line.split(separator, -1);
				if(fields == null || fields.length == 0)
					continue;
				for(int i = 0; i < fields.length; i++) {
					fields[i] = fields[i].trim();
				}
				if(marker) {
					// 记录导入内容
					list.add(fields);
				} else {
					// 记录未找到读取标识时的数据，以便于没有配置读取标识或者在文件内容中没有找到读取标识时使用
					nonMarkerList.add(fields);
					// 如果配置了读取标识，且在文件中找到了读取标识
					if(startLineMarker != null && startLineMarker.length() > 0 && fields[0].equals(startLineMarker)) {
						// 标记marker为true
						marker = true;
						// 清空未找到读取标识时记录的数据
						nonMarkerList.clear();
						// 从下一行开始读取文件内容
						continue;
					}
				}
			}
			// 如果没有配置读取标识，或者在文件中没有找到读取标识，默认从第一行开始读取文件内容
			if(!marker) {
				list = nonMarkerList;
			}
			log.info("读取.csv文件完毕");
		} catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if(br != null) {
				br.close();
				br = null;
			}
			if(reader != null) {
				reader.close();
				reader = null;
			}
			if(input != null) {
				input.close();
				input = null;
			}
		}
		return list;
	}
}