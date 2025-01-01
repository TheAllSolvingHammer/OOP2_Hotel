package com.tuvarna.hotel.api.models.create.hotel;

import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Manager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateHotelInputTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCreateHotelInput() {
        Manager manager = Manager.builder()
                .id(UUID.randomUUID())
                .firstName("travis")
                .lastName("scott")
                .email("travis.scott@example.com")
                .phoneNumber("1234567890")
                .hotelList(List.of(new Hotel()))
                .build();

        CreateHotelInput input = CreateHotelInput.builder()
                .name("Grand Hotel")
                .location("Paris, France")
                .rating(5)
                .ownerID(UUID.randomUUID())
                .managerList(List.of(manager))
                .build();

        Set<ConstraintViolation<CreateHotelInput>> violations = validator.validate(input);

        assertTrue(violations.isEmpty(), "Valid input should not produce violations");
    }

    @Test
    void testEmptyNameConstraint() {
        Manager manager = Manager.builder()
                .id(UUID.randomUUID())
                .firstName("travis")
                .lastName("scott")
                .email("travis.scott@example.com")
                .phoneNumber("1234567890")
                .hotelList(List.of(new Hotel()))
                .build();

        CreateHotelInput input = CreateHotelInput.builder()
                .name("")
                .location("Paris, France")
                .rating(5)
                .ownerID(UUID.randomUUID())
                .managerList(List.of(manager))
                .build();

        Set<ConstraintViolation<CreateHotelInput>> violations = validator.validate(input);

        assertFalse(violations.isEmpty(), "Empty name should produce violations");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Hotel name can not be empty")));
    }

    @Test
    void testEmptyLocationConstraint() {
        Manager manager = Manager.builder()
                .id(UUID.randomUUID())
                .firstName("travis")
                .lastName("scott")
                .email("travis.scott@example.com")
                .phoneNumber("1234567890")
                .hotelList(List.of(new Hotel()))
                .build();

        CreateHotelInput input = CreateHotelInput.builder()
                .name("Grand Hotel")
                .location("")
                .rating(5)
                .ownerID(UUID.randomUUID())
                .managerList(List.of(manager))
                .build();

        Set<ConstraintViolation<CreateHotelInput>> violations = validator.validate(input);

        assertFalse(violations.isEmpty(), "Empty location should produce violations");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Location can not be empty")));
    }

    @Test
    void testNullManagerList() {
        CreateHotelInput input = CreateHotelInput.builder()
                .name("Grand Hotel")
                .location("Paris, France")
                .rating(5)
                .ownerID(UUID.randomUUID())
                .managerList(null)
                .build();

        Set<ConstraintViolation<CreateHotelInput>> violations = validator.validate(input);

        assertTrue(violations.isEmpty(), "Manager list being null should not produce violations");
    }
}
