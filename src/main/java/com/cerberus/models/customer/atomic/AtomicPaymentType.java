package com.cerberus.models.customer.atomic;

import com.cerberus.models.customer.PaymentType;

public class AtomicPaymentType {
    private PaymentType type;

    public AtomicPaymentType(PaymentType type) {
        this.type = type;
    }

    public PaymentType get() {
        return type;
    }

    public void set(PaymentType type) {
        this.type = type;
    }
}
