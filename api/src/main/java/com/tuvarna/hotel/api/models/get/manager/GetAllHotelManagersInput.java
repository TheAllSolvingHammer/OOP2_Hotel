package com.tuvarna.hotel.api.models.get.manager;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class GetAllHotelManagersInput implements OperationInput {
    private Hotel hotel;
}
