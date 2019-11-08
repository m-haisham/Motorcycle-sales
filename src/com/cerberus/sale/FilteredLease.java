package com.cerberus.sale;

public class FilteredLease {
    private final Integer[] paid;

    /**
     * @return all installments that have been paid for
     */
    public Integer[] getPaid() {
        return paid;
    }

    /**
     * @return all installments that haven't been paid for
     */
    private final Integer[] unpaid;
    public Integer[] getUnpaid() {
        return unpaid;
    }

    public FilteredLease(Integer[] paid, Integer[] unpaid) {
        this.paid = paid;
        this.unpaid = unpaid;
    }
}
