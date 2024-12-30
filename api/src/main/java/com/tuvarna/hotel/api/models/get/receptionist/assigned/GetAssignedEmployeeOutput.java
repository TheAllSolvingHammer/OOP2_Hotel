package com.tuvarna.hotel.api.models.get.receptionist.assigned;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.entities.Receptionist;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class GetAssignedEmployeeOutput implements OperationOutput {
     private List<Receptionist> receptionistList;
}
