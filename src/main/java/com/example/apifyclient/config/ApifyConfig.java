package com.example.apifyclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "apify")
@Data
public class ApifyConfig {
    private String apiToken;
    private String baseUrl;
}
