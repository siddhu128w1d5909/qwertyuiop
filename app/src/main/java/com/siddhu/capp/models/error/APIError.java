package com.siddhu.capp.models.error;


public class APIError {
    private int status;
    private String message;

    public APIError() {
    }

    public APIError(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int status() {
        return status;
    }

    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return "APIError{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
