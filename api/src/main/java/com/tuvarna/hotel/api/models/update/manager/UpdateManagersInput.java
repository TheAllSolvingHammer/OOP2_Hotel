package com.tuvarna.hotel.api.models.update.manager;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import com.tuvarna.hotel.api.models.display.service.Service;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class UpdateManagersInput implements OperationInput {
    private List<Manager> managerList;
    private UUID hotelID;
}
