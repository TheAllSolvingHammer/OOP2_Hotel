package com.tuvarna.hotel.api.models.update.receptionist;

import static org.junit.jupiter.api.Assertions.*;

import com.tuvarna.hotel.api.models.entities.Receptionist;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

public class UpdateReceptionistOfHotelInputTest {

    @Test
    public void testConstructorAndGettersSetters() {
        UUID hotelID = UUID.randomUUID();

        UUID receptionistId = UUID.randomUUID();
        Receptionist receptionist = new Receptionist(receptionistId, "Kendrick", "Lamar", "kendrick.lamar@gmail.com", "1112223333", null);

        List<Receptionist> receptionistList = List.of(receptionist);

        UpdateReceptionistOfHotelInput input = new UpdateReceptionistOfHotelInput(hotelID, receptionistList);

        assertEquals(receptionistList, input.getReceptionistList());
        assertEquals(hotelID, input.getHotelID());

        UUID newHotelID = UUID.randomUUID();
        input.setHotelID(newHotelID);
        assertEquals(newHotelID, input.getHotelID());

        Receptionist newReceptionist = new Receptionist(UUID.randomUUID(), "Travis", "Scott", "Travis.Scott@gmail.com", "4445556666", null);
        input.setReceptionistList(List.of(newReceptionist));
        assertEquals(List.of(newReceptionist), input.getReceptionistList());
    }

    @Test
    public void testToString() {
        UUID hotelID = UUID.randomUUID();

        UUID receptionistId = UUID.randomUUID();
        Receptionist receptionist = new Receptionist(receptionistId, "Kendrick", "Lamar", "kendrick.lamar@gmail.com", "1112223333", null);

        List<Receptionist> receptionistList = List.of(receptionist);

        UpdateReceptionistOfHotelInput input = new UpdateReceptionistOfHotelInput(hotelID, receptionistList);

        String toStringOutput = input.toString();
        assertTrue(toStringOutput.contains("receptionistList=" + receptionistList));
        assertTrue(toStringOutput.contains("hotelID=" + hotelID));
    }

    @Test
    public void testBuilder() {
        UUID hotelID = UUID.randomUUID();

        UUID receptionistId = UUID.randomUUID();
        Receptionist receptionist = new Receptionist(receptionistId, "Kendrick", "Lamar", "kendrick.lamar@gmail.com", "1112223333", null);

        List<Receptionist> receptionistList = List.of(receptionist);

        UpdateReceptionistOfHotelInput input = UpdateReceptionistOfHotelInput.builder()
                .receptionistList(receptionistList)
                .hotelID(hotelID)
                .build();

        assertEquals(receptionistList, input.getReceptionistList());
        assertEquals(hotelID, input.getHotelID());
    }

    @Test
    public void testNoArgsConstructor() {
        UpdateReceptionistOfHotelInput input = new UpdateReceptionistOfHotelInput();
        assertNull(input.getReceptionistList());
        assertNull(input.getHotelID());
    }
}
