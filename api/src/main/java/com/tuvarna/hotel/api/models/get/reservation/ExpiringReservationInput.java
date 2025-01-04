package com.tuvarna.hotel.api.models.get.reservation;

import com.tuvarna.hotel.api.base.OperationInput;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@Builder
@AllArgsConstructor
public class ExpiringReservationInput implements OperationInput {
    private UUID userID;
    private LocalDate date;
}
