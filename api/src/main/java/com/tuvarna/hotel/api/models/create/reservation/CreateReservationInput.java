package com.tuvarna.hotel.api.models.create.reservation;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.enums.TypeReservation;
import com.tuvarna.hotel.api.enums.TypeRoom;
import com.tuvarna.hotel.api.models.entities.Client;
import com.tuvarna.hotel.api.models.entities.Room;
import com.tuvarna.hotel.api.models.entities.Service;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateReservationInput implements OperationInput {
    @NotNull(message = "Type can not be null")
    private TypeReservation type;
    @FutureOrPresent(message = "Start Date must be in the present or future")
    @NotNull(message = "Start date can not be null")
    private LocalDate startDate;
    @FutureOrPresent(message = "End Date must be in the present or future")
    @NotNull(message = "End date can not be null")
    private LocalDate endDate;
    @NotNull(message = "Room type can not be null")
    private Client client;
    @NotNull(message = "Room can not be null")
    private Room room;
    private List<Service> services;
}
