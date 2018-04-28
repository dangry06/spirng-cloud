package com.aspire.imp.aop.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {
	private final static Logger log = LoggerFactory.getLogger(RequestLogInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ip = request.getRemoteAddr();
		long startTime = System.currentTimeMillis();
		request.setAttribute("requestStartTime", startTime);
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 获取用户token
		Method method = handlerMethod.getMethod();
		System.out.println("用户:" + ip + ",访问目标:" + method.getDeclaringClass().getName() + "." + method.getName());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		long startTime = (Long) request.getAttribute("requestStartTime");
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;

		// log it
		if (executeTime > 1000) {
			log.info("[" + method.getDeclaringClass().getName() + "." + method.getName() + "] 执行耗时 : " + executeTime + "ms");
		} else {
			log.info("[" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "] 执行耗时 : " + executeTime + "ms");
		}
	}

}
