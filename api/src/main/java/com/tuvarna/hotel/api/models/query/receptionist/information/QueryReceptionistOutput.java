package com.tuvarna.hotel.api.models.query.receptionist.information;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.entities.ReceptionistDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@Builder
@AllArgsConstructor
public class QueryReceptionistOutput implements OperationOutput {
    private List<ReceptionistDTO> receptionistDTOS;
}
