package com.tuvarna.hotel.api.models.display.hotel;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DisplayHotelsInputTest {

    @Test
    void testDisplayHotelsInputWithValidId() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        DisplayHotelsInput input = DisplayHotelsInput.builder()
                .id(UUID.randomUUID())
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNotNull(input.getId());
    }

    @Test
    void testDisplayHotelsInputWithNullId() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        DisplayHotelsInput input = DisplayHotelsInput.builder()
                .id(null)
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNull(input.getId());
    }
}
