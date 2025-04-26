package com.example.apifyclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.apifyclient.config.ApifyConfig;
import com.example.apifyclient.exception.ApifyClientException;
import com.example.apifyclient.model.ActorRunsListResponse;

import java.util.Collections;

@Service
public class ActorRunsService {

    private final RestTemplate restTemplate;
    private final ApifyConfig apifyConfig;

    @Autowired
    public ActorRunsService(RestTemplate restTemplate, ApifyConfig apifyConfig) {
        this.restTemplate = restTemplate;
        this.apifyConfig = apifyConfig;
    }

    public ActorRunsListResponse getUserRunsList(Integer offset, Integer limit, Boolean desc, String status) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apifyConfig.getBaseUrl() + "/actor-runs")
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .queryParam("desc", desc);

        if (status != null && !status.isEmpty()) {
            builder.queryParam("status", status);
        }

        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        try {
            ResponseEntity<ActorRunsListResponse> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    ActorRunsListResponse.class);
            return response.getBody();
        } catch (Exception e) {
            throw new ApifyClientException("Failed to fetch user runs list", e);
        }
    }

    public String getRawUserRunsList(Integer offset, Integer limit, Boolean desc, String status) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apifyConfig.getBaseUrl() + "/actor-runs")
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .queryParam("desc", desc);

        if (status != null && !status.isEmpty()) {
            builder.queryParam("status", status);
        }

        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new ApifyClientException("Failed to fetch raw user runs list", e);
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apifyConfig.getApiToken());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
