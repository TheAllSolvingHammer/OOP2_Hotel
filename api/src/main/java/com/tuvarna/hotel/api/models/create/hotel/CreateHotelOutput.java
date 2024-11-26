package com.tuvarna.hotel.api.models.create.hotel;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateHotelOutput implements OperationOutput {
    private String message;
}
