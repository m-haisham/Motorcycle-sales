package com.cerberus.register;

import com.cerberus.models.customer.Customer;
import com.google.gson.Gson;

import java.io.*;
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

    private Gson gson = new Gson();

    private CustomerRegister(File storage) {
        this.storage = storage;
        this.customers = new ArrayList<>();
    }

    public static CustomerRegister fromFile(File file) throws FileNotFoundException {
        CustomerRegister register = new CustomerRegister(file);

        Gson gson = new Gson();

        // load customers list from json data file
        Customer[] cArray = gson.fromJson(new FileReader(file), Customer[].class);
        register.setCustomers(new ArrayList<>(Arrays.asList(cArray)));

        return register;
    }

    /**
     * adds new customer
     * removes this customer if storage cant be updated
     * @param customer customer to add
     */
    public void addCustomer(Customer customer) {
        addCustomer(customer, true);
    }

    /**
     * adds new customer
     * removes this customer if storage cant be updated
     * @param customer customer to add
     * @param update whether to check if storage can be updated if so do so
     */
    public void addCustomer(Customer customer, boolean update) {
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
     * overwrite current state to storage
     * @throws IOException
     */
    private void updateStorage() throws IOException {

        String json = gson.toJson(this.getCustomers().toArray());

        new FileWriter(storage) {{
            write(json);
        }}.close();

    }


}
