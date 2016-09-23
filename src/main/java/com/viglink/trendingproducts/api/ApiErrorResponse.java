package com.viglink.trendingproducts.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

public class ApiErrorResponse implements ApiResponse {

    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Map<String, String>> errors;

    public ApiErrorResponse(String message, List<Map<String, String>> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }

    public void setErrors(List<Map<String, String>> errors) {
        this.errors = errors;
    }
}
