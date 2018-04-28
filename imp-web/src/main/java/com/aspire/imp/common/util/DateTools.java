package com.aspire.imp.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>DateTools</p>
 *
 * <p>日期时间处理工具类</p>
 * 
 * @ClassName DateTools
 * @author duanyong
 * @version 1.0
 * 
 */

public class DateTools {
	// *****************************************************************************************************************//
	// ************************ 基本时间处理方法 ********************************************//
	// *****************************************************************************************************************//

	/**
	 * <p>获取当前时间</p>
	 * 
	 * @return
	 * @return Date
	 * @see
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * <p>获取给定时间的前一天</p>
	 * 
	 * @param date
	 * @return
	 * @return Date
	 * @see
	 */
	public static Date getPreviousDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}

	/**
	 * <p>获取给定时间的后一天</p>
	 * 
	 * @param date
	 * @return
	 * @return Date
	 * @see
	 */
	public static Date getNextDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}

	/**
	 * <p>获取当前日期的零点</p>
	 * 
	 * @return
	 * @throws ParseException
	 * @return Date
	 * @see
	 */
	public static Date getStartDateTimeOfCurrent() throws ParseException {
		Date date = new Date();
		return DateTools.getStartDateTimeOfDate(date);
	}

	/**
	 * <p>获取给定时间的的零点</p>
	 * 
	 * @param date
	 * @return
	 * @return Date
	 * @throws ParseException
	 * @see
	 */
	public static Date getStartDateTimeOfDate(Date date) throws ParseException {
		String zeroStrDate = getDateString(date);
		Date zeroDate = parseDate(zeroStrDate);
		return zeroDate;
	}

	/**
	 * <p>获取当前日期的23:59:59</p>
	 * 
	 * @return
	 * @throws ParseException
	 * @return Date
	 * @see
	 */
	public static Date getEndDateTimeOfCurrent() throws ParseException {
		Date date = new Date();
		return DateTools.getEndDateTimeOfDate(date);
	}

	/**
	 * <p>获取给定时间的23:59:59</p>
	 * 
	 * @param date
	 * @return
	 * @return Date
	 * @throws ParseException
	 * @see
	 */
	public static Date getEndDateTimeOfDate(Date date) throws ParseException {
		String endStrDate = getDateString(date);
		return parseDateTime(endStrDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
	}

	// *****************************************************************************************************************//
	// ************************ 格式化时间为字符串 ********************************************//
	// *****************************************************************************************************************//

	/**
	 * <p>格式化时间为'yyyy-MM-dd'格式的字符串</p>
	 * 
	 * @param date
	 * @return
	 * @return String
	 * @see
	 */
	public static String getDateString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * <p>格式化时间为'yyyy-MM-dd HH:mm:ss'格式的字符串</p>
	 * 
	 * @param date
	 * @return
	 * @return String
	 * @see
	 */
	public static String getDateTimeString(Date date) {
		if(date == null) {
			return null;
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		}

	}

	/**
	 * <p>格式化时间为指定格式的字符串</p>
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @return String
	 * @see
	 */
	public static String getDateTimeString(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * <p>格式化时间为'HH:mm:ss'格式的字符串</p>
	 * 
	 * @param date
	 * @return
	 * @return String
	 * @see
	 */
	public static String getTimeHourString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	// *****************************************************************************************************************//
	// ************************ 解析字符串为时间 ********************************************//
	// *****************************************************************************************************************//

	/**
	 * <p>解析字符串为'yyyy-MM-dd'格式的日期</p>
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 * @throws ParseException
	 * @return Date
	 * @see
	 */
	public static Date parseDate(String dateString) throws ParseException {
		return parseDateTime(dateString, "yyyy-MM-dd");
	}

	/**
	 * <p>解析字符串为'yyyy-MM-dd HH:mm:ss'格式的时间</p>
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 * @return Date
	 * @see
	 */
	public static Date parseDateTime(String dateString) throws ParseException {
		return parseDateTime(dateString, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * <p>解析字符串为指定格式的时间</p>
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 * @throws ParseException
	 * @return Date
	 * @see
	 */
	public static Date parseDateTime(String dateString, String format) throws ParseException {
		return parseDateTimeByFormats(dateString, new String[] { format });
	}

	/**
	 * <p>使用固定格式集合解析字符串为的时间</p>
	 * 
	 * @param dateString
	 *            时间字符串，被格式的优先级为："yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd"
	 * @return
	 * @throws ParseException
	 * @return Date
	 * @see
	 */
	public static Date parseDateByAvailableFormat(String dateString) throws ParseException {
		return parseDateTimeByFormats(dateString, new String[] { "yyyy-MM-dd HH:mm:ss" });
	}

	/**
	 * <p>使用指定格式集合解析字符串为时间</p>
	 * 
	 * @param dateString
	 *            时间字符串
	 * @param formats
	 *            格式集合
	 * @return
	 * @throws ParseException
	 * @return Date
	 * @see
	 */
	private static Date parseDateTimeByFormats(String dateString, String[] formats) throws ParseException {
		SimpleDateFormat df = null;
		ParseException ex = null;
		Date date = null;
		for(String format : formats) {
			ex = null;
			try {
				df = new SimpleDateFormat(format);
				date = df.parse(dateString);
			} catch(ParseException e) {
				ex = e;
			}
			if(ex == null) {
				return date;
			}
		}
		throw ex;
	}

	// *****************************************************************************************************************//
	// ************************ 高级时间处理方法 ********************************************//
	// *****************************************************************************************************************//

	/**
	 * Get date offset the input seconds
	 * 
	 * @param date
	 * @param days
	 * @return Date
	 */
	public static Date getDateOffsetBySeconds(Date date, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.SECOND, seconds);
		return calendar.getTime();
	}
	
	/**
	 * Get date offset the input date according to param-days
	 * 
	 * @param date
	 * @param days
	 * @return Date
	 */
	public static Date getDateOffsetByDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	/**
	 * <p>获取给定时间偏移指定月数的时间</p>
	 * 
	 * @param date
	 * @param month
	 *            ：偏移多少月
	 * @return
	 * @return Date
	 * @see
	 */
	public static Date getDateOffsetByMonths(Date date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	/**
	 * <p>获取给定时间偏移指定小时的时间</p>
	 * 
	 * @param date
	 * @param hours
	 * @return
	 * @return Date
	 * @see
	 */
	public static Date getDateOffsetByHours(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}

	/**
	 * 获取给定时间所在周的周一日期【00:00:00】和周日的日期【23:59:59】
	 * 
	 * @param date
	 * @return Date数组，[0]为周一的日期【00:00:00】，[1]周日的日期【23:59:59】
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Date[] getStartAndEndDateTimeOfWeek(Date date) throws ParseException {
		Date[] dates = new Date[2];
		Date firstDateOfWeek, lastDateOfWeek;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		firstDateOfWeek = DateTools.getStartDateTimeOfDate(calendar.getTime());

		// 老外认为周日为一周的第一天，所以这里的周日其实是上一周的周日
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		lastDateOfWeek = DateTools.getEndDateTimeOfDate(calendar.getTime());
		// 偏移7天获得中国人认为的给定时间所在的周日
		lastDateOfWeek = DateTools.getDateOffsetByDays(lastDateOfWeek, 7);

		dates[0] = firstDateOfWeek;
		dates[1] = lastDateOfWeek;
		return dates;
	}

	/**
	 * 获取给定时间所在月的第一天日期【00:00:00】和最后一天的日期【23:59:59】
	 * 
	 * @param date
	 * @return Date数组，[0]为第一天的日期【00:00:00】，[1]最后一天的日期【23:59:59】
	 * @throws ParseException
	 */
	public static Date[] getStartAndEndDateTimeOfMonth(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return DateTools.getStartAndEndDateTimeOfMonth(calendar);
	}

	/**
	 * 获取给定时间所在月的第一天日期【00:00:00】和最后一天的日期【23:59:59】
	 * 
	 * @param calendar
	 * @return Date数组，[0]为第一天的日期【00:00:00】，[1]最后一天的日期【23:59:59】
	 * @throws ParseException
	 */
	public static Date[] getStartAndEndDateTimeOfMonth(Calendar calendar) throws ParseException {
		Date[] dates = new Date[2];
		Date firstDateOfMonth, lastDateOfMonth; // 得到当天是这月的第几天
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		// 减去dayOfMonth,得到第一天的日期，因为Calendar用０代表每月的第一天，所以要减一
		calendar.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
		firstDateOfMonth = DateTools.getStartDateTimeOfDate(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
		lastDateOfMonth = DateTools.getEndDateTimeOfDate(calendar.getTime());
		dates[0] = firstDateOfMonth;
		dates[1] = lastDateOfMonth;
		return dates;
	}

	/**
	 * 获取给定时间所在年的第一天日期【yyyy-01-01 00:00:00】和最后一天的日期【yyyy-12-31 23:59:59】
	 * 
	 * @param date
	 * @return Date数组，[0]为第一天的日期【yyyy-01-01 00:00:00】，[1]最后一天的日期【yyyy-12-31 23:59:59】
	 * @throws ParseException
	 */
	public static Date[] getStartAndEndDateTimeOfYear(Date date) throws ParseException {
		Date[] dates = new Date[2];
		dates[0] = DateTools.getStartDateTimeOfYear(date);
		dates[1] = DateTools.getEndDateTimeOfYear(date);
		return dates;
	}

	/**
	 * <p>获取给定日期所在年的第一天【yyyy-01-01 00:00:00】</p>
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 * @return Date
	 * @see
	 */
	public static Date getStartDateTimeOfYear(Date date) throws ParseException {
		String startDate = DateTools.getDateTimeString(date, "yyyy-01-01 00:00:00");
		return DateTools.parseDateTime(startDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * <p>获取给定日期所在年的最后一天【yyyy-12-31 23:59:59】</p>
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 * @return Date
	 * @see
	 */
	public static Date getEndDateTimeOfYear(Date date) throws ParseException {
		String startDate = DateTools.getDateTimeString(date, "yyyy-12-31 23:59:59");
		return DateTools.parseDateTime(startDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取2个日期间的天数
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int getDaysBetweenStartAndEndDate(Date beginTime, Date endTime) {
		// 相差的天数
		int days = (int) ((endTime.getTime() - beginTime.getTime()) / 86400000);
		return days;
	}

	/**
	 * <p>传入时间,返回 天：小时：分：秒串</p>
	 * 
	 * @param dateFirst
	 * @param dateSecond
	 * @return String
	 * @see
	 */
	public static String getDateSplitString(Date dateFirst, Date dateSecond) {
		if(dateFirst == null) {
			return "";
		}
		StringBuffer strResult = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFirst);
		long millF = cal.getTimeInMillis();
		cal.setTime(dateSecond);
		long millS = cal.getTimeInMillis();

		long secs = (millF - millS) / 1000;
		long day = secs / (24 * 3600);
		long hour = (secs % (24 * 3600)) / 3600;
		long min = ((secs % (24 * 3600)) % 3600) / 60;
		long sec = ((secs % (24 * 3600)) % 3600) % 60;

		strResult.append(day).append(":").append(hour).append(":").append(min).append(":").append(sec);
		return strResult.toString();
	}

	/**
	 * 
	 * <p>根据偏移量偏移时间的字符串</p>
	 * 
	 * @param date
	 * @param offset
	 *            ,格式如 天:小时:分钟:秒
	 * @return
	 * @return String
	 * @throws ParseException
	 * @see
	 */
	public static String getOffsetDateString(String dateStr, String offset) throws ParseException {
		Date dateSrc = DateTools.parseDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
		Date offsetDate = DateTools.getOffsetDate(dateSrc, offset);
		return DateTools.getDateTimeString(offsetDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * <p>根据偏移量偏移时间</p>
	 * 
	 * @param date
	 * @param offset
	 *            ,格式如 天:小时:分钟:秒
	 * @return
	 * @return String
	 * @throws ParseException
	 * @see
	 */
	public static Date getOffsetDate(Date date, String offset) throws ParseException {
		String[] arr = offset.split(":");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(arr[0]));
		cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(arr[1]));
		cal.add(Calendar.MINUTE, Integer.parseInt(arr[2]));
		cal.add(Calendar.SECOND, Integer.parseInt(arr[3]));
		return cal.getTime();
	}

	/**
	 * <p>根据毫秒数返回时间字符串</p>
	 * 
	 * @param milliSecond
	 *            毫秒数
	 * @return String, 时分秒
	 * @throws ParseException
	 * @see
	 */
	public static String milliSecondToStr(Long milliSecond) {
		StringBuffer strResult = new StringBuffer();
		long hour = (milliSecond / 1000) / 3600;
		long min = (milliSecond / 1000) % 3600 / 60;
		long sec = ((milliSecond / 1000) % 3600) % 60;
		if(hour > 0) {
			strResult.append(hour + "时");
		}
		if(min > 0) {
			strResult.append(min + "分");
		}
		if(sec > 0) {
			strResult.append(sec + "秒");
		}
		return strResult.toString();
	}

	/**
	 * date1是否在date2之前
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean beforeDate(Date date1, Date date2) {
		if(date1 == null || date2 == null) {
			return false;
		}
		long result = date1.getTime() - date2.getTime();
		if(result < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据两个时间比较，获取期间的每一天的0:0:0的日期。
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDateListByPeriod(Date beginDate, Date endDate) {
		List<Date> result = null;
		if(!DateTools.beforeDate(endDate, beginDate)) {
			int days = DateTools.getDaysBetweenStartAndEndDate(beginDate, endDate);
			result = new ArrayList<Date>();
			try {
				result.add(DateTools.getStartDateTimeOfDate(beginDate));
				for(int i = 0; i < days; i++) {
					result.add(new Date(DateTools.getStartDateTimeOfDate(beginDate).getTime() + 86400000L * (i + 1)));
				}
			} catch(ParseException e) {
			}
		}
		return result;
	}

	/**
	 * 根据两个时间比较，获取期间的每一天的0:0:0的日期。日期类型为java.sql.Date
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<java.sql.Date> getSQLDateListByPeriod(Date beginDate, Date endDate) {
		List<java.sql.Date> result = null;
		if(!DateTools.beforeDate(endDate, beginDate)) {
			int days = DateTools.getDaysBetweenStartAndEndDate(beginDate, endDate);
			result = new ArrayList<java.sql.Date>();
			try {
				result.add(new java.sql.Date(DateTools.getStartDateTimeOfDate(beginDate).getTime()));
				for(int i = 0; i < days; i++) {
					result.add(new java.sql.Date(DateTools.getStartDateTimeOfDate(beginDate).getTime() + 86400000L * (i + 1)));
				}
			} catch(ParseException e) {
			}
		}
		return result;
	}

	/**
	 * 把一段时间分成三段，整月的一段，剩下两段，如果分不出来，则对应数组位置为null，当月列外，当月月末如果在第二段，则转移到第三段
	 * @param begin
	 * @param end
	 * @param offset 例外的偏移量
	 * @return
	 * @throws ParseException
	 */
	public static Date[] splitEntireMonthSpecialCurrentMonth(Date begin,Date end,int offset) throws ParseException{
		
		Date[] period = splitEntireMonth(begin,end);
		
		//获取偏移后的那个月第一天和最后一天
		Date zeroNow = getStartDateTimeOfCurrent();
		Date watershed = getDateOffsetByDays(zeroNow, -1*offset);//new Date(zeroNow.getTime() - 60*60*24*1000*offset);
		Date[] currentArray = getStartAndEndDateTimeOfMonth(watershed);
		Date currentMonthStart = currentArray[0];
		Date currentMonthEnd = currentArray[1];
		
		//第二段有当月
		if(currentMonthEnd.equals(period[3])){
			//把当月放入第三段
			period[4] = currentMonthStart;
			if(period[5] == null){
				period[5] = currentMonthEnd;
			}
			//第二段移除当月
			if(currentMonthStart.equals(period[2])){
				period[2] = null;
				period[3] = null;
			}else{
				period[3] = new Date(currentMonthStart.getTime()-1000);
			}
		}
		
		
		return period;
	}
	
	/**
	 * 把一段时间分成三段，整月的一段，剩下两段，如果分不出来，则对应数组位置为null
	 * @param beginDate
	 * @param endDate
	 * @return Date[6] 如果beginDate或者endDate为null，则返回null
	 * @throws ParseException 
	 */
	public static Date[] splitEntireMonth(Date begin,Date end) throws ParseException{
		
		Date[] result = new Date[6];
		if(begin==null || end==null){
			return null;
		}
		//获取参数副本
		Date beginDate = new Date(begin.getTime());
		Date endDate = new Date(end.getTime());
		
		Date[] beginArray = getStartAndEndDateTimeOfMonth(beginDate);
		Date beginMonthStart = beginArray[0];
		Date beginMonthEnd = beginArray[1];
		
		Date[] endArray = getStartAndEndDateTimeOfMonth(endDate);
		Date endMonthStart = endArray[0];
		Date endMonthEnd = endArray[1];
		
		//先处理开始时间结束时间就是月初月末的情况
		if(beginDate.equals(beginMonthStart) && endDate.equals(endMonthEnd)){
			//beginDate就是月初、endDate就是月末，则只有整月的第二段
			result[2] = beginDate;
			result[3] = endDate;
		}else if(beginDate.equals(beginMonthStart)){
			//beginDate就是月初
			if(endDate.getTime() > beginMonthEnd.getTime()){
				//不在一个月内，分2、3两段
				result[2] = beginDate;
				result[3] = new Date(endMonthStart.getTime()-1000);
				result[4] = endMonthStart;
				result[5] = endDate;
			}else{//否则只有1段
				result[0] = beginDate;
				result[1] = endDate;
			}
		}else if(endDate.equals(endMonthEnd)){
			//endDate是月末
			if(beginDate.getTime() < endMonthStart.getTime()){
				//不在一个月内，分1、2两段
				result[0] = beginDate;
				result[1] = beginMonthEnd;
				result[2] = new Date(beginMonthEnd.getTime()+1000);
				result[3] = endDate;
			}else{//否则只有1段
				result[0] = beginDate;
				result[1] = endDate;
			}
		}else if(beginMonthStart.equals(endMonthStart) || beginMonthEnd.getTime()+1000==endMonthStart.getTime()){
			//普通情况下，beginDate、endDate在同一个月内或相邻的两个月
			result[0] = beginDate;
			result[1] = endDate;
		}else {
			//普通情况下，beginDate、endDate跨三个月
			result[0] = beginDate;
			result[1] = beginMonthEnd;
			result[2] = new Date(beginMonthEnd.getTime()+1000);
			result[3] = new Date(endMonthStart.getTime()-1000);
			result[4] = endMonthStart;
			result[5] = endDate;
		}
		
		return result;
	}
	
}
