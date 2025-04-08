package com.bnpp.pb.lynx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:applicationContext.xml"})
@ComponentScan(basePackages = {"com.bnpp.pb.lynx"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
} 