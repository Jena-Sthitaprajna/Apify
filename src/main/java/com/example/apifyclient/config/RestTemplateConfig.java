package com.example.apifyclient.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new CustomResponseErrorHandler())
                .build();
    }

    private static class CustomResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().is4xxClientError() ||
                    response.getStatusCode().is5xxServerError();
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            String responseBody = new BufferedReader(new InputStreamReader(response.getBody()))
                    .lines().collect(Collectors.joining("\n"));

            throw new RuntimeException(
                    "API Error: " + response.getStatusCode() + " - " + responseBody);
        }
    }
}
