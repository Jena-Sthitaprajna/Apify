package com.example.apifyclient.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apifyclient.model.Dataset;
import com.example.apifyclient.model.DatasetCollection;
import com.example.apifyclient.model.DatasetItemsResponse;
import com.example.apifyclient.service.DatasetService;

@RestController
@RequestMapping("/api/datasets")
public class DatasetController {

    private final DatasetService datasetService;

    @Autowired
    public DatasetController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @GetMapping
    public ResponseEntity<DatasetCollection> getDatasets(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false) String desc) {
        return ResponseEntity.ok(datasetService.getDatasets(offset, limit, desc));
    }

    @GetMapping("/{datasetId}")
    public ResponseEntity<Dataset> getDataset(@PathVariable String datasetId) {
        return ResponseEntity.ok(datasetService.getDataset(datasetId));
    }

    @GetMapping("/{datasetId}/items")
    public ResponseEntity<DatasetItemsResponse> getDatasetItems(
            @PathVariable String datasetId,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "false") Boolean clean,
            @RequestParam(required = false) String fields,
            @RequestParam(required = false) String omit,
            @RequestParam(required = false) String unwind,
            @RequestParam(required = false) String desc) {
        return ResponseEntity.ok(datasetService.getDatasetItems(datasetId, offset, limit, clean, fields, omit, unwind, desc));
    }

    @GetMapping("/{datasetId}/items/all")
    public ResponseEntity<List<Map<String, Object>>> getAllDatasetItems(
            @PathVariable String datasetId,
            @RequestParam(required = false, defaultValue = "false") Boolean clean,
            @RequestParam(required = false) String fields,
            @RequestParam(required = false) String omit,
            @RequestParam(required = false) String unwind,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false, defaultValue = "1000") Integer batchSize,
            @RequestParam(required = false, defaultValue = "10000") Integer maxItems) {
        return ResponseEntity.ok(datasetService.getAllDatasetItems(datasetId, clean, fields, omit, unwind, desc, batchSize, maxItems));
    }
}
