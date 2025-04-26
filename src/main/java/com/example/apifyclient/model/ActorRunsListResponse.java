package com.example.apifyclient.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorRunsListResponse {
    private DataWrapper data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataWrapper {
        private Integer total;
        private Integer count;
        private Integer offset;
        private Integer limit;
        private Boolean desc;
        private List<ActorRun> items;
    }
}
