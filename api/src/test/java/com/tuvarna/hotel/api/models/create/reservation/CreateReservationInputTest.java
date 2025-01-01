package com.tuvarna.hotel.api.models.create.reservation;

import com.tuvarna.hotel.api.enums.TypeReservation;
import com.tuvarna.hotel.api.models.entities.Client;
import com.tuvarna.hotel.api.models.entities.Room;
import com.tuvarna.hotel.api.models.entities.Service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;

class CreateReservationInputTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCreateReservationInput() {
        Service service = Service.builder()
                .id(randomUUID())
                .name("Spa")
                .price(BigDecimal.valueOf(100.00))
                .build();

        Client client = Client.builder()
                .id(randomUUID())
                .firstName("travis")
                .lastName("scott")
                .email("travis.scott@example.com")
                .phone("1234567890")
                .build();

        Room room = Room.builder()
                .roomID(randomUUID())
                .roomNumber("101")
                .floor(1)
                .price(BigDecimal.valueOf(200.00))
                .build();

        CreateReservationInput input = CreateReservationInput.builder()
                .type(TypeReservation.ONLINE)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .client(client)
                .room(room)
                .id(randomUUID())
                .services(List.of(service))
                .build();

        Set<ConstraintViolation<CreateReservationInput>> violations = validator.validate(input);

        assertTrue(violations.isEmpty(), "Valid input should not produce violations");
    }

    @Test
    void testNullTypeConstraint() {
        CreateReservationInput input = CreateReservationInput.builder()
                .type(null)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .client(new Client())
                .room(new Room())
                .services(List.of(new Service()))
                .build();

        Set<ConstraintViolation<CreateReservationInput>> violations = validator.validate(input);

        assertFalse(violations.isEmpty(), "Null type should produce violations");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Type can not be null")));
    }

    @Test
    void testPastStartDateConstraint() {
        CreateReservationInput input = CreateReservationInput.builder()
                .type(TypeReservation.ONLINE)
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .client(new Client())
                .room(new Room())
                .services(List.of(new Service()))
                .build();

        Set<ConstraintViolation<CreateReservationInput>> violations = validator.validate(input);

        assertFalse(violations.isEmpty(), "Past start date should produce violations");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Start Date must be in the present or future")));
    }

    @Test
    void testNullRoomConstraint() {
        CreateReservationInput input = CreateReservationInput.builder()
                .type(TypeReservation.ONLINE)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .client(new Client())
                .room(null)
                .services(List.of(new Service()))
                .build();

        Set<ConstraintViolation<CreateReservationInput>> violations = validator.validate(input);

        assertFalse(violations.isEmpty(), "Null room should produce violations");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Room can not be null")));
    }

    @Test
    void testValidServices() {
        Service service = Service.builder()
                .id(randomUUID())
                .name("Spa")
                .price(BigDecimal.valueOf(100.00))
                .build();

        CreateReservationInput input = CreateReservationInput.builder()
                .type(TypeReservation.REAL)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .client(new Client())
                .room(new Room())
                .services(List.of(service))
                .build();

        Set<ConstraintViolation<CreateReservationInput>> violations = validator.validate(input);

        assertTrue(violations.isEmpty(), "Valid services should not produce violations");
    }
}
