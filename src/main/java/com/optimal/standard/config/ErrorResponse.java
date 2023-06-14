package com.optimal.standard.config;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    private String message;
    private Map<String, String> validationErrors = new HashMap<>();

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getValidationErrors() {
        return this.validationErrors;
    }

    public void addValidationError(String field, String message) {
        this.validationErrors.put(field, message);
    }
}
