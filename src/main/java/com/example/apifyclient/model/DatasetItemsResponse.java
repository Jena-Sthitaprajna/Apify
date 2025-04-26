package com.example.apifyclient.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetItemsResponse {
    private List<Map<String, Object>> items = new ArrayList<>();

    // These fields are not in the direct API response but we'll keep them
    // for compatibility with our service layer
    private Integer total;
    private Integer offset;
    private Integer count;
    private Integer limit;
}

