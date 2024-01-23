package com.example.lib.patron;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PatronValidatorTest {

    private PatronValidator patronValidator;

    @BeforeEach
    void setUp() {
        patronValidator = new PatronValidator();
    }

    @Test
    void validateUpdates_WithValidData_ShouldNotThrowException() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "John Doe");
        updates.put("contactInformation", "john.doe@example.com");

        assertDoesNotThrow(() -> patronValidator.validateUpdates(updates));
    }

}
