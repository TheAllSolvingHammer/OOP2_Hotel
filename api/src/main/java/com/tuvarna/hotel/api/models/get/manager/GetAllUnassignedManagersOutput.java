package com.tuvarna.hotel.api.models.get.manager;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class GetAllUnassignedManagersOutput implements OperationOutput {
    List<Manager> managerlist;
}
