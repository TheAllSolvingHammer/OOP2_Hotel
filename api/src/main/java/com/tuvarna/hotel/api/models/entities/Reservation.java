package com.tuvarna.hotel.api.models.entities;

import com.tuvarna.hotel.api.enums.StatusReservation;
import com.tuvarna.hotel.api.enums.TypeReservation;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor()
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation {

    @EqualsAndHashCode.Include
    private UUID id;
    private TypeReservation type;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private String room;
    private String hotel;
    private String client;
    private List<Service> services;
    private StatusReservation status;

    @Override
    public String toString(){
        return "ID: " + id +
                ", Room: " + room +
                " Hotel: " + hotel +
                "\n";
    }
}
