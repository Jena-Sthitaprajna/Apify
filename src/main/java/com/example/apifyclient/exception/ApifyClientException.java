package com.example.apifyclient.exception;

public class ApifyClientException extends RuntimeException {

    public ApifyClientException(String message) {
        super(message);
    }

    public ApifyClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
