package com.tuvarna.hotel.api.models.display.reservation;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.entities.Reservation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class DisplayAllReservationsHotelOutput implements OperationOutput {
    private List<Reservation> reservations;
}
