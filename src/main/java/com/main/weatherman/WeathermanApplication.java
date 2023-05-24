package com.main.weatherman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeathermanApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeathermanApplication.class, args);
    }
}
