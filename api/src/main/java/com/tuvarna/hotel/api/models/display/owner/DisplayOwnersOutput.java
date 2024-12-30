package com.tuvarna.hotel.api.models.display.owner;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.entities.Owner;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class DisplayOwnersOutput implements OperationOutput {
    private List<Owner> ownerList;
}
