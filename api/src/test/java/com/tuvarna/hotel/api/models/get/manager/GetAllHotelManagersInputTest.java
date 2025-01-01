package com.tuvarna.hotel.api.models.get.manager;

import com.tuvarna.hotel.api.models.entities.Hotel;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class GetAllHotelManagersInputTest {

    @Test
    void testGetAllHotelManagersInputWithValidHotel() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Hotel hotel = Hotel.builder().build();
        GetAllHotelManagersInput input = GetAllHotelManagersInput.builder()
                .hotel(hotel)
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNotNull(input.getHotel());
    }

    @Test
    void testGetAllHotelManagersInputWithNullHotel() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        GetAllHotelManagersInput input = GetAllHotelManagersInput.builder()
                .hotel(null)
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNull(input.getHotel());
    }
}
