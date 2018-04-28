package com.aspire.imp.aop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.aspire.imp.aop.interceptor.LoginInterceptor;
import com.aspire.imp.aop.interceptor.RequestLogInterceptor;

@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {
	@Autowired
    private LoginInterceptor loginInterceptor;

	@Autowired
    private RequestLogInterceptor requestLogInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor);
		registry.addInterceptor(requestLogInterceptor);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 可以直接使用addResourceLocations 指定磁盘绝对路径，同样可以配置多个位置，注意路径写法需要加上file:
		//registry.addResourceHandler("/images/**").addResourceLocations("file:/data/imp/content/");
	}

	
}
