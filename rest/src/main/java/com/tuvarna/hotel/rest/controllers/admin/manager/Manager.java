package com.tuvarna.hotel.rest.controllers.admin.manager;

public class Manager {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String number;

    public Manager(int id, String name, String email, String surname, String number) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.surname = surname;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }
}
