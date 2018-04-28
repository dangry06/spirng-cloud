/**============================================================
 * 文件：com.ladon.datastatics.DataStaticsHanlder.java
 * 所含类: DataStaticsHanlder.java
 * 修改记录：
 * 日期                           作者                            内容
 * =============================================================
 * 2017年8月7日       duanyong       创建文件，实现基本功能
 * ============================================================*/

package com.ladon.datastatics;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ladon.common.export.ExportForTXT;
import com.ladon.common.fileparse.FileParser;
import com.ladon.common.fileparse.FileParserFactory;
import com.ladon.common.fileparse.FileTypeCode;

/**
 * <p>DataStaticsHanlder</p>
 *
 * <p>类用途详细说明</p>
 * 
 * @ClassName DataStaticsHanlder
 * @author duanyong
 * @version 1.0
 * 
 */

public class DataStaticsHanlder {
	private static final Logger log = LoggerFactory.getLogger(DataStaticsHanlder.class);
	
	private static final String Mobile_File = "D:/data/ladon/config/BiYeJI/mobile.txt";
	private static final String Old_File = "D:/data/ladon/config/BiYeJI/old.txt";
	private static final String New_File = "D:/data/ladon/config/BiYeJI/new.txt";
	
	static Map<String, Integer> countMap = new TreeMap<String, Integer>();
	static {
		countMap.put("2017-7-4",288);
		countMap.put("2017-7-5",333);
		countMap.put("2017-7-6",656);
		countMap.put("2017-7-7",89001);
		countMap.put("2017-7-8",43597);
		countMap.put("2017-7-9",41610);
		countMap.put("2017-7-10",20089);
		countMap.put("2017-7-11",13043);
		countMap.put("2017-7-12",10108);
		countMap.put("2017-7-13",8806);
		countMap.put("2017-7-14",42016);
		countMap.put("2017-7-15",9812);
		countMap.put("2017-7-16",8000);
		countMap.put("2017-7-17",17811);
		countMap.put("2017-7-18",16093);
		countMap.put("2017-7-19",17111);
		countMap.put("2017-7-20",17328);
		countMap.put("2017-7-21",22211);
		countMap.put("2017-7-22",18867);
		countMap.put("2017-7-23",11113);
		countMap.put("2017-7-24",10110);
		countMap.put("2017-7-25",4205);
		countMap.put("2017-7-26",3021);
	}
	
	/**
	 * <p>方法描述</p>
	 * @param args
	 * @return void
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		FileParser parser = FileParserFactory.getFileParserFactory().createFileParser(FileTypeCode.TXT_FILE, new File(Mobile_File), "   ", "");
		List<String[]> mobileArrayList = parser.parse();
		List<String> mobileDataList = new ArrayList<String>();
		for (String[] str : mobileArrayList) {
			String mobile = str[0].trim();
			if (StringUtils.isNotBlank(mobile)) {
				mobileDataList.add(mobile);
			}
		}
		
		FileParser parser1 = FileParserFactory.getFileParserFactory().createFileParser(FileTypeCode.TXT_FILE, new File(Old_File), " ", "");
		List<String[]> oldList = parser1.parse();
		
		log.info("1开始分拆old.txt文件....");
		Map<String, Set<String>> oldMap = new TreeMap<String, Set<String>>();
		for (String[] arr : oldList) {
			String mobile = arr[0].trim();
			String date = arr[1].trim();
			if (StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(date)) {
				Set<String> mobileSet = oldMap.get(date);
				if (mobileSet == null) {
					mobileSet = new HashSet<String>();
				}
				mobileSet.add(mobile);
				oldMap.put(date, mobileSet);
			}
		}
		log.info("1分拆old.txt文件完成...." + oldMap.size());
		
		log.info("2开始计算添加数据量....");
		for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
			String date = entry.getKey();
			Integer count = entry.getValue();
			
			Set<String> mobileSet = oldMap.get(date);
			if (mobileSet == null) {
				mobileSet = new HashSet<String>();
			}
			
			//如果数据有问题，提出的明细量大于给出的统计量，则删除相关明细
			if (mobileSet.size() > count) {
				for (Iterator<String> iterator = mobileSet.iterator(); iterator.hasNext();) {
					if (mobileSet.size() <= count) {
						break;
					}
					String it = iterator.next();
					iterator.remove();
				}
			} else {
				Collections.shuffle(mobileDataList);
				Set<String> mobileDataSet = new HashSet<String>(mobileDataList);
				for (Iterator<String> iterator = mobileDataSet.iterator(); iterator.hasNext();) {
					if (mobileSet.size() >= count) {
						break;
					}
					mobileSet.add(iterator.next());
				}
			}
		}
		log.info("2计算添加数据量完成....");
		
		//循环oldMap转换成list集合
		log.info("3开始组成写入数据....");
		List<List<String>> dataList = new ArrayList<List<String>>();
		for (Map.Entry<String, Set<String>> entry : oldMap.entrySet()) {
			String date = entry.getKey();
			Set<String> mobileSet = entry.getValue();
			
			for (String mobile : mobileSet) {
				List<String> datas = new ArrayList<String>();
				datas.add(date);
				datas.add(mobile);
				datas.add("微信");
				dataList.add(datas);
			}
		}
		log.info("3写入数据组成完成...." + dataList.size());
		
		List<String> headerList = new ArrayList<String>();
		headerList.add("date");
		headerList.add("mobile");
		headerList.add("渠道");
		log.info("4开始向txt文件写入数据....");
		ExportForTXT.write(New_File, headerList, dataList, ",");
		log.info("4向txt文件写入数据完成！！！");
		
	}

}
