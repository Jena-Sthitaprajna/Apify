package com.example.apifyclient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "running");
        response.put("message", "Apify Dataset Client is running");
        response.put("documentation", "Use /api/datasets to access the API");

        return ResponseEntity.ok(response);
    }
}
