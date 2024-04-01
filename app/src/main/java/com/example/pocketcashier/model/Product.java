package com.example.pocketcashier.model;

public class Product {
    private int id;
    private String name;
    private double unitPrice;
    private String serialNumber;
    private int quantity;

    // Constructor
    public Product() {
    }

    // Constructor con parámetros
    public Product(int id, String name, double unitPrice, String serialNumber, int quantity) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.serialNumber = serialNumber;
        this.quantity = quantity;
    }

    // Métodos getter y setter para id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Métodos getter y setter para name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Métodos getter y setter para unitPrice
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    // Métodos getter y setter para serialNumber
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    // Métodos getter y setter para quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Método toString para imprimir la información del producto
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", serialNumber='" + serialNumber + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
