package com.tuvarna.hotel.api.models.update.reservation;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class UpdateReservationOutput implements OperationOutput {
    private String message;
}
