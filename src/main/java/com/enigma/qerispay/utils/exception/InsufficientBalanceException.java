package com.enigma.qerispay.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
