package com.tuvarna.hotel.api.models.display.manager.hotel;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DisplayManagerHotelInputTest {

    @Test
    void testDisplayManagerHotelInputWithValidId() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        DisplayManagerHotelInput input = DisplayManagerHotelInput.builder()
                .id(UUID.randomUUID())
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNotNull(input.getId());
    }

    @Test
    void testDisplayManagerHotelInputWithNullId() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        DisplayManagerHotelInput input = DisplayManagerHotelInput.builder()
                .id(null)
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNull(input.getId());
    }
}
