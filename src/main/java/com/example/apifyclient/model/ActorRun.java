package com.example.apifyclient.model;

import java.time.ZonedDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorRun {
    private String id;
    private String actId;
    private String status;
    private ZonedDateTime startedAt;
    private ZonedDateTime finishedAt;
    private String buildId;
    private String buildNumber;
    private Map<String, Object> meta;
    private String defaultKeyValueStoreId;
    private String defaultDatasetId;
    private String defaultRequestQueueId;
}
