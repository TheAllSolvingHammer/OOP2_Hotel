package com.tuvarna.hotel.api.models.display.manager.hotel;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DisplayManagerHotelOutput implements OperationOutput {
    private List<Hotel> hotelList;
}
