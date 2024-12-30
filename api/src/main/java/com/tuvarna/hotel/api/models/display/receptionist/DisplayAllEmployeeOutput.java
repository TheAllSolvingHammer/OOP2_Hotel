package com.tuvarna.hotel.api.models.display.receptionist;

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
public class DisplayAllEmployeeOutput implements OperationOutput {
    private List<Receptionist> receptionistList;
}
