package com.aspire.imp.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	public static void addCookie(HttpServletResponse response, String name, String value) {
		addCookie(response, name, value, null);
	}
	
	public static void addCookie(HttpServletResponse response, String name,
			String value, CookieValidityPeriodEnum validityPeriod) {
		Cookie cookie = new Cookie(name, value);
		if (validityPeriod != null) {
			int maxAge = CookieValidityPeriodEnum.getMaxAge(validityPeriod);
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}
	
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					String retValue = cookies[i].getValue();
					return retValue;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * cookie有效期
	 * @author duanyong
	 *
	 */
	public enum CookieValidityPeriodEnum {
		ZERO,
		FIVE_MINUTES,
		TEN_MINUTES,
		TWENTY_MINUTES,
		HALF_AN_HOUR,
		ONE_HOUR,
		ONE_DAY,
		ONE_WEEK,
		ONE_MONTH,
		ONE_YEAR;
		
		/**
		 * 根据不同的类型返回对应的有效期，单位：秒
		 * @param validityPeriod
		 * @return
		 */
		public static int getMaxAge(CookieValidityPeriodEnum validityPeriod) {
			if (validityPeriod == null) {
				validityPeriod = CookieValidityPeriodEnum.HALF_AN_HOUR;
			}
			
			int maxAge = 30 * 60;
			if (validityPeriod.equals(FIVE_MINUTES)) {
				maxAge = 5 * 60;
			} else if (validityPeriod.equals(TEN_MINUTES)) {
				maxAge = 10 * 60;	
			} else if (validityPeriod.equals(TWENTY_MINUTES)) {
				maxAge = 20 * 60;	
			} else if (validityPeriod.equals(HALF_AN_HOUR)) {
				maxAge = 30 * 60;
			} else if (validityPeriod.equals(ONE_HOUR)) {
				maxAge = 60 * 60;
			} else if (validityPeriod.equals(ONE_DAY)) {
				maxAge = 24 * 60;
			} else if (validityPeriod.equals(ONE_WEEK)) {
				maxAge = 7 * 24 * 60;
			} else if (validityPeriod.equals(ONE_MONTH)) {
				maxAge = 30 * 24 * 60;
			} else if (validityPeriod.equals(ONE_YEAR)) {
				maxAge = 365 * 24 * 60;
			} else {
				maxAge = 0;
			}
			return maxAge;
		}
	}
	
}
