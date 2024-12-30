package com.tuvarna.hotel.api.models.create.reservation;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateReservationOutput implements OperationOutput {
    private String message;
}
