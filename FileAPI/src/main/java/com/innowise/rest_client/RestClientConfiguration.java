package com.innowise.rest_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return restTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return objectMapper;
    }
}
