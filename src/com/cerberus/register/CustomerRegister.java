package com.cerberus.register;

import com.cerberus.models.customer.Customer;
import com.cerberus.models.customer.event.Event;
import com.cerberus.models.customer.event.PurchaseEvent;
import com.cerberus.models.helpers.GsonHelper;
import com.cerberus.models.helpers.StringHelper;
import com.cerberus.models.motorcycle.Motorcycle;
import com.google.gson.Gson;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerRegister {

    private ArrayList<Customer> customers;
    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    private final File storage;
    public File getStorage() {
        return storage;
    }

    private Gson gson = GsonHelper.create();

    private CustomerRegister(File storage) {
        this.storage = storage;
        this.customers = new ArrayList<>();
    }

    public static CustomerRegister fromFile(File file) throws FileNotFoundException {
        CustomerRegister register = new CustomerRegister(file);

        Gson gson = GsonHelper.create();

        // load customers list from json data file
        Customer[] cArray = gson.fromJson(new FileReader(file), Customer[].class);
        try {
            register.setCustomers(new ArrayList<>(Arrays.asList(cArray)));
        } catch (NullPointerException ignored) {
            register.setCustomers(new ArrayList<>());
        }
        return register;
    }

    /**
     * adds new customer
     * removes this customer if storage cant be updated
     * @param customer customer to add
     */
    public void addCustomer(Customer customer) throws InvalidObjectException {
        addCustomer(customer, true);
    }

    /**
     * adds new customer
     * removes this customer if storage cant be updated
     * @param customer customer to add
     * @param update whether to check if storage can be updated if so do so
     */
    public void addCustomer(Customer customer, boolean update) throws InvalidObjectException {

        // check if it exists
        for (Customer it : this.getCustomers()) {
            if (it.getNationalId().equals(customer.getNationalId()))
                throw new InvalidObjectException("Customer wtih ID already exists.");
        }

        // add
        this.getCustomers().add(customer);

        if (update) {
            try {
                // update storage
                this.updateStorage();
            } catch (IOException e) {
                System.out.println(e);

                // remove
                this.getCustomers().remove(this.getCustomers().size() - 1);
            }
        }
    }

    /**
     * generated a report of all sales on month date
     * @param date month of reports to recover
     */
    public Report generateReport(LocalDate date) {

        ArrayList<Customer> customers = new ArrayList<>();

        for (Customer customer : getCustomers()) {

            // create a copy of this customer with clean history
            Customer constrainedCustomer = new Customer(
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getNationalId(),
                    customer.getBirthDate()
            );

            customer.getHistory().forEach(event -> {
                if (event.getClass() == PurchaseEvent.class) {
                    boolean yearMatch = ((PurchaseEvent) event).getDateTime().getYear() == date.getYear();
                    boolean monthMatch = ((PurchaseEvent) event).getDateTime().getMonth() == date.getMonth();

                    // if in this month
                    if (yearMatch && monthMatch) {
                        constrainedCustomer.getHistory().add(event);
                    }
                }
            });

            // if customer has any history this month
            if (constrainedCustomer.getHistory().size() != 0)
                // add
                customers.add(constrainedCustomer);

        }

        return new Report(customers.toArray(new Customer[0]));

    }

    public static void printReport(Report report) {

        int width = 100;

        String seperator = StringHelper.create("-", width);
        String indent = StringHelper.create(" ", 2);
        String padding = StringHelper.create(" ", 4);

        // header
        System.out.println(seperator);

        String heading = indent+"REPORT";
        System.out.print(heading);

        String date = report.getDate().getMonth().getValue() + "/" + report.getDate().getYear();
        System.out.print(StringHelper.create(" ", width - heading.length() - date.length() - indent.length()));
        System.out.println(date);

        System.out.println(seperator);

        if (report.getCustomers().length <= 0) {
            System.out.println(indent + "NO TRANSACTIONS THIS MONTH! :(");
        }

        double totol;
        for (int i = 0; i < report.getCustomers().length; i++) {
            Customer customer = report.getCustomers()[i];

            // name of customer and transactions count
            String name = StringHelper.padded(customer.getFullName().toUpperCase(), indent);
            String amount = "[ " + String.valueOf(customer.getHistory().size()) + " ]";

            System.out.print(name);
            System.out.print(StringHelper.create(" ", width - name.length() - amount.length() - indent.length()));
            System.out.println(amount);

            for (Event event : customer.getHistory()) {

                String lineHeading;
                String lineTrailing;
                String lineSpacer;

                if (event.getClass() == PurchaseEvent.class) {
                    event = (PurchaseEvent) event;

                    Motorcycle cycle = ((PurchaseEvent) event).getMotorcycle();
                    lineHeading = indent + "\u2022 " + cycle.getName();
                    lineTrailing = "RF " + StringHelper.formatMoney(cycle.getPrice()) + indent;
                    lineSpacer = StringHelper.getSpacer(width, lineHeading, lineTrailing);

                    System.out.println(lineHeading + lineSpacer + lineTrailing);

                    lineHeading = padding + "PURCHASE | " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(((PurchaseEvent) event).getDateTime());
                    lineTrailing = "";
                    lineSpacer = StringHelper.getSpacer(width, lineHeading, lineTrailing);

                    System.out.println(lineHeading + lineSpacer + lineTrailing);

                }
                // TODO add LeaseEvent conditional
                // TODO add InstallmentEvent conditional

            }

            // make space
            System.out.println();
        }

        System.out.println(seperator);

    }

    /**
     * overwrite current state to storage
     * @throws IOException
     */
    public void updateStorage() throws IOException {

        String json = gson.toJson(this.getCustomers().toArray());

        new FileWriter(storage) {{
            write(json);
        }}.close();

    }


}
