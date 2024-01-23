package com.example.lib.patron;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PatronValidator {

    private void validateName(Object value) {
        if (!(value instanceof String name)) {
            throw new ValidationException("Name must be a string");
        }
        if (name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be blank");
        }
        if (name.length() > 100) {
            throw new ValidationException("Name must not exceed 100 characters");
        }
    }

    private void ValidateContactInformation(Object value) {
        if (!(value instanceof String contactInformation)) {
            throw new ValidationException("Name must be a string");
        }
        if (contactInformation.trim().isEmpty()) {
            throw new ValidationException("Name cannot be blank");
        }
    }

    void validateUpdates(Map<String, Object> updates) {
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            switch (entry.getKey()) {
                case "name":
                    validateName(entry.getValue());
                    break;
                case "contactInformation":
                    ValidateContactInformation(entry.getValue());
                    break;
                default:
                    throw new ValidationException("Invalid update field: " + entry.getKey());
            }
        }
    }

}
