package com.example.lib.book;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BookValidatorTest {
    private final BookValidator bookValidator = new BookValidator();

    @Test
    void validateUpdates_WithValidData_ShouldPass() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "Valid Title");
        updates.put("author", "Valid Author");
        updates.put("publicationYear", 2021);
        updates.put("ISBN", "123-456-789");

        assertDoesNotThrow(() -> bookValidator.validateUpdates(updates));
    }
}