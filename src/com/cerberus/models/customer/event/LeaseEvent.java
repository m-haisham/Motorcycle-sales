package com.cerberus.models.customer.event;

import com.cerberus.sale.Lease;

import java.time.LocalDateTime;

public class LeaseEvent implements Event {

    private final LocalDateTime dateTime;
    private final Lease lease;

    public LeaseEvent(Lease lease) {
        this.dateTime = LocalDateTime.now();
        this.lease = lease;
    }
}
