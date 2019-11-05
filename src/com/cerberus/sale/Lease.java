package com.cerberus.sale;

import com.cerberus.models.motorcycle.Motorcycle;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lease {

    private static final float interestRate  = 0.10f;

    private double totalPrice;

    private InstallmentSlice[] installmentSlices;

    private Motorcycle motorcycle;

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

        installmentSlices = new InstallmentSlice[length];
        for (int i = 0; i < installmentSlices.length; i++) {
            installmentSlices[i] = new InstallmentSlice(monthlyPayment, currentDate.plusMonths(i));
        }
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
    public InstallmentSlice[] getinstallmentSlices() {
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
     * @param _months amount months to pay interest for
     */
    public void payinstallmentSlices(int _months) {
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
     * @return list of installmentSlices unpaid up to today and installmentSlices in next 30 days
     */
    public ArrayList<InstallmentSlice> installmentSlicesDue() {
        LocalDate currentDate = LocalDate.now();

        ArrayList<InstallmentSlice> installmentSlicesDue = new ArrayList<>();
        for (int i = 0; i < installmentSlices.length; i++) {
            InstallmentSlice currentInstallmentSlice = installmentSlices[i];
            if (Math.abs(currentInstallmentSlice.getDueDate().getMonth().getValue() - currentDate.getMonth().getValue() ) <= 1 // check if any this installment in proximity
                    && currentInstallmentSlice.getDueDate().isAfter(currentDate)) { // check if installment date is after
                // due in next 30 days
                if (!currentInstallmentSlice.isPaid())
                    installmentSlicesDue.add(currentInstallmentSlice);
                break;
            } else if (!currentInstallmentSlice.isPaid()
                        && currentInstallmentSlice.getDueDate().isBefore(currentDate))
                installmentSlicesDue.add(currentInstallmentSlice);
        }
        return installmentSlicesDue;
    }

    // filter paid and unpaid
}
