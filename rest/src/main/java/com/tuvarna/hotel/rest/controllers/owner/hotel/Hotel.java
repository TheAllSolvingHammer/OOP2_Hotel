package com.tuvarna.hotel.rest.controllers.owner.hotel;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private int stars;

    public Hotel(int id, String name, String address, int stars) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.stars = stars;
    }
}
