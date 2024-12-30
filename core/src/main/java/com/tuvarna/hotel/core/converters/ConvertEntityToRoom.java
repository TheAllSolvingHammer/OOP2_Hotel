package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.enums.TypeRoom;
import com.tuvarna.hotel.api.models.entities.Room;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.RoomEntity;

import java.util.List;

@Singleton
public class ConvertEntityToRoom implements BaseConverter<List<RoomEntity>,List<Room>>{
    @Override
    public List<Room> convert(List<RoomEntity> roomEntities) {
        return roomEntities.stream().map(roomEntity -> Room.builder()
                .roomID(roomEntity.getId())
                .floor(roomEntity.getFloor())
                .roomNumber(roomEntity.getRoomNumber())
                .price(roomEntity.getPrice())
                .price(roomEntity.getPrice())
                .roomType(TypeRoom.getByCode(roomEntity.getType().name())) //todo use filter and throw exception
                .build()).toList();
    }
}
