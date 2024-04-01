package com.example.pocketcashier.model;

public class Product {
    private String name;
    private String color;
    private double cost;

    // Constructor
    public Product(String name, String color, double cost) {
        this.name = name;
        this.color = color;
        this.cost = cost;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public double getCost() {
        return cost;
    }

    // Setter methods (if needed)
    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // toString method to represent the object as a string
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", cost=" + cost +
                '}';
    }
}