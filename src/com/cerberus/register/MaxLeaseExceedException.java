package com.cerberus.register;

public class MaxLeaseExceedException extends Exception {
    public MaxLeaseExceedException(String errorMessage) {
        super(errorMessage);
    }
}
