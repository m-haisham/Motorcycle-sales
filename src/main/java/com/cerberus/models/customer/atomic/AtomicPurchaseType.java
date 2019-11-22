package com.cerberus.models.customer.atomic;

import com.cerberus.models.customer.PurchaseType;

public class AtomicPurchaseType {

    private PurchaseType type;

    public AtomicPurchaseType(PurchaseType type) {
        this.type = type;
    }

    public PurchaseType get() {
        return type;
    }

    public void set(PurchaseType type) {
        this.type = type;
    }
}
