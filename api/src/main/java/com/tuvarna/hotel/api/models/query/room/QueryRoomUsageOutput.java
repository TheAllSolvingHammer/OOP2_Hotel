package com.tuvarna.hotel.api.models.query.room;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.entities.RoomQueryDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class QueryRoomUsageOutput implements OperationOutput {
    private List<RoomQueryDTO> roomsList;
}
