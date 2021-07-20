package com.cisco.assignment.exception;

public class ApiRequestException extends RuntimeException {

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public ApiRequestException(String message) {
        super(message);
    }

}
