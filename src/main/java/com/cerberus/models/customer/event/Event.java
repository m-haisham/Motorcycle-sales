package com.cerberus.models.customer.event;

import java.time.LocalDateTime;

public class Event {

    private final LocalDateTime dateTime;

    protected Event() {
        dateTime = LocalDateTime.now();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
