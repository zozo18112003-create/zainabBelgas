package com.hotelmanagement.util;

import com.hotelmanagement.entity.Bill;
import com.hotelmanagement.entity.Customer;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.service.BillingService;
import com.hotelmanagement.service.CustomerService;
import com.hotelmanagement.service.RoomService;

public class DatabaseSeeder {

    public static void seed() {
        CustomerService customerService = new CustomerService();
        RoomService roomService = new RoomService();
        BillingService billingService = new BillingService();

        // Check if data exists (simple check)
        if (!customerService.getAllCustomers().isEmpty()) {
            return;
        }

        System.out.println("Seeding database...");

        // Create Rooms
        Room r1 = new Room(101, "First Floor", true);
        Room r2 = new Room(102, "First Floor", true);
        roomService.addRoom(r1);
        roomService.addRoom(r2);

        // Create Customers
        Customer c1 = new Customer("John Doe", "123 Main St", 5551234);
        c1.setRoom(r1); // Assign room
        customerService.addCustomer(c1);

        Customer c2 = new Customer("Jane Smith", "456 Oak St", 5555678);
        c2.setRoom(r2);
        customerService.addCustomer(c2);

        // Create Bills
        Bill b1 = new Bill(150.00, c1);
        billingService.createBill(b1); // This might not associate nicely if Bill didn't cascade properly or we didn't add to customer list.
        // Since Bill owns the relationship (ManyToOne), saving Bill with setCustomer works.

        System.out.println("Database seeded.");
    }
}
