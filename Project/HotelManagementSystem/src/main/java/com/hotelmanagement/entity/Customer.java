package com.hotelmanagement.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;
    private int telNo;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bill> bills;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "customer_food",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private List<FoodItem> foodItems;

    public Customer() {}

    public Customer(String name, String address, int telNo) {
        this.name = name;
        this.address = address;
        this.telNo = telNo;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getTelNo() { return telNo; }
    public void setTelNo(int telNo) { this.telNo = telNo; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public List<Bill> getBills() { return bills; }
    public void setBills(List<Bill> bills) { this.bills = bills; }

    public List<FoodItem> getFoodItems() { return foodItems; }
    public void setFoodItems(List<FoodItem> foodItems) { this.foodItems = foodItems; }
}
