package com.tuvarna.hotel.api.models.display.hotel;

import com.tuvarna.hotel.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class DisplayHotelsInput implements OperationInput {
    private UUID id;

}
