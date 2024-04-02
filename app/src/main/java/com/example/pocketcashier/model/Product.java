package com.example.pocketcashier.model;

import java.util.ArrayList;

public class Product {
    private int id;
    private String name;
    private double unitPrice;
    private String serialNumber;
    private Integer quantity;
    private String imagePath;
    private ArrayList<Category> categories;

    public Product(int id, String name, double unitPrice, String serialNumber, Integer quantity) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.serialNumber = serialNumber;
        this.quantity = quantity;

        this.categories = new ArrayList<Category>();
    }

    public Product(int id, String name, double unitPrice, String serialNumber, Integer quantity, String imagePath) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.serialNumber = serialNumber;
        this.quantity = quantity;
        this.imagePath = imagePath;

        this.categories = new ArrayList<Category>();
    }


    public void clearCategories(){
        categories.clear();
    }
    public void addCategory(Category category){
        categories.add(category);
    }
    public void setCategories(Category... categories){
        for(Category category : categories){
            this.categories.add(category);
        }
    }
    public void setCategories(ArrayList<Category> categories){
        this.categories.addAll(categories);
    }
    public void eraseCategory(Category category){
        this.categories.remove(category);
    }

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


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Métodos getter y setter para imagePath
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Método toString para imprimir la información del producto
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", serialNumber='" + serialNumber + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
