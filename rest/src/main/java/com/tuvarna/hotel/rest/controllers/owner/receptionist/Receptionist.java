package com.tuvarna.hotel.rest.controllers.owner.receptionist;

public class Receptionist {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String number;

    public Receptionist(int id, String name, String surname, String email, String number) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.number = number;
    }
}
