package com.cerberus;

import com.cerberus.input.query.Query;
import com.cerberus.input.selection.*;
import com.cerberus.models.customer.Customer;
import com.cerberus.register.CustomerRegister;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;


public class Main {

    /**
     * customers data file location
     */
    public static String customersListLocation = "data/customers.json";

    /**
     * cycles data file location
     */
    public static String motorcyclesListLocation = "data/motorcycles.json";

    public static CustomerRegister customerRegister;

    public static void main(String[] args) throws IOException {


        /*Customer customer = new Customer("Jon", "Doe", "A258771", LocalDate.parse("2001-08-16"));
        Motorcycle cycle = new Motorcycle(
                "Honda",
                81000,
                MotorcycleTransmissionType.Manual,
                MotorcycleBrand.Honda,
                MotorcycleCylinderVolume.v150
        );

        customer.addEvent(new PaymentEvent(cycle, PurchaseType.purchase, PaymentType.cash));

        try {
            customer.setLease(new ArrayList<>());
        } catch (MaxLeaseExceedException e) {
            e.printStackTrace();
        }*/

        customerRegister = CustomerRegister.fromFile(new File(customersListLocation));

        Customer customer = customerRegister.getCustomers().get(0);

        SelectionMenu.create("Customer Details", new SelectionItem[] {

                SelectionOption.create("Add new", () -> {
                    addCustomer();
                    return null;
                }),
                SelectionSeperator.empty(),
                SelectionOption.create("All", () -> {
                    customerRegister.getCustomers().forEach(it -> {
                        System.out.println(it.getFullName());
                    });
                    return null;
                }),
                SelectionSeperator.empty(),
                SelectionOption.create("Exit", () -> null)

        }).prompt();

    }

    public static void addCustomer() {

        customerRegister.addCustomer(Customer.create());

        System.out.println(customerRegister);
    }
}
