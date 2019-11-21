package com.cerberus.models.customer.event;

import com.cerberus.models.customer.PaymentType;
import com.cerberus.models.motorcycle.Motorcycle;
import com.cerberus.sale.Installment;
import com.cerberus.sale.Lease;

import java.time.LocalDateTime;

public class InstallmentEvent extends Event {

    private final Motorcycle motorcycle;
    private final PaymentType paymentType;

    private final Installment installment;

    public InstallmentEvent(Motorcycle motorcycle, PaymentType paymentType, Installment installment) {
        super();
        this.motorcycle = motorcycle;
        this.paymentType = paymentType;
        this.installment = installment;
    }

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Installment getInstallment() {
        return installment;
    }
}
