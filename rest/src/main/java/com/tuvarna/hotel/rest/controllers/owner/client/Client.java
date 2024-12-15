package com.tuvarna.hotel.rest.controllers.owner.client;

public class Client {
    private String name;
    private String surname;
    private String number;
    private int stars;

    public Client(String name, String surname, String number, int stars) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.stars = stars;
    }
}
