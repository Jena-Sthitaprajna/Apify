package com.example.apifyclient.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetCollection {
    private Integer total;
    private Integer offset;
    private Integer count;
    private Integer limit;
    private List<Dataset> items;
}
