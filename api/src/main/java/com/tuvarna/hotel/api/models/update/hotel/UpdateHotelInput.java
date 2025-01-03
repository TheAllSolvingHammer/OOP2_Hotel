package com.tuvarna.hotel.api.models.update.hotel;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.models.entities.Manager;
import com.tuvarna.hotel.api.models.entities.Service;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class UpdateHotelInput implements OperationInput {
    private List<Manager> managerList;
    private List<Service> serviceList;
    private UUID hotelID;
}
