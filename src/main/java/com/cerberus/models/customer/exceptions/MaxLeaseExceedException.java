package com.cerberus.models.customer.exceptions;

public class MaxLeaseExceedException extends Exception {
    public MaxLeaseExceedException(String errorMessage) {
        super(errorMessage);
    }
}
