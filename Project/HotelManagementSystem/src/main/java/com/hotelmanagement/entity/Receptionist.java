package com.hotelmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "receptionists")
public class Receptionist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int shiftHours;

    public Receptionist() {}

    public Receptionist(String name, int shiftHours) {
        this.name = name;
        this.shiftHours = shiftHours;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getShiftHours() { return shiftHours; }
    public void setShiftHours(int shiftHours) { this.shiftHours = shiftHours; }
}
