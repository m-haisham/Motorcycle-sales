package com.cerberus.models.customer.event;

import com.cerberus.models.motorcycle.Motorcycle;
import com.cerberus.models.customer.PaymentType;
import com.cerberus.models.customer.PurchaseType;

import java.time.LocalDateTime;

public class PurchaseEvent extends Event {

    private final Motorcycle motorcycle;
    private final PaymentType paymentType;

    /**
     * default constructor
     * @param _motorcycle motorcycle payment is applied on
     */
    public PurchaseEvent(Motorcycle _motorcycle, PaymentType _paymentType) {
        super();
        motorcycle = _motorcycle;
        paymentType = _paymentType;
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
