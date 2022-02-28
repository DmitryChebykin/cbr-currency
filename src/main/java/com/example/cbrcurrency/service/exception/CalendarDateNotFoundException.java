package com.example.cbrcurrency.service.exception;

public class CalendarDateNotFoundException extends RuntimeException {

    public CalendarDateNotFoundException() {
    }

    public CalendarDateNotFoundException(String message) {
        super(message);
    }
}
