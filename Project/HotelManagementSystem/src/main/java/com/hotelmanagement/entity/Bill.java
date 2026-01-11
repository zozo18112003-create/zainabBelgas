package com.hotelmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billNo;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Bill() {}

    public Bill(double amount, Customer customer) {
        this.amount = amount;
        this.customer = customer;
    }

    // Getters and Setters
    public int getBillNo() { return billNo; }
    public void setBillNo(int billNo) { this.billNo = billNo; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}
