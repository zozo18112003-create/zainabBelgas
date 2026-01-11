package com.hotelmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double sharePercentage;

    public Owner() {}

    public Owner(String name, double sharePercentage) {
        this.name = name;
        this.sharePercentage = sharePercentage;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getSharePercentage() { return sharePercentage; }
    public void setSharePercentage(double sharePercentage) { this.sharePercentage = sharePercentage; }
}
