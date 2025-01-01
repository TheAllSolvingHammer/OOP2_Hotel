package com.tuvarna.hotel.api.models.get.rooms;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.UUID;

public class GetAllRoomsPerHotelInputTest {

    @Test
    public void testConstructorAndGettersSetters() {
        UUID hotelID = UUID.randomUUID();
        GetAllRoomsPerHotelInput input = new GetAllRoomsPerHotelInput(hotelID);
        assertEquals(hotelID, input.getHotelID());

        UUID newHotelID = UUID.randomUUID();
        input.setHotelID(newHotelID);
        assertEquals(newHotelID, input.getHotelID());
    }

    @Test
    public void testToString() {
        UUID hotelID = UUID.randomUUID();
        GetAllRoomsPerHotelInput input = new GetAllRoomsPerHotelInput(hotelID);
        String toStringOutput = input.toString();
        assertTrue(toStringOutput.contains("hotelID=" + hotelID));
    }

    @Test
    public void testBuilder() {
        UUID hotelID = UUID.randomUUID();
        GetAllRoomsPerHotelInput input = GetAllRoomsPerHotelInput.builder()
                .hotelID(hotelID)
                .build();
        assertEquals(hotelID, input.getHotelID());
    }

    @Test
    public void testNoArgsConstructor() {
        GetAllRoomsPerHotelInput input = new GetAllRoomsPerHotelInput();
        assertNull(input.getHotelID());
    }
}
