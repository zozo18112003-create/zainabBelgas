package com.hotelmanagement.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "room_no")
    private int roomNo;

    @Column(name = "location")
    private String location;

    @Column(name = "available")
    private boolean available;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Customer> customers;

    public Room() {}

    public Room(int roomNo, String location, boolean available) {
        this.roomNo = roomNo;
        this.location = location;
        this.available = available;
    }

    // Getters and Setters
    public int getRoomNo() { return roomNo; }
    public void setRoomNo(int roomNo) { this.roomNo = roomNo; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public List<Customer> getCustomers() { return customers; }
    public void setCustomers(List<Customer> customers) { this.customers = customers; }
}
