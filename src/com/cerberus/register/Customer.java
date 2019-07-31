package com.cerberus.register;

import com.cerberus.motorcycle.Motorcycle;
import com.cerberus.motorcycle.MotorcycleBrand;
import com.cerberus.motorcycle.MotorcycleCylinderVolume;
import com.cerberus.motorcycle.MotorcycleTransmissionType;
import com.cerberus.sale.Lease;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author mHaisham and yaish
 * A Simple implementation of a customer for motorcycle sales
 */
public class Customer {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String nationalId;
    private final LocalDate birthDate;

    /**
     * if customer is currently leasing a motorcycle
     */
    private boolean leased;

    /**
     * Motorcycle lease by customer
     */
    private Lease lease; // allow for multiple leases

    /**
     * Payment log of motorcycles
     */
    private final ArrayList<Payment> paymentLog = new ArrayList<>();

    /**
     * Default constructor
     * @param _firstName First name of customer
     * @param _lastName Last name of customer
     */
    public Customer(String _firstName, String _lastName, String _nationalId, LocalDate _birthDate) {
        id = UUID.randomUUID().toString();
        firstName = _firstName;
        lastName = _lastName;
        nationalId = _nationalId;
        birthDate = _birthDate;
        leased = false;
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
     * getter for {@link #paymentLog}
     * @return {@link #paymentLog}
     */
    public ArrayList<Payment> getpaymentLog() {
        return paymentLog;
    }

    /**
     * Add Payment to Payment log of customer
     * @param _Payment Payment object to be added to log
     */
    public void addPayment(Payment _Payment) {
        paymentLog.add(_Payment);
    }

    /**
     * creates a motorcycle using {@link com.cerberus.motorcycle.Motorcycle} default constructor
     * creates Payment using {@link com.cerberus.register.Payment} and motorcycle
     * then Payment to {@link #paymentLog}
     * @param _name name of motorcycle Paymentd
     * @param _price price of motorcycle Paymentd
     * @param _type type of motorcycle Paymentd
     */
    public void addPayment(String _name, double _price, MotorcycleTransmissionType _type, MotorcycleBrand _brand, MotorcycleCylinderVolume _power) {
        Motorcycle cycle = new Motorcycle(_name, _price, _type, _brand, _power);
        paymentLog.add(new Payment(cycle, PurchaseType.purchase));
    }

    /**
     * @return UUID of customer
     */
    public String getId() {
        return id;
    }

    /**
     * @return whether the customer is leasing a motorcycle
     */
    public boolean isLeased() {
        return leased;
    }

    /**
     * @return lease of customer
     */
    public Lease getLease() {
        return lease;
    }

    /**
     * sets the lease of the customer
     * @param motorcycle motorcycle leased
     * @param duration duration in months
     */
    public void setLease(Motorcycle motorcycle, int duration) {
        if (!leased) {
            leased = true;
            Lease _lease = new Lease(motorcycle, duration);
            lease = _lease;
        }
    }

}
