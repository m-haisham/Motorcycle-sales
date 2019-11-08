package com.cerberus;

import com.cerberus.input.confirm.ConfirmMenu;
import com.cerberus.input.query.Query;
import com.cerberus.input.selection.*;
import com.cerberus.models.motorcycle.Motorcycle;
import com.cerberus.models.motorcycle.MotorcycleBrand;
import com.cerberus.models.motorcycle.MotorcycleCylinderVolume;
import com.cerberus.models.motorcycle.MotorcycleTransmissionType;
import com.cerberus.register.Customer;
import com.cerberus.register.exceptions.MaxLeaseExceedException;
import com.cerberus.register.PaymentType;
import com.cerberus.register.event.PaymentEvent;

import com.cerberus.register.PurchaseType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static ArrayList<Customer> customerRegistry = new ArrayList<>();

    public static void main(String[] args) {


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

        addCustomer();
        Customer customer = customerRegistry.get(0);

        SelectionMenu.create("Customer Details", new SelectionItem[] {
                SelectionOption.create("Full name", () -> {

                    ConfirmMenu.create("Are you sure?",
                            (/* when chosen yes */) -> {
                                System.out.println(customer.getFullName());
                                return null;
                            },
                            (/* when chosen no */) -> {
                                System.out.println("Too bad");
                                return null;
                            }
                    ).prompt();
                    return null;
                }, new String[] {"name"}),
                SelectionOption.create("Age", () -> {
                    System.out.println(customer.getAge());
                    return null;
                }),
                SelectionOption.create("ID", () -> {
                    System.out.println(customer.getId());
                    return null;
                }),
                SelectionSeperator.empty(),
                SelectionOption.create("Exit", () -> null)

        }).prompt();

    }

    public static void addCustomer() {

        Query query = Query.create();

        String firstName = query.ask("First name: ", Scanner::nextLine);
        String lastName = query.ask("Last name: ", Scanner::nextLine);
        String id = query.ask("National ID: ", Scanner::nextLine);
        String birthday = query.ask("Birth date (yyyy-MM-dd): ", Scanner::nextLine);

        customerRegistry.add(new Customer(firstName, lastName, id, LocalDate.parse(birthday)));

        System.out.println(customerRegistry);
    }
}
