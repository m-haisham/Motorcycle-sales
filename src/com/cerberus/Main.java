package com.cerberus;

import com.cerberus.input.selection.*;
import com.cerberus.models.customer.Customer;
import com.cerberus.models.customer.PaymentType;
import com.cerberus.models.customer.PurchaseType;
import com.cerberus.models.customer.exceptions.MaxLeaseExceedException;
import com.cerberus.register.CustomerRegister;
import com.cerberus.register.MotorcyclesRegister;
import com.cerberus.register.Report;
import com.cerberus.sale.Lease;

import java.io.File;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


public class Main {

    /**
     * customers data file
     */
    public static File customersFile = new File("data/customers.json");

    /**
     * cycles data file
     */
    public static File motorcyclesFile = new File("data/motorcycles.json");

    public static CustomerRegister customerRegister;

    public static MotorcyclesRegister motorcyclesRegister;

    public static boolean debug = true;

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

        /* initiations */
        customerRegister = CustomerRegister.fromFile(customersFile);
        motorcyclesRegister = MotorcyclesRegister.fromFile(motorcyclesFile);

        // app entry point
        if (!debug) app();
        else debug();

    }

    private static void app() {
        AtomicBoolean exit = new AtomicBoolean(false);

        SelectionMenu menu = SelectionMenu.create("Main Menu", new SelectionItem[] {
                SelectionOption.create("Customer", () -> { amCustomer(); return null; }),
                SelectionOption.create("Retailer", () -> { amRetailer(); return null; }),
                SelectionSeperator.empty(),
                SelectionOption.create("Back", () -> { exit.set(true); return null; })
        });

        while (!exit.get()) {
            menu.prompt();
        }

        /* on application exit */

        // save state
        try {
            customerRegister.updateStorage();
            motorcyclesRegister.updateStorage();
        } catch (IOException e) {
            e.printStackTrace(); // for debugging
//            System.out.println(e.getMessage());
        }
    }

    private static void amCustomer() {

        AtomicBoolean loop = new AtomicBoolean(true);

        SelectionMenu customerMenu = SelectionMenu.create("Menu", new SelectionItem[]{
                SelectionOption.create("Browse", () -> {

                    motorcyclesRegister.getMotorcycles().forEach(motorcycle -> {
                        System.out.println(motorcycle.toDetailString());
                        System.out.println();
                    });

                    System.out.print("Press enter to continue ...");
                    new Scanner(System.in).nextLine();

                    return null;
                }),
                SelectionSeperator.empty(),
                SelectionOption.create("Back", () -> {
                    loop.set(false);
                    return null;
                })
        });

        while (loop.get())
            customerMenu.prompt();

    }

    private static void amRetailer() {

    }

    private static void debug() {

        AtomicBoolean exit = new AtomicBoolean(false);

        SelectionMenu debugMenu = SelectionMenu.create("Debug Menu", new SelectionItem[] {

                SelectionOption.create("Add new Customer", () -> {
                    addCustomer();
                    return null;
                }),
                SelectionOption.create("Add new Purchase", () -> {
                    Customer customer = customerRegister.getCustomers().get(new Random().nextInt(customerRegister.getCustomers().size()));
                    System.out.println(motorcyclesRegister.getMotorcycles());
                    customer.addPurchase(
                            motorcyclesRegister.getMotorcycles().get(0),
                            PaymentType.card
                    );
                    try {
                        customerRegister.updateStorage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }),
                SelectionOption.create("Add new Lease", () -> {
                    Customer customer = customerRegister.getCustomers().get(new Random().nextInt(customerRegister.getCustomers().size()));

                    Lease lease = new Lease(motorcyclesRegister.getMotorcycles().get(1), 24);

                    try {
                        customer.addLease(lease);
                    } catch (MaxLeaseExceedException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }

                    try {
                        customerRegister.updateStorage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }),

                SelectionOption.create("Pay Installment", () -> {
                    Customer customer = customerRegister.getCustomers().get(0);

                    customer.payLeases(0, 1, PaymentType.cash);

                    try {
                        customerRegister.updateStorage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }),
                SelectionSeperator.empty(),
                SelectionOption.create("Report", () -> {
                    Report report = customerRegister.generateReport(LocalDate.now());
                    System.out.println(report);
                    return null;
                }),
                SelectionOption.create("Cycle display", () -> {

                    SelectionMenu catalogueMenu = SelectionMenu.create("Motorcycles", motorcyclesRegister.toSelectionList());

                    int option = catalogueMenu.promptNoAction();

                    System.out.println(motorcyclesRegister.getMotorcycles().get(option).toDetailString());
                    return null;
                }),
                SelectionOption.create("Customer display", () -> {

                    SelectionMenu customerMenu = SelectionMenu.create("Customers", customerRegister.toSelectionList());

                    int option = customerMenu.promptNoAction();

                    System.out.println(customerRegister.getCustomers().get(option).toDetailString());

                    return null;
                }),
                SelectionSeperator.empty(),
                SelectionOption.create("Exit", () -> {
                    exit.set(true);
                    return null;
                })

        });

        while (!exit.get()) {
            debugMenu.prompt();
        }

        // on application exit
    }

    public static void addCustomer() {

        try {
            customerRegister.addCustomer(Customer.create());
        } catch (InvalidObjectException e) {
            System.out.println(e.getMessage());
        }

    }
}
