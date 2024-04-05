// Sale.java
package com.example.pocketcashier.model;

import java.util.LinkedHashMap;

public class Sale {
    private int id;
    private String dateTime;
    private double total;

    private int clientId;
    public LinkedHashMap<Product, Integer> cart;

    Client client;

    public Sale(int id, String dateTime, double total, LinkedHashMap<Product, Integer> cart, int clientId) {
        this.id = id;
        this.dateTime = dateTime;
        this.total = total;
        this.cart = cart;
        this.clientId = clientId;
    }

    public void setClient(String CI, String name){
        this.client = new Client(Integer.parseInt(CI), name, CI);
    }

    public void setClient(Client client){
        this.client = client;
    }

    public int getClientId() {
        return clientId;
    }

    public Client getClient() {
        return client;
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
