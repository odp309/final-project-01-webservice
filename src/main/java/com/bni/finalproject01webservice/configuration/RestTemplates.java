package com.bni.finalproject01webservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplates {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
