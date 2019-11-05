package com.cerberus.register.event;

import com.cerberus.models.motorcycle.Motorcycle;
import com.cerberus.register.PaymentType;
import com.cerberus.register.PurchaseType;

import java.time.LocalDateTime;

public class PaymentEvent implements Event {

    private final LocalDateTime dateTime;
    private final Motorcycle motorcycle;
    private final PurchaseType purchaseType; //change to purchase type
    private final PaymentType paymentType;

    /**
     * default constructor
     * @param _motorcycle motorcycle payment is applied on
     * @param _type type of payment
     */
    public PaymentEvent(Motorcycle _motorcycle, PurchaseType _type, PaymentType _paymentType) {
        motorcycle = _motorcycle;
        purchaseType = _type;
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
     * @return type of purchase
     */
    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    /**
     * @return payment type
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }
}
