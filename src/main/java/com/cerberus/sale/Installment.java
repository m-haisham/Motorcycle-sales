package com.cerberus.sale;

import com.cerberus.models.customer.event.PurchaseEvent;
import com.cerberus.sale.exceptions.DateSegmentError;

import java.time.LocalDate;

public class Installment {

    private boolean paid = false;
    private double amount;
    private boolean penaltyAdded = false;
    private LocalDate dueDate;

    /**
     * default constructor
     * @param _amount money needed to be paid
     * @param _dueDate due date for installment
     */
    public Installment(double _amount, LocalDate _dueDate){
        amount = _amount;
        dueDate = _dueDate;
    }

    /**
     * @return amount of money
     */
    public double getAmount() {
        return amount;
    }

    /**
     * adds penalty to amount
     * @param rate interest rate to be applied to calculate penalty
     */
    public void addPenaltyByRate(float rate) throws DateSegmentError {
        if (dueDate.isAfter(LocalDate.now()))
            throw new DateSegmentError("Due date has not yet been reached");

        int yearsUnpaid = LocalDate.now().getYear() - dueDate.getYear();
        int monthsUnpaid = LocalDate.now().getMonth().getValue() - dueDate.getMonth().getValue();
        amount = amount * /* full interest percent */(1 + (rate * /* months unpaid */(yearsUnpaid * 12 + monthsUnpaid)));
        penaltyAdded = true;
    }

    public void addPenalty(double amount) {
        this.amount += amount;
        penaltyAdded = true;
    }

    /**
     * @return date installment is due
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * @return whether the installment has been paid
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * change whether the installment has been paid
     * @param paid status of payment to apply
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * @return whether penalty has been applied to money
     */
    public boolean isPenaltyAdded() {
        return penaltyAdded;
    }
}
