package com.cerberus.register;

import com.cerberus.motorcycle.Motorcycle;

import java.time.LocalDateTime;

public class Payment {

    private final LocalDateTime dateTime;
    private final Motorcycle motorcycle;
    private final PurchaseType type; //change to purchase type

    // payment type - cash/card

    /**
     * default constructor
     * @param _motorcycle motorcycle payment is applied on
     * @param _type type of payment
     */
    public Payment(Motorcycle _motorcycle, PurchaseType _type) {
        motorcycle = _motorcycle;
        type = _type;
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
     * @return type of payment
     */
    public PurchaseType getType() {
        return type;
    }

}
