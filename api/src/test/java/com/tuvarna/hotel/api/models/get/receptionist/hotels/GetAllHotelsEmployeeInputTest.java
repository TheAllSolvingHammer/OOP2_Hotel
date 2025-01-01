package com.tuvarna.hotel.api.models.get.receptionist.hotels;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.UUID;

public class GetAllHotelsEmployeeInputTest {

    @Test
    public void testConstructorAndGettersSetters() {

        UUID id = UUID.randomUUID();

        GetAllHotelsEmployeeInput input = new GetAllHotelsEmployeeInput(id);

        assertEquals(id, input.getId(), "The id should be the same as the constructor value");

        UUID newId = UUID.randomUUID();
        input.setId(newId);
        assertEquals(newId, input.getId(), "The id should be updated with the setter method");
    }

    @Test
    public void testToString() {
        UUID id = UUID.randomUUID();
        GetAllHotelsEmployeeInput input = new GetAllHotelsEmployeeInput(id);
        String toStringOutput = input.toString();
        assertTrue(toStringOutput.contains("id=" + id), "toString should contain the id value");
    }

    @Test
    public void testBuilder() {
        UUID id = UUID.randomUUID();
        GetAllHotelsEmployeeInput input = GetAllHotelsEmployeeInput.builder()
                .id(id)
                .build();
        assertEquals(id, input.getId(), "The id should be set through the builder pattern");
    }

    @Test
    public void testNoArgsConstructor() {
        GetAllHotelsEmployeeInput input = new GetAllHotelsEmployeeInput();
        assertNull(input.getId(), "The id should be null for the no-args constructor");
    }
}