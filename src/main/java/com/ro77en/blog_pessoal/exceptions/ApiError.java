package com.ro77en.blog_pessoal.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({"timestamp", "status", "message", "fields"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private String timestamp;

    private int status;

    private String message;

    private Map<String, List<String>> fields;

    public ApiError(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.message = message;
        this.status = status.value();
    }

    public ApiError(HttpStatus status, String message, Map<String, List<String>> fields) {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.status = status.value();
        this.message = message;
        this.fields = fields;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getFields() {
        return fields;
    }

    public void setFields(Map<String, List<String>> fields) {
        this.fields = fields;
    }
}
