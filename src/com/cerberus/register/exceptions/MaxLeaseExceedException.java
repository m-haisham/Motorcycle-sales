package com.cerberus.register.exceptions;

public class MaxLeaseExceedException extends Exception {
    public MaxLeaseExceedException(String errorMessage) {
        super(errorMessage);
    }
}
