package com.steve.callofthebacklog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateEntityException extends ResponseStatusException {
    public DuplicateEntityException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}