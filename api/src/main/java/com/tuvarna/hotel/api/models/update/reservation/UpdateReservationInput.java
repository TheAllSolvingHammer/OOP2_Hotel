package com.tuvarna.hotel.api.models.update.reservation;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.enums.StatusReservation;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class UpdateReservationInput implements OperationInput {
    private UUID reservationID;
    private StatusReservation status;
}
