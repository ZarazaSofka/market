package ru.mirea.auth.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}