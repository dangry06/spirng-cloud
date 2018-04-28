package com.aspire.imp.aop.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aspire.imp.aop.annotation.Auth;
import com.aspire.imp.common.contanst.RetCode;
import com.aspire.imp.common.exception.BusinessException;
import com.aspire.imp.common.util.CookieUtils;
import com.aspire.imp.web.entity.SysUser;
import com.aspire.imp.web.service.SysUserService;
import com.google.common.base.Strings;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private final static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Autowired
	private SysUserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			return true;
		}

		handlerSession(request);

		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();
		if (clazz.isAnnotationPresent(Auth.class) || method.isAnnotationPresent(Auth.class)) {
			if (request.getSession().getAttribute(request.getRequestedSessionId()) == null) {
				throw new BusinessException(RetCode.USER_NOT_LOGIN, "请先登录");
			} else {
				return true;
			}
		}

		return true;
	}

	private void handlerSession(HttpServletRequest request) {
		String sessionId = request.getRequestedSessionId();
		log.info("sessionId=" + sessionId);
		
		if (!Strings.isNullOrEmpty(sessionId)) {
			//从session中获取登录用户
			SysUser user = (SysUser)request.getSession().getAttribute(sessionId);
			if (user == null) {
				String loginName = CookieUtils.getCookie(request, "identityId");
				if (Strings.isNullOrEmpty(loginName)) {
					return;
				}
				
				user = this.userService.getByLoginName(loginName);
				if (user != null) {
					request.getSession().setAttribute(sessionId, user);
				}
			}
		}

		return;
	}

}
