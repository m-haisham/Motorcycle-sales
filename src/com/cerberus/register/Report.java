package com.cerberus.register;

import com.cerberus.models.customer.Customer;
import com.cerberus.models.customer.event.Event;
import com.cerberus.models.motorcycle.Motorcycle;
import com.cerberus.sale.Lease;

import java.time.LocalDate;

public class Report {

    /**
     * Generated time
     */
    private final LocalDate date;
    public LocalDate getDate() {
        return date;
    }

    private Customer[] customers;
    public void setCustomers(Customer[] customers) {
        this.customers = customers;
    }
    public Customer[] getCustomers() {
        return customers;
    }

    public Report(Customer[] customers) {
        this.customers = customers;
        date = LocalDate.now();
    }
}
