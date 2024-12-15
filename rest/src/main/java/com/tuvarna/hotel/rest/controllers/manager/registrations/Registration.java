package com.tuvarna.hotel.rest.controllers.manager.registrations;

import com.tuvarna.hotel.persistence.enums.ReservationType;

import java.util.Date;

public class Registration {
    private int id;
    private Date start;
    private Date end;
    private double price;
    private ReservationType type;

    public Registration(int id, Date start, Date end, double price, ReservationType type) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.price = price;
        this.type = type;
    }
}
