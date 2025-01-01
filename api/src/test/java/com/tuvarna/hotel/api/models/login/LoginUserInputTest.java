package com.tuvarna.hotel.api.models.login;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class LoginUserInputTest {

    private Validator validator;

    public LoginUserInputTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testConstructorAndGettersSetters() {
        String username = "chris";
        String password = "password123";
        LoginUserInput input = new LoginUserInput(username, password);

        assertEquals(username, input.getUsername());
        assertEquals(password, input.getPassword());

        String newUsername = "newUser";
        input.setUsername(newUsername);
        assertEquals(newUsername, input.getUsername());

        String newPassword = "newPassword123";
        input.setPassword(newPassword);
        assertEquals(newPassword, input.getPassword());
    }

    @Test
    public void testToString() {
        String username = "chris";
        String password = "password123";
        LoginUserInput input = new LoginUserInput(username, password);

        String toStringOutput = input.toString();
        assertTrue(toStringOutput.contains("username=" + username));
        assertTrue(toStringOutput.contains("password=" + password));
    }

    @Test
    public void testBuilder() {
        String username = "chris";
        String password = "password123";
        LoginUserInput input = LoginUserInput.builder()
                .username(username)
                .password(password)
                .build();

        assertEquals(username, input.getUsername());
        assertEquals(password, input.getPassword());
    }

    @Test
    public void testNoArgsConstructor() {
        LoginUserInput input = new LoginUserInput();
        assertNull(input.getUsername());
        assertNull(input.getPassword());
    }

    @Test
    public void testValidation() {
        LoginUserInput input = new LoginUserInput("", "");

        Set<ConstraintViolation<LoginUserInput>> violations = validator.validate(input);

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username can not be empty")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Password can not be empty")));

        input.setUsername("chris");
        input.setPassword("password123");
        violations = validator.validate(input);

        assertTrue(violations.isEmpty());
    }
}
