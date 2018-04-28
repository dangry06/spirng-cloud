/**============================================================
 * 文件：com.ladon.common.utils.CollectionUtils.java
 * 所含类: CollectionUtils.java
 * 修改记录：
 * 日期                           作者                            内容
 * =============================================================
 * 2017年8月7日       duanyong       创建文件，实现基本功能
 * ============================================================*/

package com.ladon.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>CollectionUtils</p>
 *
 * <p>类用途详细说明</p>
 * 
 * @ClassName CollectionUtils
 * @author duanyong
 * @version 1.0
 * 
 */

public class CollectionUtils {
    /**
     * @function:从list中随机抽取若干不重复元素
     *
     * @param paramList:被抽取list
     * @param count:抽取元素的个数
     * @return:由抽取元素组成的新list
     */
    public static List getRandomList(List paramList, int count){
        if(paramList.size()<count){
            return paramList;
        }
        Random random=new Random();
        List<Integer> tempList=new ArrayList<Integer>();
        List<Object> newList=new ArrayList<Object>();
        int temp=0;
        for(int i=0;i<count;i++){
            temp=random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
            if(!tempList.contains(temp)){
                tempList.add(temp);
                newList.add(paramList.get(temp));
            }
            else{
                i--;
            }   
        }
        return newList;
    }
    
	/**
	 * <p>拆分列表：把srcList平均拆分，每份的数量为singleListSize</p>
	 * 
	 * @param srcList
	 *            ：要拆分的列表，不能为null
	 * @param singleListSize
	 *            ：每份的数量，必须大于0
	 * @return
	 * @return List<List>
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public static List<List> divideList(List srcList, int singleListSize) {
		List<List> partsList = null;
		if(srcList == null || srcList.size() <= 0) {
			return partsList;
		} else {
			partsList = new ArrayList<List>();
			if(srcList.size() > singleListSize) {
				List partList = null;
				int kCnt = (int) Math.ceil(Integer.valueOf(srcList.size()).doubleValue() / Integer.valueOf(singleListSize).doubleValue());
				for(int i = 0; i < kCnt; i++) {
					if(i == kCnt - 1) {
						partList = srcList.subList(i * singleListSize, srcList.size());
					} else {
						partList = srcList.subList(i * singleListSize, (i + 1) * singleListSize);
					}
					partsList.add(partList);
				}
			} else {
				partsList.add(srcList);
			}
		}
		return partsList;
	}
}
