package com.ssafy.jjc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ssafy")
@SpringBootApplication
public class JjcApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjcApplication.class, args);
    }

}
