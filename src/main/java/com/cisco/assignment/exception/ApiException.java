package com.cisco.assignment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
