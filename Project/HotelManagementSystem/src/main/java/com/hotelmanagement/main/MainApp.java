package com.hotelmanagement.main;

import com.hotelmanagement.service.CustomerService;
import com.hotelmanagement.util.DatabaseSeeder;
import com.hotelmanagement.util.HibernateUtil;

public class MainApp {

    public static void main(String[] args) {
        try {
            System.out.println("Starting Hotel Management System...");

            // Seed Data
            DatabaseSeeder.seed();

            // Demonstration
            CustomerService service = new CustomerService();

            System.out.println("\n--- Customers with Bills ---");
            service.getCustomersWithBills()
                   .forEach(c -> System.out.println("Customer: " + c.getName() + " | Phone: " + c.getTelNo()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
