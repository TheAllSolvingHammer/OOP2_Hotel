package com.tuvarna.hotel.api.models.create.service;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreateServiceInputTest {

    @Test
    void testCreateServiceInputWithValidData() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateServiceInput input = CreateServiceInput.builder()
                .serviceName("Room Cleaning")
                .price(BigDecimal.valueOf(50.0))
                .build();

        var violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertEquals("Room Cleaning", input.getServiceName());
        assertEquals(BigDecimal.valueOf(50.0), input.getPrice());
    }

    @Test
    void testCreateServiceInputWithEmptyServiceName() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateServiceInput input = CreateServiceInput.builder()
                .serviceName("")
                .price(BigDecimal.valueOf(50.0))
                .build();

        var violations = validator.validate(input);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Service name can not be empty")));
    }
}