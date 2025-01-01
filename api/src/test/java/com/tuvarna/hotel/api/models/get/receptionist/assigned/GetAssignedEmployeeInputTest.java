package com.tuvarna.hotel.api.models.get.receptionist.assigned;

import com.tuvarna.hotel.api.models.entities.Hotel;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class GetAssignedEmployeeInputTest {

    @Test
    void testGetAssignedEmployeeInputWithValidHotel() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Hotel hotel = Hotel.builder().build();
        GetAssignedEmployeeInput input = GetAssignedEmployeeInput.builder()
                .hotel(hotel)
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNotNull(input.getHotel());
    }

    @Test
    void testGetAssignedEmployeeInputWithNullHotel() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        GetAssignedEmployeeInput input = GetAssignedEmployeeInput.builder()
                .hotel(null)
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNull(input.getHotel());
    }
}
