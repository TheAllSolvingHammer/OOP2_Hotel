package com.tuvarna.hotel.api.models.update.receptionist;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class UpdateReceptionistOfHotelOutput implements OperationOutput {
    private String message;
}
