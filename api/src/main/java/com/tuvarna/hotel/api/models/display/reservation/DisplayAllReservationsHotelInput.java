package com.tuvarna.hotel.api.models.display.reservation;

import com.tuvarna.hotel.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class DisplayAllReservationsHotelInput implements OperationInput {
    private UUID hotelID;
}
