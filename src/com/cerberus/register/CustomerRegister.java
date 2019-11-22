package com.cerberus.register;

import com.cerberus.input.selection.SelectionOption;
import com.cerberus.models.customer.Customer;
import com.cerberus.models.helpers.GsonHelper;
import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDate;
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
        if (this.getByID(customer.getNationalId()) == -1)
            throw new InvalidObjectException("Customer wtih ID already exists.");

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
     * overwrite current state to storage
     * @throws IOException
     */
    public void updateStorage() throws IOException {

        String json = gson.toJson(this.getCustomers().toArray());

        new FileWriter(storage) {{
            write(json);
        }}.close();

    }

    public SelectionOption[] toSelectionList() {

        ArrayList<SelectionOption> items = new ArrayList<>();

        this.getCustomers().forEach(customer -> {
            items.add(
                    SelectionOption.create(customer.getFullName(), () -> null)
            );
        });

        return items.toArray(new SelectionOption[0]);

    }


}
