package com.cerberus.register;

import com.cerberus.input.confirm.ConfirmMenu;
import com.cerberus.input.query.Query;
import com.cerberus.input.selection.SelectionOption;
import com.cerberus.models.customer.Customer;
import com.cerberus.models.helpers.GsonHelper;
import com.cerberus.models.helpers.StringHelper;
import com.cerberus.models.helpers.string.SidedLine;
import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
        if (this.getByID(customer.getNationalId()) != -1)
            throw new InvalidObjectException("Customer with ID already exists.");

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
     * Searches the list iteratively to find customer
     * @param id to match
     * @return returns first matching customer index else -1 in case of no match
     */
    public int getByID(String id) {

        for (int i = 0; i < getCustomers().size(); i++) {
            Customer customer = getCustomers().get(i);

            if (id.equalsIgnoreCase(customer.getNationalId()))
                return i;

        }

        return -1;
    }

    /**
     * generated a report of all sales on month date
     * @param date month of reports to recover
     * @return Report containing all the transactions of this month
     */
    public Report monthlyReport(LocalDate date) {

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
                boolean yearMatch = event.getDateTime().getYear() == date.getYear();
                boolean monthMatch = event.getDateTime().getMonth() == date.getMonth();

                // if in this month
                if (yearMatch && monthMatch) {
                    constrainedCustomer.getHistory().add(event);
                }
            });

            // if customer has any history this month
            if (constrainedCustomer.getHistory().size() != 0)
                // add
                customers.add(constrainedCustomer);

        }

        return new Report(customers.toArray(new Customer[0]), date);

    }

    /**
     * generated a report of all sales on year date
     * @return Report containing all the transactions of this year
     */
    public Report yearlyReport(LocalDate date) {
        return yearlyReport(date.getYear());
    }

    /**
     * generated a report of all sales on year date
     * @param year of reports to recover
     * @return Report containing all the transactions of this year
     */
    public Report yearlyReport(int year) {

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
                boolean yearMatch = event.getDateTime().getYear() == year;

                // if in this month
                if (yearMatch) {
                    constrainedCustomer.getHistory().add(event);
                }
            });

            // if customer has any history this month
            if (constrainedCustomer.getHistory().size() != 0)
                // add
                customers.add(constrainedCustomer);

        }

        return new Report(customers.toArray(new Customer[0]), LocalDate.of(year, 1, 1));

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

    public void updateStorageIgnore() {

        String json = gson.toJson(this.getCustomers().toArray());

        try {
            new FileWriter(storage) {{
                write(json);
            }}.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * use promptNoAction function to give this menu functionality
     * @return list of SeletionOption with cycle name as label and no action
     */
    public List<SelectionOption> toSelectionList() {

        return getCustomers()
                .stream()
                .map(customer -> SelectionOption.create(customer.getFullName(), (index) -> {}))
                .collect(Collectors.toList());

    }

    public int promptId() {

        Query query = Query.create();

        int cIndex = -1;
        AtomicBoolean shouldContinue = new AtomicBoolean(true);

        // get from existing
        while (shouldContinue.get()) {
            String id = query.ask("Enter your ID: ", Scanner::nextLine);

            int idx = this.getByID(id);

            if (idx != -1) {

                // TODO add better checking

                cIndex = idx;
                break;
            } else {
                ConfirmMenu.create("try again? ",
                        () -> null,
                        () -> {shouldContinue.set(false); return null; })
                        .prompt();
            }

        }

        return cIndex;
    }

    public String getAll() {

        StringBuilder builder = new StringBuilder();

        int width = StringHelper.width;

        String separator = StringHelper.create("-", width);

        builder.append(separator).append("\n");
        builder.append(
                new SidedLine(width, "ALL CUSTOMERS", "")
        );
        builder.append(separator).append("\n");

        getCustomers().forEach(customer -> builder.append(
                new SidedLine(width, StringHelper.bullet() + " " + customer.getFullName(), customer.getNationalId())
        ));

        builder.append(separator).append("\n");
        builder.append(separator).append("\n");

        return builder.toString();
    }

}
