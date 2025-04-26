package com.example.apifyclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apifyclient.model.ActorRunsListResponse;
import com.example.apifyclient.service.ActorRunsService;

@RestController
@RequestMapping("/api/actor-runs")
public class ActorRunsController {

    private final ActorRunsService actorRunsService;

    @Autowired
    public ActorRunsController(ActorRunsService actorRunsService) {
        this.actorRunsService = actorRunsService;
    }

    @GetMapping
    public ResponseEntity<ActorRunsListResponse> getUserRunsList(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "1000") Integer limit,
            @RequestParam(required = false, defaultValue = "false") Boolean desc,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(actorRunsService.getUserRunsList(offset, limit, desc, status));
    }

    @GetMapping("/raw")
    public ResponseEntity<String> getRawUserRunsList(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "1000") Integer limit,
            @RequestParam(required = false, defaultValue = "false") Boolean desc,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(actorRunsService.getRawUserRunsList(offset, limit, desc, status));
    }
}
