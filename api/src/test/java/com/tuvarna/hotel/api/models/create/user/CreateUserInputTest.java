package com.tuvarna.hotel.api.models.create.user;

import com.tuvarna.hotel.api.enums.RoleType;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


import static org.junit.jupiter.api.Assertions.*;

class CreateUserInputTest {

    @Test
    void testCreateUserInputWithValidData() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateUserInput input = CreateUserInput.builder()
                .firstName("kendrick")
                .lastName("lamar")
                .phone("1234567890")
                .username("kendricklamar")
                .role(RoleType.EMPLOYEE)
                .email("kendrick.lamar@example.com")
                .password("password123")
                .passwordSecond("password123")
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertEquals("kendrick", input.getFirstName());
        assertEquals("lamar", input.getLastName());
        assertEquals("1234567890", input.getPhone());
        assertEquals("kendricklamar", input.getUsername());
        assertEquals(RoleType.EMPLOYEE, input.getRole());
        assertEquals("kendrick.lamar@example.com", input.getEmail());
        assertEquals("password123", input.getPassword());
        assertEquals("password123", input.getPasswordSecond());
    }

    @Test
    void testCreateUserInputWithInvalidEmail() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateUserInput input = CreateUserInput.builder()
                .firstName("kendrick")
                .lastName("lamar")
                .phone("1234567890")
                .username("kendricklamar")
                .role(RoleType.EMPLOYEE)
                .email("invalid-email")
                .password("password123")
                .passwordSecond("password123")
                .build();

        var violations = validator.validate(input);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Incorrect email")));
    }

    @Test
    void testCreateUserInputWithEmptyFirstName() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateUserInput input = CreateUserInput.builder()
                .firstName("")
                .lastName("lamar")
                .phone("1234567890")
                .username("kendricklamar")
                .role(RoleType.EMPLOYEE)
                .email("kendrick.lamar@example.com")
                .password("password123")
                .passwordSecond("password123")
                .build();

        var violations = validator.validate(input);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("First name can not be null")));
    }

    @Test
    void testCreateUserInputWithMismatchedPasswords() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateUserInput input = CreateUserInput.builder()
                .firstName("kendrick")
                .lastName("lamar")
                .phone("1234567890")
                .username("kendricklamar")
                .role(RoleType.EMPLOYEE)
                .email("kendrick.lamar@example.com")
                .password("password123")
                .passwordSecond("password456")
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNotEquals(input.getPassword(), input.getPasswordSecond());
    }
}
