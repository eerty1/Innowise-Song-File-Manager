package com.innowise;

import com.innowise.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(RsaKeyProperties.class)
public class FileAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileAPIApplication.class, args);
    }
}
