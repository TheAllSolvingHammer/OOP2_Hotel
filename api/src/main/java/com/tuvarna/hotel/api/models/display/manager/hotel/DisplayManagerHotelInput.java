package com.tuvarna.hotel.api.models.display.manager.hotel;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DisplayManagerHotelInput implements OperationInput {
    private UUID id;
}
