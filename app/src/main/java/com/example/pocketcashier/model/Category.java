package com.example.pocketcashier.model;

public class Category {
    private int id;
    private String name;
    private String image;

    // Constructor
    public Category() {
    }

    // Constructor con parámetros
    public Category(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
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

    // Métodos getter y setter para image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Método toString para imprimir la información de la categoría
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
