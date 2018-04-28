package com.aspire.imp.common.contanst;

public interface RetCode {
	/**
	 * 公共常量，小于100000
	 */
	public static final String SUCCESS = "000000";
	public static final String MISSING_PARAMETER = "000010"; //缺少参数
	
	public static final String SYSTEM_ERROR = "000900";	
	public static final String DB_ERROR = "000910";
	
	/**
	 * 用户相关10开头
	 */
	public static final String LOGIN_ERROR = "100000";	//用户或密码错误
	public static final String USER_NOTEXIST = "100010";
	public static final String USER_EXISTED = "100020";	//用户已经存在
	public static final String USERNAME_NOTEXIST = "100030"; 	//用户名不存在
	public static final String PASSWORD_ERROR = "100040";		//密码错误
	public static final String USER_NOT_LOGIN = "100050";
	
	/**
	 * 帖子相关20开头
	 */
	public static final String POST_NOTEXIST = "200010";
	public static final String POST_NORIGHT = "200020";
	public static final String POST_THUMBSUP_ALREADY = "200030";
	
	public static final String FILE_NOTEXIST = "900000";
	public static final String FILE_OPERATE_ERROR = "900010";	
}
