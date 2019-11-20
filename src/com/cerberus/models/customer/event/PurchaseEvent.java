package com.cerberus.models.customer.event;

import com.cerberus.models.motorcycle.Motorcycle;
import com.cerberus.models.customer.PaymentType;
import com.cerberus.models.customer.PurchaseType;

import java.time.LocalDateTime;

public class PurchaseEvent implements Event {

    private final LocalDateTime dateTime;
    private final Motorcycle motorcycle;
    private final PaymentType paymentType;

    /**
     * default constructor
     * @param _motorcycle motorcycle payment is applied on
     */
    public PurchaseEvent(Motorcycle _motorcycle, PaymentType _paymentType) {
        motorcycle = _motorcycle;
        paymentType = _paymentType;

        dateTime = LocalDateTime.now();

    }

    /**
     * @return date and time of payment
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * @return motorcycle payment is applied on
     */
    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    /**
     * @return payment type
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }
}
