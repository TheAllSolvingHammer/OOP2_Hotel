package com.tuvarna.hotel.api.models.create.client;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateClientInputTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validCreateClientInput_shouldPassValidation() {
        CreateClientInput input = CreateClientInput.builder()
                .firstName("Travis")
                .lastName("Scott")
                .phone("1234567890")
                .ucn("1234567890")
                .address("123 Main Street")
                .email("travis.scott@example.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .issueDate(LocalDate.of(2010, 1, 1))
                .expireDate(LocalDate.of(2030, 1, 1))
                .issuedBy("Some Authority")
                .build();

        Set<ConstraintViolation<CreateClientInput>> violations = validator.validate(input);

        assertTrue(violations.isEmpty(), "Expected no validation errors for valid input");
    }

    @Test
    void invalidCreateClientInput_shouldFailValidation() {
        CreateClientInput input = CreateClientInput.builder()
                .firstName("")
                .lastName("")
                .phone("123")
                .ucn("123")
                .address("")
                .email("not-an-email")
                .birthDate(LocalDate.of(2025, 1, 1))
                .issueDate(LocalDate.of(2030, 1, 1))
                .expireDate(LocalDate.of(2000, 1, 1))
                .issuedBy("")
                .build();

        Set<ConstraintViolation<CreateClientInput>> violations = validator.validate(input);

        assertFalse(violations.isEmpty(), "Expected validation errors for invalid input");

        assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("First name cannot be empty")),
                "Expected validation error for first name"
        );
        assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("UCN must be 10 characters")),
                "Expected validation error for UCN length"
        );
    }
}