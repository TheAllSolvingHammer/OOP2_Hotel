package com.tuvarna.hotel.api.models.display.hotel;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class DisplayHotelsOutput implements OperationOutput {
    List<Hotel> hotelList;
}
