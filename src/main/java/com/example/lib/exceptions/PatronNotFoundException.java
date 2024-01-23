package com.example.lib.exceptions;

public class PatronNotFoundException extends RuntimeException{
    public PatronNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }}
