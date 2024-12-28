package com.tuvarna.hotel.api.models.display.service;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class DisplayServicesOutput implements OperationOutput {
    List<Service> serviceList;
}