package com.facility.management.usecases.auth.exceptions;

public class WrongCredentialsException extends RuntimeException{

    public WrongCredentialsException(String message) {
        super(message);
    }
}
