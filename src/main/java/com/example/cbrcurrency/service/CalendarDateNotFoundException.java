package com.example.cbrcurrency.service;

public class CalendarDateNotFoundException extends RuntimeException {

    public CalendarDateNotFoundException() {
    }

    public CalendarDateNotFoundException(String message) {
        super(message);
    }
}
