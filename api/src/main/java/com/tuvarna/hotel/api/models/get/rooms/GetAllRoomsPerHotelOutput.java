package com.tuvarna.hotel.api.models.get.rooms;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.Room;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class GetAllRoomsPerHotelOutput implements OperationOutput {
    private List<Room> roomList;
}
