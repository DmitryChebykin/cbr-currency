package com.example.cbrcurrency.service.exception;

public class RateNotFoundException  extends RuntimeException{
    public RateNotFoundException() {
    }

    public RateNotFoundException(String message) {
        super(message);
    }
}
