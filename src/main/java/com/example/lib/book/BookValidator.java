package com.example.lib.book;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookValidator {
    void validateUpdates(Map<String, Object> updates) {
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            switch (entry.getKey()) {
                case "title":
                    validateTitle(entry.getValue());
                    break;
                case "author":
                    validateAuthor(entry.getValue());
                    break;
                case "publicationYear":
                    validatePublicationYear(entry.getValue());
                    break;
                case "ISBN":
                    validateISBN(entry.getValue());
                    break;
                default:
                    throw new ValidationException("Invalid update field: " + entry.getKey());
            }
        }
    }

    private void validateTitle(Object value) {
        if (!(value instanceof String title)) {
            throw new ValidationException("Title must be a string");
        }
        if (title.trim().isEmpty()) {
            throw new ValidationException("Title cannot be blank");
        }
        if (title.length() > 100) {
            throw new ValidationException("Title must not exceed 100 characters");
        }
    }

    private void validateAuthor(Object value) {
        if (!(value instanceof String author)) {
            throw new ValidationException("Author must be a string");
        }
        if (author.trim().isEmpty()) {
            throw new ValidationException("Author cannot be blank");
        }
    }

    private void validatePublicationYear(Object value) {
        if (!(value instanceof Integer)) {
            throw new ValidationException("Publication year must be an integer");
        }
        int publicationYear = (Integer) value;
        if (publicationYear <= 0) {
            throw new ValidationException("Publication year must be a positive number");
        }
    }

    private void validateISBN(Object value) {
        if (value != null && !(value instanceof String)) {
            throw new ValidationException("ISBN must be a string");
        }
    }
}
