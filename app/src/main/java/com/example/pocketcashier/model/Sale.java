// Sale.java
package com.example.pocketcashier.model;

public class Sale {
    private int id;
    private String dateTime;
    private double total;

    public Sale(int id, String dateTime, double total) {
        this.id = id;
        this.dateTime = dateTime;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
