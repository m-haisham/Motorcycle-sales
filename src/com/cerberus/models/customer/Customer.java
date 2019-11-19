package com.cerberus.models.customer;

import com.cerberus.input.query.Query;
import com.cerberus.input.range.RangeMenu;
import com.cerberus.models.date.DateHelper;
import com.cerberus.models.motorcycle.Motorcycle;
import com.cerberus.models.customer.event.Event;
import com.cerberus.models.customer.event.PaymentEvent;
import com.cerberus.models.customer.exceptions.MaxLeaseExceedException;
import com.cerberus.sale.Installment;
import com.cerberus.sale.Lease;
import com.cerberus.sale.exceptions.DateSegmentError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author mHaisham and yaish
 * A Simple implementation of a customer for motorcycle sales
 */
public class Customer {

    private final String firstName;
    private final String lastName;
    private final String nationalId;
    private final LocalDate birthDate;

    /**
     * Motorcycle lease by customer
     */
    private ArrayList<Lease> leases;

    private int maxLeases;

    /**
     * Payment log of motorcycles
     */
    private ArrayList<Event> history = new ArrayList<>();

    /**
     * Default constructor
     * @param _firstName First name of customer
     * @param _lastName Last name of customer
     */
    public Customer(String _firstName, String _lastName, String _nationalId, LocalDate _birthDate) {
        firstName = _firstName;
        lastName = _lastName;
        nationalId = _nationalId;
        birthDate = _birthDate;
        leases = new ArrayList<>();
        maxLeases = 1;
    }

    public static Customer create() {

        Query query = Query.create();

        System.out.println("Customer Form");
        String firstName = query.ask("First Name: ", Scanner::nextLine);
        String lastName = query.ask("Last Name: ", Scanner::nextLine);
        String id = query.ask("National ID: ", Scanner::nextLine);

        int leastYear = LocalDate.now().minusYears(120).getYear();
        int year = RangeMenu.create("Year", leastYear, LocalDate.now().getYear() - 10).promptNoAction();
        int month = RangeMenu.create("Month",1,  DateHelper.getMonths().length).promptNoAction();
        int day = RangeMenu.create("Day", 1, DateHelper.getDays(month).length).promptNoAction();

        return new Customer(firstName, lastName, id, LocalDate.of(year, month, day));

    }

    /**
     * getter for {@link #firstName}
     * @return {@link #firstName}
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * getter for {@link #lastName}
     * @return {@link #lastName}
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Full name of customer
     * @return {@link #firstName} followed by {@link #lastName}
     */
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    /**
     * @return nationalId of customer
     */
    public String getNationalId() {
        return nationalId;
    }

    /**
     * @return birthdate of customer
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @return age of customer
     */
    public int getAge() {
        return Math.max(0,  LocalDate.now().getYear() - birthDate.getYear());
    }

    /**
     * getter for {@link #history}
     * @return {@link #history}
     */
    public ArrayList<Event> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Event> history) {
        this.history = history;
    }

    /**
     * Add Event to History of customer
     * @param event history object to be added to log
     */
    public void addEvent(Event event) {
        this.history.add(event);
    }

    /**
     * creates a motorcycle using {@link com.cerberus.models.motorcycle.Motorcycle} default constructor
     * creates Payment using {@link PaymentEvent} and motorcycle
     * then Payment to {@link #history}
     * @param motorcycle cycle paid for
     * @param purchaseType type of purchase
     * @param paymentType how it was paid for
     */
    public void addPayment(Motorcycle motorcycle, PurchaseType purchaseType, PaymentType paymentType) {
        PaymentEvent event = new PaymentEvent(motorcycle, purchaseType, paymentType);
        this.addEvent(event);
    }

    /**
     * @return whether the customer is leasing a motorcycle
     */
    public boolean isLeased() {
        return this.leases.size() == 0;
    }

    /**
     * @return lease of customer
     */
    public ArrayList<Lease> getLeases() {
        return this.leases;
    }

    /**
     * sets the lease of the customer
     * @param leases leases to be set
     */
    public void setLease(ArrayList<Lease> leases) throws MaxLeaseExceedException {
        if (leases.size() <= maxLeases) this.leases = leases;
        else throw new MaxLeaseExceedException("size exceeds max leases amount");
    }

    /**
     * add lease to leases list
     */
    public void addLease(Lease lease) throws MaxLeaseExceedException {
        if (leases.size() < maxLeases) this.leases.add(lease);
        else throw new MaxLeaseExceedException("Leases are maxed out");
    }

    /**
     * removes {@param lease} from the list of leases
     * @param lease to be removed
     * @throws IndexOutOfBoundsException when {@param lease} does not exist
     */
    public void removeLease(Lease lease) throws IndexOutOfBoundsException {
        int index = leases.indexOf(lease);

        // check if it exists
        if (index == -1)
            throw new IndexOutOfBoundsException("no such lease exists");

        // remove
        leases.remove(index);
    }

    /**
     * pay for leased vehicle
     * @param index lease index
     * @param segments number of segments to pay for
     * @param paymentType type of payment
     */
    public void payLeases(int index, int segments, PaymentType paymentType) {
        Lease lease = this.leases.get(index);

        for (Integer i : lease.installmentsDue()) {
            // if no segments left to update, end loop
            if (segments < 1) break;

            Installment installment = lease.getinstallmentSlices()[i];

            // try to add penalty
            try {
                installment.addPenaltyByRate(Lease.getInterestRate());
            } catch (DateSegmentError ignored) { }

            // pay
            installment.setPaid(true);

            // create payment event
            PaymentEvent event = new PaymentEvent(lease.getMotorcycle(), PurchaseType.lease, paymentType);

            // add transaction to installment
            installment.setPaymentEvent(event);

            // add transaction event to the history log of customer
            this.getHistory().add(event);

            // update segments left
            segments--;
        }

    }
}
