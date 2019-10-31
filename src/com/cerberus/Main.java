package com.cerberus;

import com.cerberus.input.confirm.ConfirmMenu;
import com.cerberus.input.selection.SelectionItem;
import com.cerberus.input.selection.SelectionMenu;
import com.cerberus.input.selection.SelectionOption;
import com.cerberus.input.selection.SelectionSeperator;
import com.cerberus.motorcycle.Motorcycle;
import com.cerberus.motorcycle.MotorcycleBrand;
import com.cerberus.motorcycle.MotorcycleCylinderVolume;
import com.cerberus.motorcycle.MotorcycleTransmissionType;
import com.cerberus.register.Customer;
import com.cerberus.register.Payment;

import com.cerberus.register.PurchaseType;

import java.time.LocalDate;


public class Main {

    public static void main(String[] args) {

        Customer customer = new Customer("Jon", "Doe", "A258771", LocalDate.parse("2001-08-16"));
        Motorcycle cycle = new Motorcycle(
                "Honda",
                81000,
                MotorcycleTransmissionType.Manual,
                MotorcycleBrand.Honda,
                MotorcycleCylinderVolume.v150
        );

        customer.addPayment(new Payment(cycle, PurchaseType.purchase));
        customer.setLease(cycle, 24);

        SelectionMenu details = SelectionMenu.create("Customer Details", new SelectionItem[] {
                SelectionOption.create("Full name", () -> {

                    ConfirmMenu.create("Are you sure?",
                            (/* when chosen yes */) -> {
                                System.out.println(customer.getFullName());
                                return true;
                            },
                            (/* when chosen no */) -> {
                                System.out.println("Too bad");
                                return true;
                            }
                    ).prompt();

                    return true;
                }),
                SelectionOption.create("Age", () -> {
                    System.out.println(customer.getAge());
                    return true;
                }),
                SelectionOption.create("ID", () -> {
                    System.out.println(customer.getId());
                    return true;
                }),
                SelectionSeperator.create(" "),
                SelectionOption.create("Exit", () -> { return true; })

        });

        details.prompt();


    }
}
