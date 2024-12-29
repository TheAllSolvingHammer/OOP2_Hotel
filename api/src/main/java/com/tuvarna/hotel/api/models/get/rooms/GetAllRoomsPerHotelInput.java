package com.tuvarna.hotel.api.models.get.rooms;

import com.tuvarna.hotel.api.base.OperationInput;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class GetAllRoomsPerHotelInput implements OperationInput {
    private UUID hotelID;
}
