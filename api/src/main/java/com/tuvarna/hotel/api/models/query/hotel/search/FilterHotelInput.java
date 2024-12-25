package com.tuvarna.hotel.api.models.query.hotel.search;

import com.tuvarna.hotel.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class FilterHotelInput implements OperationInput {
    private String param;
}
