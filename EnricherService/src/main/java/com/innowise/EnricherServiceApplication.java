package com.innowise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EnricherServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnricherServiceApplication.class, args);
    }
}
