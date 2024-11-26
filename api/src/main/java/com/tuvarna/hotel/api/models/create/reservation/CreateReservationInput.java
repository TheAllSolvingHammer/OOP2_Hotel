package com.tuvarna.hotel.api.models.create.reservation;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.enums.TypeReservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CreateReservationInput implements OperationInput {
    private UUID id;
    private TypeReservation type;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private String roomType;
    private List<String> clientList;
}
