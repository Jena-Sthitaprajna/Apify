package com.example.apifyclient.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.apifyclient.config.ApifyConfig;
import com.example.apifyclient.exception.ApifyClientException;
import com.example.apifyclient.model.Dataset;
import com.example.apifyclient.model.DatasetCollection;
import com.example.apifyclient.model.DatasetItemsResponse;

@Service
public class DatasetService {

    private final RestTemplate restTemplate;
    private final ApifyConfig apifyConfig;

    @Autowired
    public DatasetService(RestTemplate restTemplate, ApifyConfig apifyConfig) {
        this.restTemplate = restTemplate;
        this.apifyConfig = apifyConfig;
    }

    public DatasetCollection getDatasets(Integer offset, Integer limit, String desc) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apifyConfig.getBaseUrl() + "/datasets")
                .queryParam("offset", offset)
                .queryParam("limit", limit);

        if (desc != null) {
            builder.queryParam("desc", desc);
        }


            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apifyConfig.getApiToken());


        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<DatasetCollection> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    DatasetCollection.class);
            return response.getBody();
        } catch (Exception e) {
            throw new ApifyClientException("Failed to fetch datasets", e);
        }
    }

    public Dataset getDataset(String datasetId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apifyConfig.getApiToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Dataset> response = restTemplate.exchange(
                    apifyConfig.getBaseUrl() + "/datasets/" + datasetId,
                    HttpMethod.GET,
                    entity,
                    Dataset.class);
            return response.getBody();
        } catch (Exception e) {
            throw new ApifyClientException("Failed to fetch dataset with ID: " + datasetId, e);
        }
    }

    @SuppressWarnings("unchecked")
    public DatasetItemsResponse getDatasetItems(String datasetId, Integer offset, Integer limit, Boolean clean,
                                                String fields, String omit, String unwind, String desc) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apifyConfig.getBaseUrl() + "/datasets/" + datasetId + "/items")
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .queryParam("clean", clean);

        if (fields != null) {
            builder.queryParam("fields", fields);
        }
        if (omit != null) {
            builder.queryParam("omit", omit);
        }
        if (unwind != null) {
            builder.queryParam("unwind", unwind);
        }
        if (desc != null) {
            builder.queryParam("desc", desc);
        }

        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        try {
            // The API returns a direct array of items, not wrapped in an object
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    new org.springframework.core.ParameterizedTypeReference<List<Map<String, Object>>>() {});

            DatasetItemsResponse result = new DatasetItemsResponse();
            result.setItems(response.getBody());
            result.setCount(result.getItems() != null ? result.getItems().size() : 0);
            result.setOffset(offset);
            result.setLimit(limit);

            return result;
        } catch (Exception e) {
            throw new ApifyClientException("Failed to fetch items for dataset with ID: " + datasetId, e);
        }
    }


    public List<Map<String, Object>> getAllDatasetItems(String datasetId, Boolean clean, String fields,
                                                        String omit, String unwind, String desc, Integer batchSize, Integer maxItems) {
        List<Map<String, Object>> allItems = new ArrayList<>();
        int offset = 0;

        while (true) {
            DatasetItemsResponse response = getDatasetItems(datasetId, offset, batchSize, clean, fields, omit, unwind, desc);

            if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
                break;
            }

            allItems.addAll(response.getItems());

            if (response.getItems().size() < batchSize || allItems.size() >= maxItems) {
                break;
            }

            offset += batchSize;

            // Safety check to prevent infinite loops
            if (offset > maxItems) {
                break;
            }
        }

        // Respect maxItems limit
        if (allItems.size() > maxItems) {
            return allItems.subList(0, maxItems);
        }

        return allItems;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apifyConfig.getApiToken());
        return headers;
    }
}
