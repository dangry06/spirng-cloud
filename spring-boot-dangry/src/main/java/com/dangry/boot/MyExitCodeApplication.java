package com.dangry.boot;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyExitCodeApplication {

	@Bean
	public ExitCodeGenerator exitCodeGenerator() {
		return new ExitCodeGenerator() {
			@Override
			public int getExitCode() {
				return 42;
			}
		};
	}

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(MyExitCodeApplication.class, args)));
	}

}