package com.tuvarna.hotel.api.models.query.service;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.entities.ServicesDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class QueryServicesOutput implements OperationOutput {
    private List<ServicesDTO> servicesDTOList;
}
