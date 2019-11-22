package com.cerberus.models.customer.event;

import com.cerberus.sale.Lease;

public class LeaseEvent extends Event {

    private final Lease lease;

    public LeaseEvent(Lease lease) {
        super();
        this.lease = lease;
    }

    public Lease getLease() {
        return lease;
    }
}
