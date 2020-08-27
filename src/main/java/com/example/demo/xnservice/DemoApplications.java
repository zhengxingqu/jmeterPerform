package com.example.demo.xnservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.xnservice.mapper")
public class DemoApplications {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplications.class, args);
    }
}
