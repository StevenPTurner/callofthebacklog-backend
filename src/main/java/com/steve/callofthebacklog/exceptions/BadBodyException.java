package com.steve.callofthebacklog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadBodyException extends ResponseStatusException {
    public BadBodyException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
