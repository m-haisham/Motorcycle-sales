package com.cerberus.sale;

import java.time.LocalDate;

public class InstallmentSlice {

    private boolean paid = false;
    private double amount;
    private boolean penaltyAdded = false;
    private LocalDate dueDate;

    /**
     * default constructor
     * @param _amount money needed to be paid
     * @param _dueDate due date for installment
     */
    public InstallmentSlice(double _amount, LocalDate _dueDate){
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
    public void addPenalty(float rate) {
        amount = amount * (1 + rate);
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
