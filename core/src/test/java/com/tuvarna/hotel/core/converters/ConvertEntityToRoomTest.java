package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.enums.TypeRoom;
import com.tuvarna.hotel.api.models.entities.Room;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.enums.RoomType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConvertEntityToRoomTest {

    private final ConvertEntityToRoom converter = new ConvertEntityToRoom();

    @Test
    void testConvert() {
        UUID roomId = UUID.randomUUID();
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomId);
        roomEntity.setFloor(2);
        roomEntity.setRoomNumber("201");
        roomEntity.setPrice(BigDecimal.valueOf(150.75));
        roomEntity.setType(RoomType.SINGLE);

        List<Room> rooms = converter.convert(List.of(roomEntity));

        assertNotNull(rooms);
        assertEquals(1, rooms.size());

        Room room = rooms.getFirst();
        assertEquals(roomId, room.getRoomID());
        assertEquals(2, room.getFloor());
        assertEquals("201", room.getRoomNumber());
        assertEquals(BigDecimal.valueOf(150.75), room.getPrice());
        assertEquals(TypeRoom.SINGLE, room.getRoomType());
    }
}