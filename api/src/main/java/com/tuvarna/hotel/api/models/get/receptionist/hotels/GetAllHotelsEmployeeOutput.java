package com.tuvarna.hotel.api.models.get.receptionist.hotels;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.entities.Hotel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class GetAllHotelsEmployeeOutput implements OperationOutput {
    private List<Hotel> hotelList;
}
