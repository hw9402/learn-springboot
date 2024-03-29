package com.example.testloginapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// (exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class TestLoginApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestLoginApiApplication.class, args);
    }

}
