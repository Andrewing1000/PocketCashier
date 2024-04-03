package com.example.pocketcashier.model;

public class Client {
    private int id;
    private String name;
    private String ci;

    public Client(int id, String name, String ci) {
        this.id = id;
        this.name = name;
        this.ci = ci;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }
}