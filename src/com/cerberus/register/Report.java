package com.cerberus.register;

import com.cerberus.models.customer.Customer;
import com.cerberus.models.customer.event.Event;
import com.cerberus.models.customer.event.InstallmentEvent;
import com.cerberus.models.customer.event.LeaseEvent;
import com.cerberus.models.customer.event.PurchaseEvent;
import com.cerberus.models.helpers.StringHelper;
import com.cerberus.models.motorcycle.Motorcycle;
import com.cerberus.sale.Lease;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public Report(Customer[] customers, LocalDate date) {
        this.customers = customers;
        this.date = date;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        int width = StringHelper.width;

        String separator = StringHelper.create("-", width);
        String indent = StringHelper.create(" ", 2);
        String padding = StringHelper.create(" ", 4);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // header
        builder.append(separator).append("\n");

        String heading = indent+"REPORT";
        builder.append(heading);

        String date = this.getDate().getMonth().getValue() + "/" + this.getDate().getYear();
        builder.append(StringHelper.create(" ", width - heading.length() - date.length() - indent.length()));
        builder.append(date).append("\n");

        builder.append(separator).append("\n");

        if (this.getCustomers().length <= 0) {
            builder.append(indent).append("NO TRANSACTIONS THIS MONTH! :(\n");
        }

        double total = 0;
        for (int i = 0; i < this.getCustomers().length; i++) {
            Customer customer = this.getCustomers()[i];

            // name of customer and transactions count
            String name = StringHelper.padded(customer.getFullName().toUpperCase(), indent);
            String amount = "[ " + String.valueOf(customer.getHistory().size()) + " ]";

            builder.append(name);
            builder.append(StringHelper.create(" ", width - name.length() - amount.length() - indent.length()));
            builder.append(amount).append("\n");

            for (Event event : customer.getHistory()) {

                String lineLeading;
                String lineTrailing;
                String lineSpacer;

                if (event.getClass() == PurchaseEvent.class) {
                    event = (PurchaseEvent) event;

                    Motorcycle cycle = ((PurchaseEvent) event).getMotorcycle();
                    lineLeading = indent + StringHelper.bullet() + " " + "PURCHASE | " + cycle.getName();
                    lineTrailing = "RF " + StringHelper.formatMoney(cycle.getPrice()) + indent;
                    lineSpacer = StringHelper.getSpacer(width, lineLeading, lineTrailing);

                    builder.append(lineLeading).append(lineSpacer).append(lineTrailing).append("\n");

                    // payment type
                    lineLeading = padding + ((PurchaseEvent) event).getPaymentType();

                    builder.append(lineLeading).append("\n");

                    lineLeading = padding + timeFormatter.format(((PurchaseEvent) event).getDateTime());
                    lineTrailing = indent;
                    lineSpacer = StringHelper.getSpacer(width, lineLeading, lineTrailing);

                    builder.append(lineLeading).append(lineSpacer).append(lineTrailing).append("\n");

                    // add amount to total
                    total += cycle.getPrice();

                } else if (event.getClass() == LeaseEvent.class) {
                    event = (LeaseEvent) event;

                    Motorcycle motorcycle = ((LeaseEvent) event).getLease().getMotorcycle();

                    lineLeading = indent + StringHelper.bullet() + " " + "LEASE | " + motorcycle.getName();
                    lineTrailing = ((LeaseEvent) event).getLease().getinstallmentSlices().length + " MONTHS" + indent;
                    lineSpacer = StringHelper.getSpacer(width, lineLeading, lineTrailing);

                    builder.append(lineLeading).append(lineSpacer).append(lineTrailing).append("\n");

                    lineLeading = padding + timeFormatter.format(event.getDateTime());
                    lineTrailing = indent;
                    lineSpacer = "";

                    builder.append(lineLeading).append(lineSpacer).append(lineTrailing).append("\n");

                } else if (event.getClass() == InstallmentEvent.class) {
                    event = (InstallmentEvent) event;

                    Motorcycle motorcycle = ((InstallmentEvent) event).getMotorcycle();

                    lineLeading = indent + StringHelper.bullet() + " " + "INSTALLMENT | " + motorcycle.getName();
                    lineTrailing = "RF " + StringHelper.formatMoney(((InstallmentEvent) event).getInstallment().getAmount()) + indent;
                    lineSpacer = StringHelper.getSpacer(width, lineLeading, lineTrailing);

                    builder.append(lineLeading).append(lineSpacer).append(lineTrailing).append("\n");

                    // payment type
                    lineLeading = padding + ((InstallmentEvent) event).getPaymentType();

                    builder.append(lineLeading).append("\n");

                    lineLeading = padding + timeFormatter.format(event.getDateTime());
                    lineTrailing = indent;
                    lineSpacer = "";

                    builder.append(lineLeading).append(lineSpacer).append(lineTrailing).append("\n");

                    // add amount to total
                    total += ((InstallmentEvent) event).getInstallment().getAmount();

                }

            }

            // make space
            builder.append("\n");
        }

        builder.append(separator).append("\n");

        // calculate and append total
        String leading = indent + "TOTAL";
        String trailing = "RF " + StringHelper.formatMoney(total) + indent;
        String spacer = StringHelper.getSpacer(width, leading, trailing);

        builder.append(leading).append(spacer).append(trailing).append("\n");

        builder.append(separator).append("\n");
        builder.append(separator).append("\n");

        return builder.toString();
    }
}
