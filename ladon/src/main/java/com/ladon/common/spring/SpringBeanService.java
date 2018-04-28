package com.ladon.common.spring;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

@SuppressWarnings("unchecked")
public class SpringBeanService implements Serializable {

	private static final long serialVersionUID = -2228376078979553838L;
	private static ApplicationContext context;

	public static <T> T getBean(Class<T> clazz, String beanName) {
		ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		if(context == null) {
			return (T) SpringBeanService.context.getBean(beanName);
		}
		return (T) context.getBean(beanName);
	}

	/**
	 * 
	 * <p>单元测试时使用此方法注入ApplicationContext,解决WEB容器不启动无法获取Bean问题</p>
	 * 
	 * @param applicationContext
	 * @see
	 */
	public static void setApplicationContext(ApplicationContext applicationContext) {
		context = applicationContext;
	}
}
