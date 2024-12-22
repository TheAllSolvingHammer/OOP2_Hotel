package com.tuvarna.hotel.api.models.display.manager;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class DisplayManagerOutput implements OperationOutput {
    private List<Manager> managerList;
}
