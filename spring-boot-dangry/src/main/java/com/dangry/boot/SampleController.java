package com.dangry.boot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        //SpringApplication.run(SampleController.class, args);
        SpringApplication app = new SpringApplication(SampleController.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
