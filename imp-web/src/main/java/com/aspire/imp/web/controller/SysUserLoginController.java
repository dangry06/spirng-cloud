package com.aspire.imp.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.imp.common.contanst.ApiResult;
import com.aspire.imp.common.contanst.RetCode;
import com.aspire.imp.common.util.CookieUtils;
import com.aspire.imp.web.entity.SysUser;
import com.aspire.imp.web.service.SysUserService;
import com.google.common.base.Strings;

@Controller
public class SysUserLoginController {
	@Autowired
	private SysUserService userService;
	
	@RequestMapping("/rest/login")
	@ResponseBody
	public ApiResult login(
			@RequestParam String loginName, 
			@RequestParam String loginPass,
			HttpServletRequest request, HttpServletResponse response) {
		
		SysUser user = this.userService.login(loginName, loginPass);
		if (user == null) {
			return new ApiResult(RetCode.LOGIN_ERROR, "用户名或密码错误");
		}
		request.getSession().setAttribute(request.getRequestedSessionId(), user);
		CookieUtils.addCookie(response, "identityId", user.getLoginName());
		
		return new ApiResult(user);
	}
	
	@RequestMapping("/rest/logout")
	@ResponseBody
	public ApiResult logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(request.getRequestedSessionId(), null);
		CookieUtils.addCookie(response, "identityId", null, CookieUtils.CookieValidityPeriodEnum.ZERO);
		
		return new ApiResult(RetCode.SUCCESS, "退出登录成功");
	}
	
	@RequestMapping("/rest/isLogin")
	@ResponseBody
	public ApiResult isLogin(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = request.getRequestedSessionId();
		if (Strings.isNullOrEmpty(sessionId)) {
			return new ApiResult(RetCode.USER_NOT_LOGIN, "未登录");
		}
		
		//从session中获取登录用户
		SysUser user = (SysUser)request.getSession().getAttribute(sessionId);
		if (user != null) {
			return new ApiResult(user);
		} else {
			String loginName = CookieUtils.getCookie(request, "identityId");
			if (Strings.isNullOrEmpty(loginName)) {
				return new ApiResult(RetCode.USER_NOT_LOGIN, "未登录");
			}
			
			user = this.userService.getByLoginName(loginName);
			if (user != null) {
				request.getSession().setAttribute(sessionId, user);
				return new ApiResult(user);
			} else {
				return new ApiResult(RetCode.USER_NOT_LOGIN, "未登录");
			}
		}
	}
}
