package com.tuvarna.hotel.rest.controllers.reception.reservation;

import com.tuvarna.hotel.persistence.enums.ReservationType;

import java.util.Date;

public class Reservation {
    private int id;
    private Date start;
    private Date end;
    private double price;
    private ReservationType type;

    public Reservation(int id, Date start, Date end, double price, ReservationType type) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.price = price;
        this.type = type;
    }
}
