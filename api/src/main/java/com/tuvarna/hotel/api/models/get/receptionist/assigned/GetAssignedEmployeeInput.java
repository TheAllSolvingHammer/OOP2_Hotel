package com.tuvarna.hotel.api.models.get.receptionist.assigned;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.models.entities.Hotel;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class GetAssignedEmployeeInput implements OperationInput {
    private Hotel hotel;
}
