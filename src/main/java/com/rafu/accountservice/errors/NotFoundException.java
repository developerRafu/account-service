package com.rafu.accountservice.errors;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String msg) {
        super(msg);
    }
}
