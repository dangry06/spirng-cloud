package com.dangry.boot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class MyCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("<<<<<<<<<<<<这个是测试MyCommandLineRunner接口>>>>>>>>>>>>>>");
	}

}
