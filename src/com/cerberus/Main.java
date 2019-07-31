package com.cerberus;

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

        System.out.println(customer.getId());

        System.out.println(customer.getAge());

//        Gson gson = new Gson();
//        try {
//            FileWriter file = new FileWriter(new File("motorcycle.json"));
//            String jsonData = gson.toJson(cycle);
//            file.write(jsonData);
//            file.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
