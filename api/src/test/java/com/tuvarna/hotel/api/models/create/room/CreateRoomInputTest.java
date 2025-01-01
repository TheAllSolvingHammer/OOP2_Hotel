package com.tuvarna.hotel.api.models.create.room;

import com.tuvarna.hotel.api.enums.TypeRoom;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateRoomInputTest {

    @Test
    void testCreateRoomInputWithSingleTypeRoom() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UUID hotelId = UUID.randomUUID();
        CreateRoomInput input = CreateRoomInput.builder()
                .floor(1)
                .roomNumber("101")
                .price(BigDecimal.valueOf(150.0))
                .type(TypeRoom.SINGLE)
                .hotelID(hotelId)
                .build();


        var violations = validator.validate(input);

        assertTrue(violations.isEmpty(), "There should be no validation errors");
        assertEquals(1, input.getFloor());
        assertEquals("101", input.getRoomNumber());
        assertEquals(BigDecimal.valueOf(150.0), input.getPrice());
        assertEquals(TypeRoom.SINGLE, input.getType());
        assertEquals(hotelId, input.getHotelID());
    }

    @Test
    void testCreateRoomInputWithEmptyRoomNumber() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateRoomInput input = CreateRoomInput.builder()
                .floor(1)
                .roomNumber("")
                .price(BigDecimal.valueOf(150.0))
                .type(TypeRoom.SINGLE)
                .hotelID(UUID.randomUUID())
                .build();

        var violations = validator.validate(input);

        assertFalse(violations.isEmpty(), "Validation should catch an empty room number");
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Room number can not be empty")));
    }
}