package com.cerberus;

import com.cerberus.input.confirm.ConfirmMenu;
import com.cerberus.input.query.Query;
import com.cerberus.input.range.RangeMenu;
import com.cerberus.input.selection.*;
import com.cerberus.models.customer.*;
import com.cerberus.models.customer.atomic.AtomicPaymentType;
import com.cerberus.models.customer.atomic.AtomicPurchaseType;
import com.cerberus.models.customer.exceptions.MaxLeaseExceedException;
import com.cerberus.models.helpers.InputHelper;
import com.cerberus.models.helpers.StringHelper;
import com.cerberus.models.helpers.string.SidedLine;
import com.cerberus.register.CustomerRegister;
import com.cerberus.register.MotorcyclesRegister;
import com.cerberus.register.Report;
import com.cerberus.sale.Lease;

import java.io.File;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


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

        /* initiations */
        customerRegister = CustomerRegister.fromFile(customersFile);
        motorcyclesRegister = MotorcyclesRegister.fromFile(motorcyclesFile);

        app();

        // app entry point
        if (!debug) app();
        else debug();

    }

    private static void app() {
        AtomicBoolean exit = new AtomicBoolean(false);

        SelectionMenu menu = SelectionMenu.create("Main Menu", new SelectionItem[] {
                SelectionOption.create("Add Transaction", (index) -> transaction()),
                SelectionOption.create("Catalogue", (index) -> {

                    AtomicBoolean exitCatalogue = new AtomicBoolean(false);

                    SelectionMenu catalogue = SelectionMenu.create("Catalogue", new SelectionItem[0]);

                    // add all motorcycles
                    catalogue.getItems().addAll(new ArrayList<>(
                            motorcyclesRegister
                                    .getMotorcycles()
                                    .stream()

                                    .map(motorcycle -> SelectionOption.create(new SidedLine(
                                            50,
                                            "",
                                            motorcycle.getName(),
                                            "RF " + StringHelper.formatMoney(motorcycle.getPrice())
                                    ) {{ this.setEndLine(false); }} .toString(), (idx) -> {
                                        System.out.println(motorcyclesRegister.getMotorcycles().get(idx).toDetailString());
                                        InputHelper.pause();
                                    }))

                                    .collect(Collectors.toList())
                    ));

                    catalogue.getItems().add(SelectionSeperator.empty());
                    catalogue.getItems().add(SelectionOption.create("Back", (idx) -> {
                        exitCatalogue.set(true);
                    }));



                    while (!exitCatalogue.get()) {
                        catalogue.prompt();
                    }

                    /* on catalogue exit */

                }),
                SelectionSeperator.empty(),
                SelectionOption.create("Customer", (index -> customer())),
                SelectionSeperator.empty(),
                SelectionOption.create("Report", (index -> report())),
                SelectionSeperator.empty(),
                SelectionOption.create("Back", (index) -> exit.set(true))
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

    private static void debug() {

        AtomicBoolean exit = new AtomicBoolean(false);

        SelectionMenu debugMenu = SelectionMenu.create("Debug Menu", new SelectionItem[] {

                SelectionOption.create("Add new Customer", (index) -> {
                    addCustomer();
                }),
                SelectionOption.create("Add new Purchase", (index) -> {
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
                }),
                SelectionOption.create("Add new Lease", (index) -> {
                    Customer customer = customerRegister.getCustomers().get(new Random().nextInt(customerRegister.getCustomers().size()));

                    Lease lease = new Lease(motorcyclesRegister.getMotorcycles().get(1), 24);

                    try {
                        customer.addLease(lease);
                    } catch (MaxLeaseExceedException e) {
                        System.out.println(e.getMessage());
                    }

                    try {
                        customerRegister.updateStorage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),

                SelectionOption.create("Pay Installment", (index) -> {
                    Customer customer = customerRegister.getCustomers().get(0);

                    customer.payLeases(0, 1, PaymentType.cash);

                    try {
                        customerRegister.updateStorage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                SelectionSeperator.empty(),
                SelectionOption.create("Report", (index) -> {
                    Report report = customerRegister.monthlyReport(LocalDate.now());
                    System.out.println(report);
                }),
                SelectionOption.create("Cycle display", (index) -> {

                    SelectionMenu catalogueMenu = SelectionMenu.create("Motorcycles", new ArrayList<>(motorcyclesRegister.toSelectionList()));

                    int option = catalogueMenu.promptNoAction();

                    System.out.println(motorcyclesRegister.getMotorcycles().get(option).toDetailString());
                }),
                SelectionOption.create("Customer display", (index) -> {

                    SelectionMenu customerMenu = SelectionMenu.create("Customers", new ArrayList<>(customerRegister.toSelectionList()));

                    int option = customerMenu.promptNoAction();

                    System.out.println(customerRegister.getCustomers().get(option).toDetailString());

                }),
                SelectionSeperator.empty(),
                SelectionOption.create("Exit", (index) -> {
                    exit.set(true);
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

    private static void transaction() {

        // get customer info
        AtomicInteger customerIndex = new AtomicInteger(-1);
        AtomicInteger motorcycleIndex = new AtomicInteger(-1);

        AtomicPurchaseType purchaseType = new AtomicPurchaseType(PurchaseType.purchase);
        AtomicPaymentType paymentType = new AtomicPaymentType(PaymentType.cash);

        Lease lease = null;

        AtomicBoolean exit = new AtomicBoolean(false);

        Query query = Query.create();

        /* customer identification */

        // get customer criteria
        ConfirmMenu.create("Is the customer registered?",
                (/* YES */) -> {

            AtomicBoolean shouldContinue = new AtomicBoolean(true);

            // get from existing
            while (shouldContinue.get()) {
                String id = query.ask("Enter your ID: ", Scanner::nextLine);

                int index = customerRegister.getByID(id);

                if (index != -1) {
                    customerIndex.set(index);
                    break;
                } else {
                    ConfirmMenu.create("try again? ",
                            () -> null,
                            () -> {shouldContinue.set(false); return null; })
                            .prompt();
                }

            }

            return null;

        }).prompt();

        // create new
        if (customerIndex.get() == -1) {

            while (true) {
                Customer c = Customer.create();

                try {
                    customerRegister.addCustomer(c);

                } catch (InvalidObjectException e) {
                    if (debug)
                        e.printStackTrace();

                    System.out.println(e.getMessage());
                    continue;
                }

                customerIndex.set(customerRegister.getCustomers().size() - 1);
                break;
            }
        }

        // hello message
        System.out.println("Customer: " + customerRegister.getCustomers().get(customerIndex.get()).getFullName());

        /* select motorcycle to purchase */

        SelectionMenu catalogue = SelectionMenu.create("Catalogue", new SelectionItem[0]);

        // add all motorcycles
        catalogue.getItems().addAll(new ArrayList<>(
                motorcyclesRegister
                        .getMotorcycles()
                        .stream()

                        .map(motorcycle -> SelectionOption.create(new SidedLine(
                                StringHelper.width,
                                "",
                                motorcycle.getName(),
                                "RF " + StringHelper.formatMoney(motorcycle.getPrice())
                        ) {{ this.setEndLine(false); }} .toString(), motorcycleIndex::set))

                        .collect(Collectors.toList())
        ));

        catalogue.getItems().add(SelectionSeperator.empty());
        catalogue.getItems().add(SelectionOption.create("Cancel", (idx) -> exit.set(true)));

        catalogue.prompt();

        // if transaction is cancelled
        if (exit.get()) return;

        // print details
        System.out.println(motorcyclesRegister.getMotorcycles().get(motorcycleIndex.get()).toDetailString());

        /* Lease or purchase */
        SelectionMenu.create("Purchase Type", new SelectionItem[]{
                SelectionOption.create("Purchase", (idx) -> purchaseType.set(PurchaseType.purchase)),
                SelectionOption.create("Lease", (idx) -> purchaseType.set(PurchaseType.lease)),
                SelectionSeperator.empty(),
                SelectionOption.create("Cancel", (idx) -> exit.set(true))
        }).prompt();

        // if transaction is cancelled
        if (exit.get()) return;

        // get additional lease related info and create lease
        if (purchaseType.get() == PurchaseType.lease) {
            int length = RangeMenu.create("Input length of lease period.", Lease.minLeasePeriod, Lease.maxLeasePeriod).promptNoAction();

            lease = new Lease(motorcyclesRegister.getMotorcycles().get(motorcycleIndex.get()), length);
        }

        /* cash or card */
        SelectionMenu.create("Payment Type", new SelectionItem[]{
                SelectionOption.create("Cash", (idx) -> paymentType.set(PaymentType.cash)),
                SelectionOption.create("Card", (idx) -> paymentType.set(PaymentType.card)),
                SelectionSeperator.empty(),
                SelectionOption.create("Cancel", (idx) -> exit.set(true))
        }).prompt();

        // if transaction is cancelled
        if (exit.get()) return;

        // complete transaction
        try {
            completeTransaction(
                    customerIndex.get(),
                    motorcycleIndex.get(),
                    purchaseType.get(),
                    paymentType.get(),
                    lease
            );
        } catch (MaxLeaseExceedException e) {
            if (debug)
                e.printStackTrace();

            System.out.println(e.getMessage());
            System.out.println("Transaction was not completed");
            return;
        }

        System.out.println("Transaction completed successfully.");

    }

    private static void completeTransaction(int c, int m, PurchaseType purchaseType, PaymentType paymentType, Lease lease) throws MaxLeaseExceedException {

        switch (purchaseType) {
            case purchase:
                customerRegister.getCustomers().get(c).addPurchase(
                        motorcyclesRegister.getMotorcycles().get(m),
                        paymentType
                );
                break;

            case lease:
                customerRegister.getCustomers().get(c).addLease(lease);

                // pay first installment
                customerRegister.getCustomers().get(c).payLeases(
                        // last added
                        customerRegister.getCustomers().get(c).getLeases().size() - 1,
                        1,
                        paymentType
                );
                break;
        }

    }

    private static void customer() {

        AtomicBoolean exit = new AtomicBoolean(false);

        // TODO implement below methods
        SelectionMenu menu = SelectionMenu.create("Customer", new SelectionItem[]{
                SelectionOption.create("Add", (index) -> {
                    try {
                        customerRegister.addCustomer(Customer.create());
                    } catch (InvalidObjectException e) {
                        if (debug) e.printStackTrace();
                        else System.out.println(e.getMessage());
                    }

                    customerRegister.updateStorageIgnore();
                }),
                SelectionOption.create("Remove", (index) -> {

                    int idx = customerRegister.promptId();

                    if (idx == -1)
                        return;

                    // remove customer
                    Customer removed = customerRegister.getCustomers().get(idx);
                    customerRegister.getCustomers().remove(idx);

                    System.out.println("Successfully remove "+removed.getFullName() + " | " + removed.getNationalId());

                    customerRegister.updateStorageIgnore();
                }),
                SelectionOption.create("Modify", (index) -> {

                    int idx = customerRegister.promptId();

                    if (idx == -1)
                        return;

                    // get modified
                    Customer modified = customerRegister.getCustomers().get(idx).modify();

                    // set and replace
                    customerRegister.getCustomers().set(idx, modified);

                    customerRegister.updateStorageIgnore();
                }),
                SelectionSeperator.empty(),
                SelectionOption.create("Installments due", (index) -> {}),
                SelectionOption.create("Full history", (index) -> {}),
                SelectionSeperator.empty(),
                SelectionOption.create("Back", (index) -> exit.set(true)),
        });

        while (!exit.get()) {
            menu.prompt();
        }

    }
    private static void report() {
        // TODO monthly [ this | select ]
        // TODO yearly [ this | select ]
        // TODO all
    }
}
