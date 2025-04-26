package com.example.apifyclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApifyClientException.class)
    public ResponseEntity<Map<String, String>> handleApifyClientException(ApifyClientException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Apify Client Error");
        errorResponse.put("message", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<Map<String, String>> handleMismatchedInputException(MismatchedInputException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Deserialization Error");
        errorResponse.put("message", "Failed to deserialize response from Apify API: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "JSON Parsing Error");
        errorResponse.put("message", "Failed to parse response from Apify API: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
