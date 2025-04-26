package com.example.apifyclient.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dataset {
    private String id;
    private String name;
    private Integer itemCount;
    private Long createdAt;
    private Long modifiedAt;
    private Long accessedAt;
    private Map<String, Object> stats;
}
