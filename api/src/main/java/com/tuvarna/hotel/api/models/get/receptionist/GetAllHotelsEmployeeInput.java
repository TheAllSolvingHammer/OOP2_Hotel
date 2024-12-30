package com.tuvarna.hotel.api.models.get.receptionist;

import com.tuvarna.hotel.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class GetAllHotelsEmployeeInput implements OperationInput {
    private UUID id;
}
