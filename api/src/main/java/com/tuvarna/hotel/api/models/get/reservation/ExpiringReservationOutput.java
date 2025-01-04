package com.tuvarna.hotel.api.models.get.reservation;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.entities.Reservation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@Builder
@AllArgsConstructor
public class ExpiringReservationOutput implements OperationOutput {
    private List<Reservation> reservations;
}
