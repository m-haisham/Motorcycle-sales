package com.cerberus.sale;

import com.cerberus.models.motorcycle.Motorcycle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Lease {

    private static final float interestRate  = 0.10f;

    private double totalPrice;

    private Installment[] installmentSlices;

    private Motorcycle motorcycle;

    private final LocalDateTime timeLeased;

    public LocalDateTime getTimeLeased() {
        return timeLeased;
    }

    /**
     * default constructor
     * @param _motorcycle leased motorcycle
     * @param length months leased
     */
    public Lease (Motorcycle _motorcycle, int length) {
        motorcycle = _motorcycle;

        // setup price
        totalPrice = _motorcycle.getPrice() + getInterest(motorcycle.getPrice(), length);
        double monthlyPayment = totalPrice / length;
        LocalDate currentDate = LocalDate.now();

        installmentSlices = new Installment[length];
        for (int i = 0; i < installmentSlices.length; i++) {
            installmentSlices[i] = new Installment(monthlyPayment, currentDate.plusMonths(i));
        }

        // set leased time
        this.timeLeased = LocalDateTime.now();
    }

    /**
     * calculates the interest on the lease using interest {@link #interestRate}
     * @param _price amount interest is to be calculated on
     * @param length months interest is to be calculated for
     * @return interest
     */
    private double getInterest(double _price, int length) {
        return _price * interestRate * length;
    }

    /**
     * @return interest rate
     */
    public static float getInterestRate(){
        return interestRate;
    }

    /**
     * @return total price of lease
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * @return current installmentSlices
     */
    public Installment[] getinstallmentSlices() {
        return installmentSlices;
    }

    /**
     * @return motorcycle leased
     */
    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    /**
     * pays installment
     * @param _months amount of months to pay interest for
     */
    public void payinstallments(int _months) {
        for (int i = 0; i < installmentSlices.length; i++) {
            if (_months <= 0) {
                return;
            }

            if (!installmentSlices[i].isPaid()) {
                installmentSlices[i].setPaid(true);
                _months--;
            }
        }
    }

    /**
     * @return list of installment indexes unpaid up to today and installmentSlices in next 30 days
     */
    public ArrayList<Integer> installmentsDue() {
        LocalDate currentDate = LocalDate.now();

        ArrayList<Integer> installmentSlicesDue = new ArrayList<>();
        for (int i = 0; i < installmentSlices.length; i++) {
            Installment currentInstallmentSlice = installmentSlices[i];
            if (Math.abs(currentInstallmentSlice.getDueDate().getMonth().getValue() - currentDate.getMonth().getValue() ) <= 1 // check if any this installment in proximity
                    && currentInstallmentSlice.getDueDate().isAfter(currentDate)) { // check if installment date is after

                // due in next 30 days
                if (!currentInstallmentSlice.isPaid())
                    installmentSlicesDue.add(i);
                break;
            } else if (!currentInstallmentSlice.isPaid()
                        && currentInstallmentSlice.getDueDate().isBefore(currentDate))
                installmentSlicesDue.add(i);
        }
        return installmentSlicesDue;
    }

    /**
     * @return FilteredLease object containing all filters
     */
    public FilteredLease filterLeases() {
        ArrayList<Integer> paid = new ArrayList<>();
        ArrayList<Integer> unpaid = new ArrayList<>();

        for (int i = 0; i < installmentSlices.length; i++)
            if (installmentSlices[i].isPaid())
                paid.add(i);
            else
                unpaid.add(i);

        return new FilteredLease(paid.toArray(new Integer[0]), unpaid.toArray(new Integer[0]));
    }
}
