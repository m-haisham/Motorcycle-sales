package com.cerberus;

import com.cerberus.input.confirm.ConfirmMenu;
import com.cerberus.input.selection.*;
import com.cerberus.models.motorcycle.Motorcycle;
import com.cerberus.models.motorcycle.MotorcycleBrand;
import com.cerberus.models.motorcycle.MotorcycleCylinderVolume;
import com.cerberus.models.motorcycle.MotorcycleTransmissionType;
import com.cerberus.register.Customer;
import com.cerberus.register.MaxLeaseExceedException;
import com.cerberus.register.PaymentType;
import com.cerberus.register.event.PaymentEvent;

import com.cerberus.register.PurchaseType;
import com.cerberus.sale.Lease;

import java.time.LocalDate;
import java.util.ArrayList;


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

        customer.addEvent(new PaymentEvent(cycle, PurchaseType.purchase, PaymentType.cash));

        try {
            customer.setLease(new ArrayList<>());
        } catch (MaxLeaseExceedException e) {
            e.printStackTrace();
        }

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
}
