package com.tuvarna.hotel.api.models.query.reservation;

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
public class QueryReservationOutput implements OperationOutput {
    private List<Reservation> reservations;
}
